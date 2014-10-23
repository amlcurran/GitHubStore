package uk.co.amlcurran.githubstore;

import com.google.gson.JsonObject;

public interface JsonConverter {
    JsonObject convert(String json);
}
