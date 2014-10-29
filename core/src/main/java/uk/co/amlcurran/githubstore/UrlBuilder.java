package uk.co.amlcurran.githubstore;

public class UrlBuilder {
    private static final String PROJECT_URL = "https://api.github.com/repos/%1$s/%2$s";
    static final String RELEASES_URL = "https://api.github.com/repos/%1$s/%2$s/releases";

    public UrlBuilder() {
    }

    String getReleasesUrl(BasicProjectItem basicProjectItem) {
        return String.format(RELEASES_URL, basicProjectItem.getUser(), basicProjectItem.getProjectName());
    }

    public String projectUrl(BasicProjectItem basicProjectItem) {
        return String.format(PROJECT_URL, basicProjectItem.getUser(), basicProjectItem.getProjectName());
    }
}