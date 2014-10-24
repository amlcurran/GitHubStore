package uk.co.amlcurran.githubstore;

import java.util.ArrayList;
import java.util.List;

public class Release {
    private final String tagName;
    private final String body;
    private final List<ApkAsset> apkAssets = new ArrayList<ApkAsset>();

    public Release(String tagName, String body) {
        this.tagName = tagName;
        this.body = body;
    }

    public String getTagName() {
        return tagName;
    }

    public String getBody() {
        return body;
    }

    public List<ApkAsset> getApkAssets() {
        return apkAssets;
    }

    public void addApkAssets(List<ApkAsset> apkAssetList) {
        apkAssets.addAll(apkAssetList);
    }
}
