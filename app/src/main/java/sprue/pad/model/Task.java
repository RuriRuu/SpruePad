package sprue.pad.model;

public class Task {
    private String ID;
    private String task;

    public Task(String ID ,String task ) {
        this.ID = ID;
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
