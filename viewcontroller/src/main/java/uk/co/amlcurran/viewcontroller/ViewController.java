package uk.co.amlcurran.viewcontroller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ViewController {
    View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup);

    void start();

    void stop();

    void pushed();

    void popped();

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

        @Override
        public void pushed() {

        }

        @Override
        public void popped() {

        }
    };
}
