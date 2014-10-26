package uk.co.amlcurran.githubstore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GsonJsonConverter implements JsonConverter {

    private static final String APK_CONTENT_TYPE = "application/vnd.android.package-archive";

    @Override
    public List<Release> convertReleases(String json) {
        List<Release> releases = new ArrayList<Release>();

        JsonArray releasesArray = new JsonParser().parse(json).getAsJsonArray();
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
        return new Release(tagName, body);
    }

}
