package uk.co.amlcurran.githubstore;

import java.util.List;

import uk.co.amlcurran.githubstore.release.Release;

public interface JsonConverter {
    List<Release> convertReleases(String json);

    Project convertProject(String json);
}
