package uk.co.amlcurran.githubstore;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DownloadButton extends FrameLayout {

    private final ImageView imageView;
    private final View progress;
    private final int translation;
    private final Interpolator interpolator;
    private Listener listener;
    private State myState = State.IDLE;

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
        imageView = ((ImageView) findViewById(R.id.download_button));
        setOnClickListener(new StartDownloadListener());
        progress = findViewById(R.id.download_progress);
        translation = getResources().getDimensionPixelOffset(R.dimen.button_translation);
        interpolator = AnimationUtils.loadInterpolator(getContext(), getInterpolator());
    }

    public void setDownloading() {
        if (myState != State.DOWNLOADING) {
            myState = State.DOWNLOADING;
            View view = imageView;
            animateViewOut(view);
            animateViewIn(progress);
        }
    }

    private int getInterpolator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.R.interpolator.fast_out_linear_in;
        } else {
            return android.R.interpolator.decelerate_cubic;
        }
    }

    public void setDownloaded() {
        if (myState != State.DOWNLOADED) {
            myState = State.DOWNLOADED;
            animateViewIn(imageView);
            animateViewOut(progress);
            imageView.setImageResource(R.drawable.ic_done_grey600_36dp);
            setOnClickListener(new OpenApkListener());
        }
    }

    private void animateViewIn(View view) {
        view.setTranslationX(translation);
        view.animate()
                .translationX(0)
                .alpha(1f)
                .withStartAction(new VisibilityVisibile(view));
    }

    private void animateViewOut(View view) {
        view.setTranslationX(0);
        view.animate()
                .setInterpolator(interpolator)
                .translationX(-translation)
                .alpha(0f)
                .withEndAction(new VisibilityGone(view));
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

    private enum State {
        IDLE, DOWNLOADED, DOWNLOADING

    }

    private class VisibilityGone implements Runnable {
        private final View view;

        public VisibilityGone(View view) {
            this.view = view;
        }

        @Override
        public void run() {
            view.setVisibility(GONE);
        }
    }

    private class VisibilityVisibile implements Runnable {
        private final View view;

        public VisibilityVisibile(View view) {
            this.view = view;
        }

        @Override
        public void run() {
            view.setVisibility(VISIBLE);
        }
    }
}
