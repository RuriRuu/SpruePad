package sprue.pad.ui.mainmenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import sprue.pad.R;
import sprue.pad.model.Project;
import sprue.pad.ui.projectcontents.ProjectContentsActivity;
import sprue.pad.utility.FirebaseUtil;

public class MainMenuRVAdapter extends RecyclerView.Adapter<MainMenuRVAdapter.ViewHolder> {

    private final List<Project> projectList;
    public MaterialCardView projectInstance;
    public Context context;
    public Button projectSettingsButton;

    public MainMenuRVAdapter(List<Project> projectList, Context context) {
        this.projectList = projectList;
        this.context = context;
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
        holder.projectSettingsButton.setOnClickListener(view -> {
            initiateProjectDialog(project, context, projectList, this);
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProjectContentsActivity.class);
            intent.putExtra("project", project);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (projectList != null) ? projectList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectName;
        private final TextView projectDescription;
        private final ImageButton projectSettingsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            projectDescription = itemView.findViewById(R.id.projectDescription);
            projectSettingsButton = itemView.findViewById(R.id.projectSettingsButton);
        }
    }

    public void initiateProjectDialog(Project project, Context context, List<Project> projectList, MainMenuRVAdapter adapter) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_project_settings);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        EditText name = dialog.findViewById(R.id.changeProjectName);
        EditText brand = dialog.findViewById(R.id.changeBrand);
        EditText scale = dialog.findViewById(R.id.changeScale);
        EditText status = dialog.findViewById(R.id.changeStatus);
        EditText desc = dialog.findViewById(R.id.changeDescription);
        ShapeableImageView avatar = dialog.findViewById(R.id.dialogProjectAvatar);

        Button saveButton = dialog.findViewById(R.id.saveProjectButton);
        Button deleteButton = dialog.findViewById(R.id.deleteProjectButton);

        name.setText(project.getProjectName());
        brand.setText(project.getBrand());
        scale.setText(project.getScale());
        status.setText(project.getStatus());
        desc.setText(project.getDescription());

        deleteButton.setOnClickListener(view -> {
            FirebaseUtil.deleteProject(project.getProjectName());
            projectList.remove(project);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        saveButton.setOnClickListener(view -> {
            Project newProject = new Project(name.getText().toString(), brand.getText().toString(), scale.getText().toString(), status.getText().toString(), desc.getText().toString());
            FirebaseUtil.deleteProject(project.getProjectName());
            FirebaseUtil.addProjectToDatabase(newProject.getProjectName(), newProject.getBrand(), newProject.getScale(), newProject.getStatus(), newProject.getDescription());
            projectList.remove(project);
            projectList.add(newProject);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }


}
