package uk.co.amlcurran.githubstore;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GithubApiTest {

    private static final GithubApi.ErrorListener UNUSED_ERROR_LISTENER = null;
    private FakeHttpClient fakeHttpClient;
    private FakeJsonConverter fakeJsonConverter;
    private GithubApi githubApi;

    @Before
    public void setUp() throws Exception {
        fakeHttpClient = new FakeHttpClient();
        fakeJsonConverter = new FakeJsonConverter();
        githubApi = new GithubApi(fakeHttpClient, fakeJsonConverter, UNUSED_ERROR_LISTENER);
    }

    @Test
    public void whenGetReleasesIsCalled_TheHttpClientIsQueriedWithTheCorrectUrl() {
        githubApi.getReleases(null, new NoOperationResultListener());

        assertThat(fakeHttpClient.get_param, is(UrlBuilder.RELEASES_URL));
    }

    @Test
    public void whenTheReleasesResponseReturns_TheJsonConverterReceivesTheResult() {
        githubApi.getReleases(null, new NoOperationResultListener());

        assertThat(fakeJsonConverter.convert_param, is(FakeHttpClient.GET_RETURN_VALUE));
    }

    private static class NoOperationResultListener implements GithubApi.ResultListener<List<Release>> {
        @Override
        public void received(List<Release> result) {

        }
    }

    private class FakeHttpClient implements HttpClient {
        public static final String GET_RETURN_VALUE = "{ 'json' : 'string' }";
        public String get_param;

        @Override
        public AsyncTask get(String url, HttpClientListener<String> clientListener) {
            get_param = url;
            clientListener.success(GET_RETURN_VALUE);
            return null;
        }
    }

    private class FakeJsonConverter implements JsonConverter {
        public String convert_param;

        @Override
        public List<Release> convertReleases(String json) {
            convert_param = json;
            return null;
        }

        @Override
        public Project convertProject(String json) {
            return null;
        }

    }
}