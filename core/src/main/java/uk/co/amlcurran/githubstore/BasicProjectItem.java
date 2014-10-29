package uk.co.amlcurran.githubstore;

public class BasicProjectItem {
    private final String user;
    private final String projectName;

    public BasicProjectItem(String user, String projectName) {
        this.user = user;
        this.projectName = projectName;
    }

    public String getUser() {
        return user;
    }

    public String getProjectName() {
        return projectName;
    }

}
