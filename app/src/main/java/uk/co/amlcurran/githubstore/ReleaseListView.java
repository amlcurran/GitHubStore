package uk.co.amlcurran.githubstore;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.amlcurran.githubstore.release.Release;
import uk.co.amlcurran.githubstore.release.ReleaseCollection;

public class ReleaseListView {
    private final ReleaseAdapter releasesAdapter;
    private final List<Release> releaseList = new ArrayList<Release>();
    private final List<Release> downloadedItems = new ArrayList<Release>();
    private final List<Release> downloadingItems = new ArrayList<Release>();
    private final Listener listener;
    private final Resources resources;
    private final TextView latestVersionText;

    public ReleaseListView(View view, Listener listener, Resources resources) {
        this.listener = listener;
        this.resources = resources;
        releasesAdapter = new ReleaseAdapter();
        RecyclerView releasesListView = ((RecyclerView) view.findViewById(R.id.releases_list));
        releasesListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        releasesListView.setAdapter(releasesAdapter);
        latestVersionText = ((TextView) view.findViewById(R.id.releases_latest_version));
    }

    private void removeAllReleases() {
        int removedNumber = releaseList.size();
        releaseList.clear();
        releasesAdapter.notifyItemRangeRemoved(0, removedNumber);
    }

    private void addReleases(List<Release> result) {
        releaseList.addAll(result);
        releasesAdapter.notifyItemRangeInserted(0, result.size());
    }

    public void downloadingAsset(Release release, int apkIndex) {
        int index = releaseList.indexOf(release);
        downloadingItems.add(release);
        releasesAdapter.notifyItemChanged(index);
    }

    public void downloadedAsset(Release release, int apkIndex) {
        int index = releaseList.indexOf(release);
        downloadedItems.add(release);
        downloadingItems.remove(release);
        releasesAdapter.notifyItemChanged(index);
    }

    public void setReleases(ReleaseCollection result) {
        removeAllReleases();
        addReleases(result.getAll());
        if (result.hasARelease()) {
            showLatestRelease(result.getLatestRelease());
        }
    }

    private void showLatestRelease(Release latestRelease) {
        latestVersionText.setText(formatTitle(latestRelease));
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

    private class ReleaseAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {

        private static final int ITEM_LATEST = 0;
        private static final int ITEM_OTHER = 1;

        @Override
        public ReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemViewType) {
            View view;
            if (itemViewType == ITEM_LATEST) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_release_latest, viewGroup, false);
            } else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_release, viewGroup, false);
            }
            return new ReleaseViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(ReleaseViewHolder releaseViewHolder, int position) {
            Release release = releaseList.get(position);
            releaseViewHolder.release = release;
            releaseViewHolder.tagText.setText(formatTitle(release));
            if (position == 0) {
                releaseViewHolder.bodyText.setText(formatDescriptionAsFirst(release));
            } else {
                releaseViewHolder.bodyText.setText(formatDescription(release));
            }
            if (isDownloading(release)) {
                releaseViewHolder.downloadButton.setDownloading();
            } else if (isDownloaded(release)) {
                releaseViewHolder.downloadButton.setDownloaded();
            }
        }

        private CharSequence formatDescriptionAsFirst(Release release) {
            Truss truss = new Truss();
            truss.pushSpan(new ForegroundColorSpan(resources.getColor(R.color.app_colour)))
                    .append(resources.getString(R.string.latest))
                    .popSpan()
                    .append(" - ")
                    .append(formatDescription(release));
            return truss.build();
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
        public int getItemViewType(int position) {
            return position == 0 ? ITEM_LATEST : ITEM_OTHER;
        }

        @Override
        public int getItemCount() {
            return releaseList.size();
        }

    }

    private static class ReleaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tagText;
        private final TextView bodyText;
        private final DownloadButton downloadButton;
        private Release release;

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
}
