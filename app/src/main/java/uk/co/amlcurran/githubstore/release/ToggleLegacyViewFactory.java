package uk.co.amlcurran.githubstore.release;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import uk.co.amlcurran.githubstore.R;

class ToggleLegacyViewFactory implements ViewSwitcher.ViewFactory {
    private final Context context;
    private final TextSwitcher legacyToggle;

    public ToggleLegacyViewFactory(Context context, TextSwitcher legacyToggle) {
        this.context = context;
        this.legacyToggle = legacyToggle;
    }

    @Override
    public View makeView() {
        return LayoutInflater.from(context).inflate(R.layout.item_release_toggle, legacyToggle, false);
    }
}
