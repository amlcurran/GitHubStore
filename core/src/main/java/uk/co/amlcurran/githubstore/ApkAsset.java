package uk.co.amlcurran.githubstore;

import java.net.URI;

public class ApkAsset {
    private final String url;

    public ApkAsset(String url) {
        this.url = url;
    }

    public URI getURI() {
        return URI.create(url);
    }
}
