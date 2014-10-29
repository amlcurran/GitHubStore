package uk.co.amlcurran.githubstore;

import java.util.List;

public interface JsonConverter {
    List<Release> convertReleases(String json);

    Project convertProject(String json);
}
