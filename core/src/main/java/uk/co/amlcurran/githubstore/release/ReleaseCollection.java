package uk.co.amlcurran.githubstore.release;

import java.util.ArrayList;
import java.util.List;

public class ReleaseCollection {
    private final List<Release> releases;
    private final List<Release> legacyReleases;

    public ReleaseCollection(List<Release> releases) {
        this.releases = releases;
        this.legacyReleases = new ArrayList<Release>();
        addLegacyReleases(releases);
    }

    private void addLegacyReleases(List<Release> releases) {
        if (releases.size() > 1) {
            for (int i = 1; i < releases.size(); i++) {
                legacyReleases.add(releases.get(i));
            }
        }
    }

    public boolean hasARelease() {
        return releases.size() > 0;
    }

    public Release getLatestRelease() {
        return releases.get(0);
    }

    public List<Release> getLegacyReleases() {
        return legacyReleases;
    }

    public List<Release> getAll() {
        return releases;
    }
}
