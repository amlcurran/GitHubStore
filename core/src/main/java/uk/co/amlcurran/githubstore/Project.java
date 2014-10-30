package uk.co.amlcurran.githubstore;

public class Project {
    private final String projectName;
    private final String ownerName;
    private final String description;
    private final String releasesUrl;

    public Project(String projectName, String ownerName, String description, String releasesUrl) {
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.description = description;
        this.releasesUrl = releasesUrl;
    }

    public String getReleasesUrl() {
        return releasesUrl;
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
