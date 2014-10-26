package uk.co.amlcurran.githubstore;

import java.util.ArrayList;
import java.util.List;

public class Release {
    private final String releaseName;
    private final String tagName;
    private final String body;
    private final List<ApkAsset> apkAssets = new ArrayList<ApkAsset>();
    private final String projectName;

    public Release(String projectName, String releaseName, String tagName, String body) {
        this.projectName = projectName;
        this.releaseName = releaseName;
        this.tagName = tagName;
        this.body = body;
    }

    public String getReleaseName() {
        return releaseName;
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

    public String getProjectName() {
        return projectName;
    }
}
