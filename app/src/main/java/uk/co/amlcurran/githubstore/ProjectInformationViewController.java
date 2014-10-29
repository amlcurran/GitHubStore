package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.viewcontroller.ViewController;

public class ProjectInformationViewController implements ViewController {

    private final BasicProjectItem basicProjectItem;
    private final ReleaseListViewController releaseListViewController;
    private ProjectInformationView projectInformationView;

    public ProjectInformationViewController(BasicProjectItem basicProjectItem, ReleaseListViewController releaseListViewController) {
        this.basicProjectItem = basicProjectItem;
        this.releaseListViewController = releaseListViewController;
    }

    @Override
    public View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.view_controller_project_info, viewGroup, false);
        ViewGroup releasesHolder = (ViewGroup) view.findViewById(R.id.releases_view);
        releasesHolder.addView(releaseListViewController.inflateView(layoutInflater, viewGroup));
        projectInformationView = new ProjectInformationView(view);
        return view;
    }

    @Override
    public void start() {
        releaseListViewController.start();
        projectInformationView.updateProjectTitle(basicProjectItem.getTitle());
    }

    @Override
    public void stop() {
        releaseListViewController.stop();
    }

    @Override
    public void pushed() {
        releaseListViewController.pushed();
    }

    @Override
    public void popped() {
        releaseListViewController.popped();
    }

}
