package uk.co.amlcurran.githubstore;

import java.net.URI;

public interface ReleaseInfoRepository {
    void storeDownloadedRelease(Release release, URI apkUri);

    URI getDownloadedRelease(Release release);
}
