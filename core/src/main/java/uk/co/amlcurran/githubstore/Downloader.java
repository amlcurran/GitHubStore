package uk.co.amlcurran.githubstore;

public interface Downloader {

    void downloadApk(Release release, int apkIndex);
}
