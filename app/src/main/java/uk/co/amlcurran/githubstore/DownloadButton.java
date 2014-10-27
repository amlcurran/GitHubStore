package uk.co.amlcurran.githubstore;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DownloadButton extends FrameLayout {

    private final ImageView button;
    private final View progress;
    private Listener listener;

    public DownloadButton(Context context) {
        this(context, null, 0);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr, int whatever) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.download_button, this);
        button = ((ImageView) findViewById(R.id.download_button));
        button.setOnClickListener(new StartDownloadListener());
        progress = findViewById(R.id.download_progress);
    }

    public void setDownloading() {
        button.setVisibility(GONE);
        progress.setVisibility(VISIBLE);
    }

    public void setDownloaded() {
        button.setVisibility(VISIBLE);
        button.setImageResource(R.drawable.ic_done_grey600_36dp);
        button.setOnClickListener(new OpenApkListener());
        progress.setVisibility(GONE);
        button.setBackgroundColor(Color.GREEN);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void requestDownload();

        void openApk();
    }

    private class StartDownloadListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.requestDownload();
            }
        }
    }

    private class OpenApkListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.openApk();
            }
        }
    }
}
