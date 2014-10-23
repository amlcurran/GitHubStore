package uk.co.amlcurran.githubstore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReleaseListView {
    private final ReleaseAdapter releasesAdapter;
    private final List<Release> releaseList = new ArrayList<Release>();

    public ReleaseListView(View view) {
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

    private class ReleaseAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {
        @Override
        public ReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_2, viewGroup, false);
            return new ReleaseViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ReleaseViewHolder releaseViewHolder, int i) {
            Release release = releaseList.get(i);
            releaseViewHolder.tagText.setText(release.getTagName());
            releaseViewHolder.bodyText.setText(release.getBody());
        }

        @Override
        public int getItemCount() {
            return releaseList.size();
        }
    }

    private class ReleaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tagText;
        private final TextView bodyText;

        public ReleaseViewHolder(View itemView) {
            super(itemView);
            tagText = (TextView) itemView.findViewById(android.R.id.text1);
            bodyText = ((TextView) itemView.findViewById(android.R.id.text2));
            bodyText.setMaxLines(3);
            bodyText.setEllipsize(TextUtils.TruncateAt.END);
        }
    }
}
