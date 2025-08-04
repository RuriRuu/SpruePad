package sprue.pad.ui.projectcontents.fragments.tasks;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import sprue.pad.R;
import sprue.pad.model.Task;

public class Tasks_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private FloatingActionButton addTaskButton;
    private static final String PREF_KEY = "projects";

    private SharedPreferences sharedPreferences;

    private Gson gson = new Gson();

    public Tasks_Fragment(List<Task> ProjectTasks) {
        this.taskList = ProjectTasks;
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
            String title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                taskList.add(new Task(title, false));
                adapter.notifyItemInserted(taskList.size());
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}