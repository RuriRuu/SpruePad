package sprue.pad.model;

public class Task {
    private String ID;
    private String Task;

    public Task(String ID ,String task ) {
        this.ID = ID;
        this.Task = task;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        this.Task = task;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
