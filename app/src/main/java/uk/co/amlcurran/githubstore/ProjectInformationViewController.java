package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.viewcontroller.ViewController;

public class ProjectInformationViewController implements ViewController {

    private final GithubApi githubApi;
    private final BasicProjectItem basicProjectItem;
    private final ReleaseListViewController releaseListViewController;
    private ProjectInformationView projectInformationView;
    private AsyncTask getProject;

    public ProjectInformationViewController(GithubApi githubApi, BasicProjectItem basicProjectItem, ReleaseListViewController releaseListViewController) {
        this.githubApi = githubApi;
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
        getProject = githubApi.getProject(basicProjectItem, new GithubApi.ResultListener<Project>() {

            @Override
            public void received(Project result) {
                projectInformationView.updateProjectTitle(result.getProjectName());
                projectInformationView.updateOwner(result.getOwnerName());
                projectInformationView.updateDescription(result.getDescription());
            }

        });
    }

    @Override
    public void stop() {
        releaseListViewController.stop();
        if (getProject != null) {
            getProject.cancel();
        }
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
