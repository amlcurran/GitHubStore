package uk.co.amlcurran.githubstore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class DownloadButton extends FrameLayout {

    private final View button;
    private final View progress;

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
        button = findViewById(R.id.download_button);
        progress = findViewById(R.id.download_progress);
    }

    public void setDownloading() {
        button.setVisibility(GONE);
        progress.setVisibility(VISIBLE);
    }
}
