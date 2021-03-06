package uk.co.amlcurran.githubstore.release;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import uk.co.amlcurran.githubstore.R;
import uk.co.amlcurran.viewcontroller.AnimateUtils;

public class DownloadButton extends FrameLayout {

    private static final int ROTATION_DEGREES = 360;
    private final ImageView imageView;
    private final View progress;
    private final TimeInterpolator interpolator;
    private final int animationDuration;
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
        if (!isInEditMode()) {
            interpolator = AnimateUtils.getInterpolator(context);
        } else {
            interpolator = null;
        }
        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        TypedArray array = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.selectableItemBackground },
                defStyleAttr, whatever);
        setBackground(array.getDrawable(0));
        array.recycle();
    }

    public void setDownloading() {
        if (myState != State.DOWNLOADING) {
            myState = State.DOWNLOADING;
            View view = imageView;
            animateViewOutWithRotation(view);
            animateViewIn(progress);
        }
    }

    private void animateViewOutWithRotation(View view) {
        float scaleEnd = 0.4f;
        view.animate()
                .setInterpolator(interpolator)
                .setDuration(animationDuration)
                .scaleX(scaleEnd)
                .rotationBy(ROTATION_DEGREES)
                .scaleY(scaleEnd)
                .alpha(0f)
                .withEndAction(new VisibilityGone(view));
    }

    public void setDownloaded() {
        if (myState != State.DOWNLOADED) {
            myState = State.DOWNLOADED;
            animateViewInWithRotation(imageView);
            animateViewOut(progress);
            imageView.setImageResource(R.drawable.ic_done_grey600_36dp);
            setOnClickListener(new OpenApkListener());
        }
    }

    private void animateViewInWithRotation(View view) {
        float scaleEnd = 0.4f;
        view.setScaleX(scaleEnd);
        view.setScaleY(scaleEnd);
        view.setAlpha(0);
        view.animate()
                .rotationBy(ROTATION_DEGREES)
                .setDuration(animationDuration)
                .scaleY(1)
                .scaleX(1)
                .alpha(1f)
                .withStartAction(new VisibilityVisibile(view));
    }

    private void animateViewIn(View view) {
        view.setScaleX(1);
        view.setScaleY(1);
        view.setAlpha(0);
        view.animate()
                .setDuration(animationDuration)
                .alpha(1f)
                .withStartAction(new VisibilityVisibile(view));
    }

    private void animateViewOut(View view) {
        float scaleEnd = 0.4f;
        view.animate()
                .setInterpolator(interpolator)
                .setDuration(animationDuration)
                .scaleX(scaleEnd)
                .scaleY(scaleEnd)
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
            view.setVisibility(INVISIBLE);
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
