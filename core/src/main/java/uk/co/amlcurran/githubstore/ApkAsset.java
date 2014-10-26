package uk.co.amlcurran.githubstore;

import java.net.URI;

public class ApkAsset {
    private final URI uri;

    public ApkAsset(URI uri) {
        this.uri = uri;
    }

    public URI getURI() {
        return uri;
    }
}
