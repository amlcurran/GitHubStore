package uk.co.amlcurran.githubstore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class GsonJsonConverter implements JsonConverter {

    @Override
    public List<Release> convertReleases(String json) {
        List<Release> releases = new ArrayList<Release>();

        JsonArray releasesArray = new JsonParser().parse(json).getAsJsonArray();
        int size = releasesArray.size();
        for (int i = 0; i < size; i++) {
            JsonObject releaseObject = releasesArray.get(i).getAsJsonObject();
            String tagName = releaseObject.get("tag_name").getAsString();
            String body = releaseObject.get("body").getAsString();
            Release release = new Release(tagName, body);
            releases.add(release);
        }

        return releases;
    }
}
