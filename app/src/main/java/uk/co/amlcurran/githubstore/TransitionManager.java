package uk.co.amlcurran.githubstore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class TransitionManager {

    private final Activity activity;
    private final ViewGroup viewGroup;
    private ViewController currentViewController;

    public TransitionManager(Activity activity, ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
    }

    public void push(ViewController viewController) {
        currentViewController = viewController;
        viewGroup.addView(viewController.inflateView(LayoutInflater.from(activity), viewGroup));
        viewController.start();
    }

    public void start() {
        if (currentViewController != null) {
            currentViewController.start();
        }
    }

    public void stop() {
        if (currentViewController != null) {
            currentViewController.stop();
        }
    }
}
