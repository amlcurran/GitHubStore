package uk.co.amlcurran.githubstore.release;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.githubstore.R;

public class ReleaseListView {
    private final LegacyReleaseAdapter releasesAdapter;
    private final List<Release> releaseList = new ArrayList<Release>();
    private final List<Release> downloadedItems = new ArrayList<Release>();
    private final List<Release> downloadingItems = new ArrayList<Release>();
    private final TextView latestVersionText;
    private final DownloadButton latestDownloadButton;
    private final Resources resources;
    private final RecyclerView legacyReleasesListView;
    private final TextSwitcher legacyToggle;
    private final View latestVersionChip;

    public ReleaseListView(View view, Listener listener, Resources resources) {
        this.resources = resources;
        releasesAdapter = new LegacyReleaseAdapter(listener, resources);
        legacyReleasesListView = ((RecyclerView) view.findViewById(R.id.releases_list));
        legacyReleasesListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        legacyReleasesListView.setAdapter(releasesAdapter);
        legacyReleasesListView.setVisibility(View.GONE);
        latestVersionText = ((TextView) view.findViewById(R.id.releases_latest_version));
        latestVersionChip = view.findViewById(R.id.releases_latest_version_chip);
        latestVersionChip.setVisibility(View.GONE);
        latestDownloadButton = ((DownloadButton) view.findViewById(R.id.releases_latest_version_dl));
        latestDownloadButton.setListener(new LatestDownloadButtonListener(listener));

        legacyToggle = ((TextSwitcher) view.findViewById(R.id.release_toggle_legacy));
        legacyToggle.setFactory(new ToggleLegacyViewFactory(view.getContext(), legacyToggle));
        legacyToggle.setText(resources.getString(R.string.show_old_versions));
        legacyToggle.setOnClickListener(new ToggleLegacyListener(legacyReleasesListView, legacyToggle, resources));
        legacyToggle.setInAnimation(new AlphaAnimation(0, 1));
        legacyToggle.setOutAnimation(new AlphaAnimation(1, 0));
        legacyToggle.setVisibility(View.GONE);
    }

    public void downloadingAsset(Release release, int apkIndex) {
        int index = releaseList.indexOf(release);
        downloadingItems.add(release);
        if (index > 0) {
            releasesAdapter.notifyItemChanged(index - 1);
        } else {
            latestDownloadButton.setDownloading();
        }
    }

    public void downloadedAsset(Release release, int apkIndex) {
        int index = releaseList.indexOf(release);
        downloadedItems.add(release);
        downloadingItems.remove(release);
        if (index > 0) {
            releasesAdapter.notifyItemChanged(index - 1);
        } else {
            latestDownloadButton.setDownloaded();
        }
    }

    public void setReleases(ReleaseCollection result) {
        releaseList.clear();
        releaseList.addAll(result.getAll());
    }

    public void showLatestRelease(Release latestRelease) {
        latestVersionText.setText(formatTitle(latestRelease));
        latestVersionChip.setVisibility(View.VISIBLE);
    }

    public void showLegacyReleases(List<Release> legacyReleases) {
        releasesAdapter.setLegacyReleases(legacyReleases);
        legacyToggle.setVisibility(View.VISIBLE);
    }

    public void hideLegacyReleases() {
        legacyToggle.setVisibility(View.GONE);
    }

    public interface Listener {
        void downloadRelease(Release release);

        void openApk(Release release);
    }

    private CharSequence formatTitle(Release release) {
        if (TextUtils.isEmpty(release.getReleaseName())) {
            return release.getTagName();
        } else {
            return release.getReleaseName();
        }
    }

    private class LegacyReleaseAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {

        private final Listener listener;
        private final Resources resources;
        private final List<Release> legacyReleases = new ArrayList<Release>();

        public LegacyReleaseAdapter(Listener listener, Resources resources) {
            this.listener = listener;
            this.resources = resources;
        }

        public void setLegacyReleases(List<Release> releases) {
            int oldSize = legacyReleases.size();
            legacyReleases.clear();
            notifyItemRangeRemoved(0, oldSize);
            legacyReleases.addAll(releases);
            notifyItemRangeInserted(0, releases.size());
        }

        @Override
        public ReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemViewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_release, viewGroup, false);
            return new ReleaseViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(ReleaseViewHolder releaseViewHolder, int position) {
            Release release = legacyReleases.get(position);
            releaseViewHolder.release = release;
            releaseViewHolder.tagText.setText(formatTitle(release));
            releaseViewHolder.bodyText.setText(formatDescription(release));
            if (isDownloading(release)) {
                releaseViewHolder.downloadButton.setDownloading();
            } else if (isDownloaded(release)) {
                releaseViewHolder.downloadButton.setDownloaded();
            }
        }

        private String formatDescription(Release release) {
            if (TextUtils.isEmpty(release.getBody())) {
                return resources.getString(R.string.no_body);
            }
            return release.getBody();
        }

        private boolean isDownloaded(Release release) {
            return downloadedItems.contains(release);
        }

        private boolean isDownloading(Release release) {
            return downloadingItems.contains(release);
        }

        @Override
        public int getItemCount() {
            return legacyReleases.size();
        }

    }

    private static class ReleaseViewHolder extends RecyclerView.ViewHolder {
        final TextView tagText;
        final TextView bodyText;
        final DownloadButton downloadButton;
        Release release;

        public ReleaseViewHolder(View itemView, final Listener releaseSelectedListener) {
            super(itemView);
            tagText = (TextView) itemView.findViewById(android.R.id.text1);
            bodyText = ((TextView) itemView.findViewById(android.R.id.text2));
            downloadButton = (DownloadButton) itemView.findViewById(R.id.button_download);
            downloadButton.setListener(new DownloadButton.Listener() {

                @Override
                public void requestDownload() {
                    releaseSelectedListener.downloadRelease(release);
                }

                @Override
                public void openApk() {
                    releaseSelectedListener.openApk(release);
                }
            });
        }

    }

    private class LatestDownloadButtonListener implements DownloadButton.Listener {

        private final Listener listener;

        public LatestDownloadButtonListener(Listener listener) {
            this.listener = listener;
        }

        @Override
        public void requestDownload() {
            listener.downloadRelease(releaseList.get(0));
        }

        @Override
        public void openApk() {
            listener.openApk(releaseList.get(0));
        }

    }

}
