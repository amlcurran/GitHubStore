package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

class DownloadServiceDownloader implements Downloader {
    private final DownloadManager downloadManager;

    public DownloadServiceDownloader(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    @Override
    public void downloadApk(final Release release, final int apkIndex, final Downloader.Listener listener) {
        ApkAsset apkAsset = release.getApkAssets().get(apkIndex);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkAsset.getURI().toString()));
        request.setTitle(release.getProjectName());
        request.setDescription(release.getReleaseName());
        long id = downloadManager.enqueue(request);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        final Cursor cursor = downloadManager.query(query);
        // STOPSHIP: LEAKY
        cursor.registerContentObserver(new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                cursor.requery();
                if (cursor.getCount() == 1 && completedAssetAtIndex(cursor, 0)) {
                    listener.downloadedApk(release, apkIndex);
                }
            }
        });
    }

    private boolean completedAssetAtIndex(Cursor cursor, int index) {
        cursor.moveToPosition(index);
        int statusColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(statusColumnIndex);
        return status == DownloadManager.STATUS_SUCCESSFUL;
    }
}
