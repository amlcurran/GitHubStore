package uk.co.amlcurran.githubstore;

import java.util.Collections;
import java.util.List;

public class GithubApi {

    private final HttpClient httpClient;
    private final JsonConverter jsonConverter;
    private final ErrorListener errorListener;
    private final UrlBuilder urlBuilder = new UrlBuilder();

    public GithubApi(HttpClient httpClient, JsonConverter jsonConverter, ErrorListener errorListener) {
        this.httpClient = httpClient;
        this.jsonConverter = jsonConverter;
        this.errorListener = errorListener;
    }

    public AsyncTask getReleases(BasicProjectItem basicProjectItem, final ResultListener<List<Release>> releaseListener) {
        return httpClient.get(urlBuilder.getReleasesUrl(basicProjectItem), new HttpClient.HttpClientListener<String>() {
            @Override
            public void success(String result) {
                List<Release> releases = jsonConverter.convertReleases(result);
                Collections.sort(releases, new RecentFirstComparator());
                releaseListener.received(releases);
            }

            @Override
            public void failure(Exception exception) {
                errorListener.apiError(exception);
            }
        });
    }

    public AsyncTask getProject(BasicProjectItem basicProjectItem, final ResultListener<Project> resultListener) {
        return httpClient.get(urlBuilder.projectUrl(basicProjectItem), new HttpClient.HttpClientListener<String>() {
            @Override
            public void success(String result) {
                Project projectResult = jsonConverter.convertProject(result);
                resultListener.received(projectResult);
            }

            @Override
            public void failure(Exception exception) {
                errorListener.apiError(exception);
            }
        });
    }

    public interface ResultListener<ReturnType> {
        void received(ReturnType result);
    }

    public interface ErrorListener {
        void apiError(Exception errorException);
    }

}
