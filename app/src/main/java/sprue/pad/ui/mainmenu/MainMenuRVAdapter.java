package sprue.pad.ui.mainmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprue.pad.R;
import sprue.pad.model.Project;

public class MainMenuRVAdapter extends RecyclerView.Adapter<MainMenuRVAdapter.ViewHolder> {

    private final List<Project> projectList;

    public MainMenuRVAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MainMenuRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View projectView = inflater.inflate(R.layout.item_project_card, parent, false);
        return new ViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuRVAdapter.ViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.projectName.setText(project.getProjectName());
        holder.projectDescription.setText(project.getDescription());

        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return (projectList != null) ? projectList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectName;
        private final TextView projectDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            projectDescription = itemView.findViewById(R.id.projectDescription);
        }
    }
}
