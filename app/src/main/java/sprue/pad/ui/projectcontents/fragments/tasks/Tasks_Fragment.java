package sprue.pad.ui.projectcontents.fragments.tasks;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sprue.pad.R;
import sprue.pad.model.Task;
import sprue.pad.utility.FirebaseUtil;
import sprue.pad.utility.Taskscallback;

public class Tasks_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private FloatingActionButton addTaskButton;

    private String ProjectName;
    private SharedPreferences sharedPreferences;


    public Tasks_Fragment(String projectName) {
        this.ProjectName = projectName;
        this.taskList = new ArrayList<>();

        Map<String, String> ProjectTasks = FirebaseUtil.getTasks(projectName, new Taskscallback() {
            @Override
            public void onSuccess(Map<String, String> projectTasks) {
                taskList.clear();
                for (Map.Entry<String, String> entry : projectTasks.entrySet()) {
                    taskList.add(new Task(entry.getKey(), entry.getValue()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Tasks_Fragment", "Failed to load tasks", e);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskList = new ArrayList<>();

        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        addTaskButton.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Task");

        EditText input = new EditText(requireContext());
        input.setHint("Enter task title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String task = input.getText().toString().trim();
            if (!task.isEmpty()) {
                taskList.add(new Task("1", task));

                adapter.notifyItemInserted(taskList.size());
                FirebaseUtil.addTasktoProject(ProjectName, task);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}