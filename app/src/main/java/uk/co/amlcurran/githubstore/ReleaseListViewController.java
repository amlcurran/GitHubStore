package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReleaseListViewController implements ViewController {
    private final GithubApi api;
    private TextView releasesText;

    public ReleaseListViewController(GithubApi api) {
        this.api = api;
    }

    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.view_controller_release_list, viewGroup, false);
        releasesText = ((TextView) view.findViewById(R.id.textView));
        return view;
    }

    @Override
    public void start() {
        api.getReleases(new GithubApi.ResultListener<List<Release>>() {
            @Override
            public void received(List<Release> result) {
                StringBuilder builder = new StringBuilder();
                for (Release release : result) {
                    builder.append(release.getTagName())
                            .append("\n");
                }
                releasesText.setText(builder);
            }
        });
    }

    @Override
    public void stop() {

    }
}
