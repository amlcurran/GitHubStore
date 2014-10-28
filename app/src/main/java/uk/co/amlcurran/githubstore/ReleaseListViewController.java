package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.amlcurran.viewcontroller.ViewController;

public class ReleaseListViewController implements ViewController {

    private final GithubApi api;
    private final Downloader downloader;
    private final Installer installer;
    private final Toaster toaster;
    private AsyncTask getReleases;
    private ReleaseListView releaseListView;

    public ReleaseListViewController(GithubApi api, Downloader downloader, Toaster toaster, Installer installer) {
        this.api = api;
        this.downloader = downloader;
        this.toaster = toaster;
        this.installer = installer;
    }

    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.view_controller_release_list, viewGroup, false);
        releaseListView = new ReleaseListView(view, listener);
        return view;
    }

    @Override
    public void start() {
        getReleases = api.getReleases(new GithubApi.ResultListener<List<Release>>() {
            @Override
            public void received(List<Release> result) {
                releaseListView.removeAllReleases();
                releaseListView.addReleases(result);
            }
        });
    }

    @Override
    public void stop() {
        if (getReleases != null) {
            getReleases.cancel();
        }
    }

    @Override
    public void pushed() {

    }

    @Override
    public void popped() {

    }

    private ReleaseListView.Listener listener = new ReleaseListView.Listener() {

        @Override
        public void downloadRelease(Release release) {
            int size = release.getApkAssets().size();
            if (size == 1) {
                downloader.downloadApk(release, 0, new Downloader.Listener() {
                    @Override
                    public void downloadedApk(Release release, int apkIndex) {
                        releaseListView.downloadedAsset(release, apkIndex);
                    }
                });
                releaseListView.downloadingAsset(release, 0);
            } else if (size == 0) {
                toaster.noApksAvailable();
            } else {
                toaster.multipleApksAvailable();
            }
        }

        @Override
        public void openApk(Release release) {
            installer.install(release);
            toaster.toast("Open sesame");
        }
    };

}
