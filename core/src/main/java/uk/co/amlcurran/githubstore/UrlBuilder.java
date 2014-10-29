package uk.co.amlcurran.githubstore;

public class UrlBuilder {
    public UrlBuilder() {
    }

    String getReleasesUrl() {
        return GithubUrls.RELEASES_URL;
    }

    public String projectUrl(BasicProjectItem basicProjectItem) {
        return String.format(GithubUrls.PROJECT_URL, basicProjectItem.getUser(), basicProjectItem.getProjectName());
    }
}