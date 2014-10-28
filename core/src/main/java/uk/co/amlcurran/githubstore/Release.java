package uk.co.amlcurran.githubstore;

import java.util.ArrayList;
import java.util.List;

public class Release {
    private final String releaseName;
    private final String tagName;
    private final String body;
    private final List<ApkAsset> apkAssets = new ArrayList<ApkAsset>();
    private final int id;
    private final String projectName;
    private final Time time;

    public Release(int id, String projectName, String releaseName, String tagName, String body, Time creationTime) {
        this.id = id;
        this.projectName = projectName;
        this.releaseName = releaseName;
        this.tagName = tagName;
        this.body = body;
        this.time = creationTime;
    }

    public int getId() {
        return id;
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

    public Time getDate() {
        return time;
    }
}
