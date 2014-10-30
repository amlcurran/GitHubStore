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

    public boolean hasARelease() {
        return releases.size() > 0;
    }

    public Release getLatestRelease() {
        return releases.get(0);
    }
}
