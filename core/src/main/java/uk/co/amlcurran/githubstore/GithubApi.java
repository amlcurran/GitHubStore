package uk.co.amlcurran.githubstore;

import java.util.List;

public class GithubApi {

    private final HttpClient httpClient;
    private final JsonConverter jsonConverter;

    public GithubApi(HttpClient httpClient, JsonConverter jsonConverter) {
        this.httpClient = httpClient;
        this.jsonConverter = jsonConverter;
    }

    public List<Release> getReleases(final ResultListener<List<Release>> releaseListener) {
        httpClient.get(GithubUrls.RELEASES_URL, new HttpClient.HttpClientListener<String>() {
            @Override
            public void success(String result) {
                releaseListener.received(jsonConverter.convertReleases(result));
            }

            @Override
            public void failure(Exception exception) {

            }
        });
        return null;
    }

    public interface ResultListener<ReturnType> {
        void received(ReturnType result);
    }

}
