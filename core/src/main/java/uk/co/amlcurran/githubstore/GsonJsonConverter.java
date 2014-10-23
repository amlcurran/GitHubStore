package uk.co.amlcurran.githubstore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonJsonConverter implements JsonConverter {

    private final Gson gson;

    public GsonJsonConverter() {
        gson = new Gson();
    }

    @Override
    public List<Release> convertReleases(String json) {
        Type collectionType = new TypeToken<List<Release>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }
}
