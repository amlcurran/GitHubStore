package uk.co.amlcurran.githubstore;

import android.view.View;
import android.widget.TextView;

public class ProjectInformationView {
    private final View view;
    private final TextView titleText;

    public ProjectInformationView(View view) {
        this.view = view;
        titleText = (TextView) view.findViewById(R.id.project_title);
    }

    public void updateProjectTitle(String title) {
        titleText.setText(title);
    }
}
