package sprue.pad.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {

    public String projectName;
    public String brand;
    public String scale;
    public String status;
    public String description;
    private Map<String, String> Tasks;
    private Map<String, Integer> Inventory;
    private Map<Integer, String> Notes;

    public Project() {
    }

    public Project(String projectName, String brand, String scale, String status, String description) {
        this.projectName = projectName;
        this.brand = brand;
        this.scale = scale;
        this.status = status;
        this.description = description;
        this.Tasks = null;
        this.Inventory = null;
        this.Notes = null;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getTasks() {
        return Tasks;
    }

    public void setTasks(Map<String, String> tasks) {
        Tasks = tasks;
    }

    public Map<String, Integer> getInventory() {
        return Inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        Inventory = inventory;
    }

    public Map<Integer, String> getNotes() {
        return Notes;
    }

    public void setNotes(Map<Integer, String> notes) {
        Notes = notes;
    }
}
