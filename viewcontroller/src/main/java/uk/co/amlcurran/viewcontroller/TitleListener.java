package uk.co.amlcurran.viewcontroller;

public interface TitleListener {

    public static final TitleListener NONE = new TitleListener() {
        @Override
        public void titleChanged(CharSequence title) {

        }

        @Override
        public void titleRemoved() {

        }
    };

    void titleChanged(CharSequence title);

    void titleRemoved();
}
