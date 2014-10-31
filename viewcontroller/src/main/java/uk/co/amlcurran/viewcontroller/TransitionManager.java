package uk.co.amlcurran.viewcontroller;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.Deque;

public class TransitionManager {

    private final ViewGroup viewGroup;
    private final LayoutInflater layoutInflater;
    private final Deque<ViewController> backstack = new ArrayDeque<ViewController>();

    @NonNull
    private ViewController currentViewController = ViewController.NONE;
    private boolean hasStarted;

    public TransitionManager(Activity activity, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.layoutInflater = LayoutInflater.from(activity);
        setupLayoutAnimations(activity);
    }

    private void setupLayoutAnimations(Activity activity) {
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimator(LayoutTransition.APPEARING, getInAnimator());
        transition.setInterpolator(LayoutTransition.APPEARING, AnimateUtils.getInterpolator(activity));
        transition.setStartDelay(LayoutTransition.APPEARING, 0);
        transition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if (transitionType == LayoutTransition.APPEARING) {
                    view.setAlpha(0f);
                }
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {

            }
        });
        viewGroup.setLayoutTransition(transition);
    }

    private Animator getInAnimator() {
        AnimatorSet set = new AnimatorSet();
        Animator alpha = ObjectAnimator.ofFloat(null, "alpha", 0, 1);
        Animator translationX = ObjectAnimator.ofFloat(null, "translationX", 100, 0);
        set.playTogether(alpha, translationX);
        set.setDuration(100);
        return set;
    }

    public void push(@NonNull ViewController viewController) {
        if (currentViewController != ViewController.NONE) {
            backstack.push(currentViewController);
        }
        replace(viewController);
    }

    private void replace(ViewController viewController) {
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

    public boolean pop() {
        if (backstack.size() > 0) {
            ViewController controller = backstack.pop();
            replace(controller);
            return true;
        } else {
            return false;
        }
    }
}
