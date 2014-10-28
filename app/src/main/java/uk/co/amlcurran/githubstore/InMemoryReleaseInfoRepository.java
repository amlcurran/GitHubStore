package uk.co.amlcurran.githubstore;

import android.util.SparseArray;

import java.net.URI;

class InMemoryReleaseInfoRepository implements ReleaseInfoRepository {

    private SparseArray<URI> store = new SparseArray<URI>();

    @Override
    public void storeDownloadedRelease(Release release, URI apkUri) {
        store.put(release.getId(), apkUri);
    }

    @Override
    public URI getDownloadedRelease(Release release) {
        return store.get(release.getId());
    }
}
