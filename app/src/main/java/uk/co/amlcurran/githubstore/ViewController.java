package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ViewController {
    View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    void start();

    void stop();
}
