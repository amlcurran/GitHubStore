package uk.co.amlcurran.githubstore.release;

import java.util.List;

public class ReleaseCollection {
    private final List<Release> releases;

    public ReleaseCollection(List<Release> releases) {
        this.releases = releases;
    }

    public List<Release> getAll() {
        return releases;
    }
}
