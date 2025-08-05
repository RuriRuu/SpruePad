package sprue.pad.model;

public class Project  {

    public String projectName;
    public String brand;
    public String scale;
    public String status;
    public String description;

    public Project() {}
    public Project(String projectName, String brand, String scale, String status, String description){
        this.projectName = projectName;
        this.brand = brand;
        this.scale = scale;
        this.status = status;
        this.description = description;
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
}
