package uk.co.amlcurran.githubstore;

public class Project {
    private String projectName;
    private String ownerName;
    private String description;

    public Project(String projectName, String ownerName, String description) {
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getOwnerName() {
        // There's another domain object here
        return ownerName;
    }

    public String getDescription() {
        return description;
    }
}
