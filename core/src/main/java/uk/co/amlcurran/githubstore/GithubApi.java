package uk.co.amlcurran.githubstore;

import java.util.List;

public class GithubApi {

    private final HttpClient httpClient;
    private final JsonConverter jsonConverter;

    public GithubApi(HttpClient httpClient, JsonConverter jsonConverter) {
        this.httpClient = httpClient;
        this.jsonConverter = jsonConverter;
    }

    public List<Release> getReleases() {
        String result = httpClient.get(GithubUrls.RELEASES_URL);
        return jsonConverter.convertReleases(result);
    }

}
