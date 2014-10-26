package uk.co.amlcurran.githubstore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReleaseListView {
    private final ReleaseAdapter releasesAdapter;
    private final List<Release> releaseList = new ArrayList<Release>();
    private final Listener listener;
    private int downloadingIndex = -1;

    public ReleaseListView(View view, ReleaseListView.Listener listener) {
        this.listener = listener;
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
        downloadingIndex = index;
        releasesAdapter.notifyItemChanged(index);
    }

    public interface Listener {
        void downloadRelease(Release release);
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
            releaseViewHolder.tagText.setText(release.getReleaseName());
            releaseViewHolder.bodyText.setText(release.getBody());
            if (i == downloadingIndex) {
                releaseViewHolder.downloadButton.setDownloading();
            }
        }

        @Override
        public int getItemCount() {
            return releaseList.size();
        }

    }

    private static class ReleaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tagText;
        private final TextView bodyText;
        private final Listener releaseSelectedListener;
        private final DownloadButton downloadButton;
        private Release release;

        public ReleaseViewHolder(View itemView, Listener releaseSelectedListener) {
            super(itemView);
            this.releaseSelectedListener = releaseSelectedListener;
            tagText = (TextView) itemView.findViewById(android.R.id.text1);
            bodyText = ((TextView) itemView.findViewById(android.R.id.text2));
            downloadButton = (DownloadButton) itemView.findViewById(R.id.button_download);
            downloadButton.setOnClickListener(onClickListener);
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 releaseSelectedListener.downloadRelease(release);
            }
        };

    }
}
