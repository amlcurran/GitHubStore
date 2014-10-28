package uk.co.amlcurran.githubstore;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReleaseListView {
    private final ReleaseAdapter releasesAdapter;
    private final List<Release> releaseList = new ArrayList<Release>();
    private final List<Release> downloadedItems = new ArrayList<Release>();
    private final List<Release> downloadingItems = new ArrayList<Release>();
    private final Listener listener;
    private final Resources resources;

    public ReleaseListView(View view, Listener listener, Resources resources) {
        this.listener = listener;
        this.resources = resources;
        releasesAdapter = new ReleaseAdapter();
        RecyclerView releasesListView = ((RecyclerView) view.findViewById(R.id.releases_list));
        releasesListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        releasesListView.setAdapter(releasesAdapter);
    }

    public void removeAllReleases() {
        int removedNumber = releaseList.size();
        releaseList.clear();
        releasesAdapter.notifyItemRangeRemoved(0, removedNumber);
    }

    public void addReleases(List<Release> result) {
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

    public interface Listener {
        void downloadRelease(Release release);

        void openApk(Release release);
    }

    private class ReleaseAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {

        @Override
        public ReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_release, viewGroup, false);
            return new ReleaseViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(ReleaseViewHolder releaseViewHolder, int i) {
            Release release = releaseList.get(i);
            releaseViewHolder.release = release;
            if (i == 0) {
                releaseViewHolder.tagText.setText(formatTitleAsLatest(release));
            } else {
                releaseViewHolder.tagText.setText(formatTitle(release));
            }
            releaseViewHolder.bodyText.setText(formatDescription(release));
            if (isDownloading(release)) {
                releaseViewHolder.downloadButton.setDownloading();
            } else if (isDownloaded(release)) {
                releaseViewHolder.downloadButton.setDownloaded();
            }
        }

        private CharSequence formatTitleAsLatest(Release release) {
            Truss truss = new Truss();
            truss.append(formatTitle(release))
                    .append(" ")
                    .pushSpan(new ForegroundColorSpan(resources.getColor(R.color.app_colour)))
                    .pushSpan(new RelativeSizeSpan(0.6f))
                    .append(resources.getString(R.string.latest))
                    .popSpan()
                    .popSpan();
            return truss.build();
        }

        private String formatDescription(Release release) {
            if (TextUtils.isEmpty(release.getBody())) {
                return resources.getString(R.string.no_body);
            }
            return release.getBody();
        }

        private CharSequence formatTitle(Release release) {
            if (TextUtils.isEmpty(release.getReleaseName())) {
                return release.getTagName();
            } else {
                return release.getReleaseName();
            }
        }

        private boolean isDownloaded(Release release) {
            return downloadedItems.contains(release);
        }

        private boolean isDownloading(Release release) {
            return downloadingItems.contains(release);
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
