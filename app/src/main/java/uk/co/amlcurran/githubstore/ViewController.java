package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ViewController {
    View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    void start();

    void stop();

    static final ViewController NONE = new ViewController() {
        @Override
        public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }
    };
}
