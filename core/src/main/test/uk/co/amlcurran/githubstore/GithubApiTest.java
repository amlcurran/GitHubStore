package uk.co.amlcurran.githubstore;

import com.google.gson.JsonObject;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GithubApiTest {

    @Test
    public void whenGetReleasesIsCalled_TheHttpClientIsQueriedWithTheCorrectUrl() {
        FakeHttpClient fakeHttpClient = new FakeHttpClient();
        FakeJsonConverter fakeJsonConverter = new FakeJsonConverter();
        GithubApi githubApi = new GithubApi(fakeHttpClient, fakeJsonConverter);

        githubApi.getReleases();

        assertThat(fakeHttpClient.get_param, is(GithubUrls.RELEASES_URL));
    }

    @Test
    public void whenTheReleasesResponseReturns_TheJsonConverterReceivesTheResult() {
        FakeHttpClient fakeHttpClient = new FakeHttpClient();
        FakeJsonConverter fakeJsonConverter = new FakeJsonConverter();
        GithubApi githubApi = new GithubApi(fakeHttpClient, fakeJsonConverter);

        githubApi.getReleases();

        assertThat(fakeJsonConverter.convert_param, is(FakeHttpClient.GET_RETURN_VALUE));
    }

    private class FakeHttpClient implements HttpClient {
        public static final String GET_RETURN_VALUE = "{ 'json' : 'string' }";
        public String get_param;

        @Override
        public String get(String url) {
            get_param = url;
            return GET_RETURN_VALUE;
        }
    }

    public class GithubApi {

        private final HttpClient httpClient;
        private final FakeJsonConverter fakeJsonConverter;

        public GithubApi(HttpClient httpClient, FakeJsonConverter fakeJsonConverter) {
            this.httpClient = httpClient;
            this.fakeJsonConverter = fakeJsonConverter;
        }

        public void getReleases() {
            String result = httpClient.get(GithubUrls.RELEASES_URL);
            fakeJsonConverter.convert(result);
        }

    }

    private class FakeJsonConverter {
        public String convert_param;

        public JsonObject convert(String json) {
            convert_param = json;
            return null;
        }

    }
}