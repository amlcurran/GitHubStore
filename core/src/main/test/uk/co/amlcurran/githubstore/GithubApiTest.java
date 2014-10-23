package uk.co.amlcurran.githubstore;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GithubApiTest {

    private FakeHttpClient fakeHttpClient;
    private FakeJsonConverter fakeJsonConverter;
    private GithubApi githubApi;

    @Before
    public void setUp() throws Exception {
        fakeHttpClient = new FakeHttpClient();
        fakeJsonConverter = new FakeJsonConverter();
        githubApi = new GithubApi(fakeHttpClient, fakeJsonConverter);
    }

    @Test
    public void whenGetReleasesIsCalled_TheHttpClientIsQueriedWithTheCorrectUrl() {
        githubApi.getReleases();

        assertThat(fakeHttpClient.get_param, is(GithubUrls.RELEASES_URL));
    }

    @Test
    public void whenTheReleasesResponseReturns_TheJsonConverterReceivesTheResult() {
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

    private class FakeJsonConverter implements JsonConverter {
        public String convert_param;

        @Override
        public List<Release> convertReleases(String json) {
            convert_param = json;
            return null;
        }

    }
}