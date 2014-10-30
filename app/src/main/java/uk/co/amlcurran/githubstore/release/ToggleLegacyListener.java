package uk.co.amlcurran.githubstore.release;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;

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
            setLeftDrawable(R.drawable.ic_unfold_more);
        } else {
            legacyView.setVisibility(View.VISIBLE);
            legacyToggle.setText(resources.getString(R.string.hide_old_versions));
            setLeftDrawable(R.drawable.ic_unfold_less);
        }
    }

    private void setLeftDrawable(int left) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((TextView) legacyToggle.getCurrentView()).setCompoundDrawablesRelativeWithIntrinsicBounds(left, 0, 0, 0);
        } else {
            ((TextView) legacyToggle.getCurrentView()).setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0);
        }
    }
}
