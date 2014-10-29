package uk.co.amlcurran.githubstore;

public class BasicProjectItem {
    private String title;
    private final String user;
    private final String projectName;

    public BasicProjectItem(String title, String user, String projectName) {
        this.title = title;
        this.user = user;
        this.projectName = projectName;
    }

    public String getUser() {
        return user;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTitle() {
        return title;
    }
}
