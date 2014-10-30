package uk.co.amlcurran.githubstore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.githubstore.release.ApkAsset;
import uk.co.amlcurran.githubstore.release.Release;

public class GsonJsonConverter implements JsonConverter {

    private static final String APK_CONTENT_TYPE = "application/vnd.android.package-archive";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final JsonParser jsonParser;

    public GsonJsonConverter() {
        jsonParser = new JsonParser();
    }

    @Override
    public List<Release> convertReleases(String json) {
        List<Release> releases = new ArrayList<Release>();

        JsonArray releasesArray = jsonParser.parse(json).getAsJsonArray();
        int size = releasesArray.size();
        for (int i = 0; i < size; i++) {
            JsonObject releaseJsonObject = releasesArray.get(i).getAsJsonObject();
            Release release = getReleaseFromJson(releaseJsonObject);
            JsonArray assetsArray = releaseJsonObject.getAsJsonArray("assets");
            List<ApkAsset> apkAssetList = createApkAssets(assetsArray);
            release.addApkAssets(apkAssetList);
            releases.add(release);
        }

        return releases;
    }

    @Override
    public Project convertProject(String json) {
        JsonObject projectObject = jsonParser.parse(json).getAsJsonObject();
        String projectName = projectObject.get("name").getAsString();
        String ownerName = projectObject.get("owner").getAsJsonObject().get("login").getAsString();
        String description = projectObject.get("description").getAsString();
        String releaseUrl = projectObject.get("releases_url").getAsString().replace("{/id}", "");
        return new Project(projectName, ownerName, description, releaseUrl);
    }

    private static List<ApkAsset> createApkAssets(JsonArray assetsArray) {
        List<ApkAsset> assets = new ArrayList<ApkAsset>();
        int size = assetsArray.size();
        for (int i = 0; i < size; i++) {
            JsonObject assetObject = assetsArray.get(i).getAsJsonObject();
            String content_type = assetObject.get("content_type").getAsString();
            if (APK_CONTENT_TYPE.equals(content_type)) {
                ApkAsset asset = createApkAsset(assetObject);
                assets.add(asset);
            }
        }
        return assets;
    }

    private static ApkAsset createApkAsset(JsonObject assetObject) {
        String url = assetObject.get("browser_download_url").getAsString();
        return new ApkAsset(URI.create(url));
    }

    private static Release getReleaseFromJson(JsonObject asJsonObject) {
        String tagName = asJsonObject.get("tag_name").getAsString();
        String body = asJsonObject.get("body").getAsString();
        String releaseName = asJsonObject.get("name").getAsString();
        int id = asJsonObject.get("id").getAsInt();
        String publishTimeString = asJsonObject.get("created_at").getAsString();
        Time publishTime = getTime(publishTimeString);
        return new Release(id, "Droidcon UK 2014", releaseName, tagName, body, publishTime);
    }

    private static Time getTime(String publishTimeString) {
        long millis = 0;
        try {
            millis = simpleDateFormat.parse(publishTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Time.fromMillis(millis);
    }

}
