package sprue.pad.utility;

import java.util.List;

import sprue.pad.model.Project;

public interface OnProjectsLoadedListener {
    void onProjectsLoaded(List<Project> projects);
}
