package uk.co.amlcurran.githubstore;

import android.view.View;
import android.widget.TextView;

public class ProjectInformationView {
    private final TextView titleText;
    private final TextView descriptionText;

    public ProjectInformationView(View view) {
        titleText = (TextView) view.findViewById(R.id.project_title);
        descriptionText = ((TextView) view.findViewById(R.id.project_description));
    }

    public void updateProjectTitle(String title) {
        titleText.setText(title);
    }

    public void updateOwner(String ownerName) {

    }

    public void updateDescription(String description) {
        descriptionText.setText(description);
    }
}
