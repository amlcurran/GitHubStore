package uk.co.amlcurran.githubstore.release;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextSwitcher;

import uk.co.amlcurran.githubstore.R;

class ToggleLegacyListener implements View.OnClickListener {
    private final View legacyView;
    private final TextSwitcher legacyToggle;
    private final Resources resources;

    public ToggleLegacyListener(View legacyView, TextSwitcher legacyToggle, Resources resources) {
        this.legacyView = legacyView;
        this.legacyToggle = legacyToggle;
        this.resources = resources;
    }

    @Override
    public void onClick(View v) {
        if (legacyView.getVisibility() == View.VISIBLE) {
            legacyView.setVisibility(View.GONE);
            legacyToggle.setText(resources.getString(R.string.show_old_versions));
        } else {
            legacyView.setVisibility(View.VISIBLE);
            legacyToggle.setText(resources.getString(R.string.hide_old_versions));
        }
    }
}
