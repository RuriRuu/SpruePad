package sprue.pad.ui.projectcontents;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sprue.pad.model.Project;
import sprue.pad.ui.projectcontents.fragments.Inventory_Fragment;
import sprue.pad.ui.projectcontents.fragments.Notes_Fragment;
import sprue.pad.ui.projectcontents.fragments.paint.Paint_Fragment;
import sprue.pad.ui.projectcontents.fragments.tasks.Tasks_Fragment;

public class ProjectPagerAdapter extends FragmentStateAdapter {
    private Project Project;

    public ProjectPagerAdapter(@NonNull FragmentActivity fragmentActivity, Project project) {
        super(fragmentActivity);
        this.Project = project;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        Fragment fragment; ???
        switch (position) {
            case 1:
                return new Paint_Fragment();
            case 2:
                return new Inventory_Fragment();
            case 3:
                return new Notes_Fragment();
            case 0:
            default:
                return new Tasks_Fragment(Project.getProjectName());
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Tasks, Paint, Inventory, Notes
    }
}
