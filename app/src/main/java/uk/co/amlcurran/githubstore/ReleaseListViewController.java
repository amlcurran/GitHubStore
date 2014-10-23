package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.amlcurran.viewcontroller.ViewController;

public class ReleaseListViewController implements ViewController {

    private final GithubApi api;
    private AsyncTask getReleases;
    private ReleaseListView releaseListView;

    public ReleaseListViewController(GithubApi api) {
        this.api = api;
    }

    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.view_controller_release_list, viewGroup, false);
        releaseListView = new ReleaseListView(view);
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
}
