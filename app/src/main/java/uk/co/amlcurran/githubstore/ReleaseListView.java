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
    private final View view;
    private final Listener listener;

    public ReleaseListView(View view, ReleaseListView.Listener listener) {
        this.view = view;
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

    public interface Listener {
        void releaseSelected(Release release);
    }

    private class ReleaseAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {

        @Override
        public ReleaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_2, viewGroup, false);
            return new ReleaseViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(ReleaseViewHolder releaseViewHolder, int i) {
            Release release = releaseList.get(i);
            releaseViewHolder.release = release;
            releaseViewHolder.tagText.setText(release.getTagName());
            releaseViewHolder.bodyText.setText(release.getBody());
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
        private Release release;

        public ReleaseViewHolder(View itemView, Listener releaseSelectedListener) {
            super(itemView);
            this.releaseSelectedListener = releaseSelectedListener;
            tagText = (TextView) itemView.findViewById(android.R.id.text1);
            bodyText = ((TextView) itemView.findViewById(android.R.id.text2));
            bodyText.setMaxLines(3);
            bodyText.setEllipsize(TextUtils.TruncateAt.END);
            itemView.setOnClickListener(onClickListener);
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 releaseSelectedListener.releaseSelected(release);
            }
        };

    }
}
