package uk.co.amlcurran.viewcontroller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class TransitionManager {

    private final ViewGroup viewGroup;
    private final LayoutInflater layoutInflater;

    @NonNull
    private ViewController currentViewController = ViewController.NONE;
    private boolean hasStarted;

    public TransitionManager(Activity activity, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    public void push(@NonNull ViewController viewController) {
        currentViewController.popped();
        viewGroup.removeAllViews();
        viewGroup.addView(viewController.inflateView(layoutInflater, viewGroup));
        viewController.pushed();
        if (hasStarted) {
            viewController.start();
        }
        currentViewController = viewController;
    }

    public void start() {
        hasStarted = true;
        currentViewController.start();
    }

    public void stop() {
        hasStarted = false;
        currentViewController.stop();
    }
}
