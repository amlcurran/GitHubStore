package uk.co.amlcurran.githubstore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ReleaseListViewController implements ViewController {
    private final GithubApi api;

    public ReleaseListViewController(GithubApi api) {
        this.api = api;
    }

    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.view_controller_release_list, viewGroup, false);
    }

    @Override
    public void start() {
        api.getReleases(new GithubApi.ResultListener<List<Release>>() {
            @Override
            public void received(List<Release> result) {
                for (Release release : result) {
                    Log.d("TAG", release.getTagName());
                }
            }
        });
    }

    @Override
    public void stop() {

    }
}
