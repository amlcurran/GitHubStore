package uk.co.amlcurran.githubstore;

public interface HttpClient {
    void get(String url, HttpClientListener<String> clientListener);

    public interface HttpClientListener<ReturnType> {
        void success(ReturnType result);
        void failure(Exception exception);
    }
}
