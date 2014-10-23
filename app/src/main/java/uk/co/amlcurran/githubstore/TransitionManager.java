package uk.co.amlcurran.githubstore;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class TransitionManager {

    private final Activity activity;
    private final ViewGroup viewGroup;

    @NonNull
    private ViewController currentViewController = ViewController.NONE;

    public TransitionManager(Activity activity, ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
    }

    public void push(@NonNull ViewController viewController) {
        currentViewController.stop();
        currentViewController = viewController;
        viewGroup.addView(viewController.inflateView(LayoutInflater.from(activity), viewGroup));
        viewController.start();
    }

    public void start() {
        currentViewController.start();
    }

    public void stop() {
        currentViewController.stop();
    }
}
