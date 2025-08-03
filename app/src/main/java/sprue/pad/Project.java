package sprue.pad;

public class Project {

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

}
