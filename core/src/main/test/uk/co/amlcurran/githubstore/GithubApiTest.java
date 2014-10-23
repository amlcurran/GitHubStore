package uk.co.amlcurran.githubstore;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GithubApiTest {

    @Test
    public void whenGetReleasesIsCalled_TheHttpClientIsQueriedWithTheCorrectUrl() {
        FakeHttpClient fakeHttpClient = new FakeHttpClient();
        GithubApi githubApi = new GithubApi(fakeHttpClient);

        githubApi.getReleases();

        assertThat(GithubUrls.RELEASES_URL, is(fakeHttpClient.get_param));
    }

    private class FakeHttpClient implements HttpClient {
        public String get_param;

        @Override
        public void get(String url) {
            get_param = url;
        }
    }

    public class GithubApi {

        private final HttpClient httpClient;

        public GithubApi(HttpClient httpClient) {
            this.httpClient = httpClient;
        }

        public void getReleases() {
            httpClient.get(GithubUrls.RELEASES_URL);
        }

    }
}