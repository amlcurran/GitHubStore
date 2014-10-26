package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.viewcontroller.ViewController;

public class ErrorViewController implements ViewController {
    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.view_controller_error, viewGroup, false);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pushed() {

    }

    @Override
    public void popped() {

    }
}
