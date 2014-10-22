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

    private class FakeHttpClient {
        public String get_param;

        public void get(String url) {
            get_param = url;
        }
    }

    public class GithubApi {

        private final FakeHttpClient fakeHttpClient;

        public GithubApi(FakeHttpClient fakeHttpClient) {
            this.fakeHttpClient = fakeHttpClient;
        }

        public void getReleases() {
            fakeHttpClient.get(GithubUrls.RELEASES_URL);
        }

    }
}