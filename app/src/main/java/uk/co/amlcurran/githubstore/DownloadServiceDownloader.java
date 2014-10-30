package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.net.URI;

import uk.co.amlcurran.githubstore.release.ApkAsset;
import uk.co.amlcurran.githubstore.release.Downloader;
import uk.co.amlcurran.githubstore.release.Release;
import uk.co.amlcurran.githubstore.release.ReleaseInfoRepository;

class DownloadServiceDownloader implements Downloader {
    private final DownloadManager downloadManager;
    private final ReleaseInfoRepository releaseInfoRepository;

    public DownloadServiceDownloader(DownloadManager downloadManager, ReleaseInfoRepository releaseInfoRepository) {
        this.downloadManager = downloadManager;
        this.releaseInfoRepository = releaseInfoRepository;
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
                    releaseInfoRepository.storeDownloadedRelease(release, getUriFromCursor(cursor));
                    listener.downloadedApk(release, apkIndex);
                }
            }
        });
    }

    private URI getUriFromCursor(Cursor cursor) {
        cursor.moveToFirst();
        String path = getString(cursor, DownloadManager.COLUMN_LOCAL_FILENAME);
        return URI.create(Uri.fromFile(new File(path)).toString());
    }

    private boolean completedAssetAtIndex(Cursor cursor, int index) {
        cursor.moveToPosition(index);
        String columnName = DownloadManager.COLUMN_STATUS;
        int status = getInt(cursor, columnName);
        return status == DownloadManager.STATUS_SUCCESSFUL;
    }

    private static int getInt(Cursor cursor, String columnName) {
        int statusColumnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(statusColumnIndex);
    }

    private static String getString(Cursor cursor, String columnName) {
        int statusColumnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(statusColumnIndex);
    }
}
