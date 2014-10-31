package uk.co.amlcurran.githubstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.amlcurran.githubstore.release.ReleaseListViewController;
import uk.co.amlcurran.viewcontroller.Titleable;
import uk.co.amlcurran.viewcontroller.ViewController;

public class ProjectInformationViewController implements ViewController, Titleable {

    private final GithubApi githubApi;
    private final BasicProjectItem basicProjectItem;
    private final ReleaseListViewController releaseListViewController;
    private final String title;
    private ProjectInformationView projectInformationView;
    private AsyncTask getProject;

    public ProjectInformationViewController(GithubApi githubApi, BasicProjectItem basicProjectItem, ReleaseListViewController releaseListViewController) {
        this.githubApi = githubApi;
        this.basicProjectItem = basicProjectItem;
        this.releaseListViewController = releaseListViewController;
        this.title = basicProjectItem.getUser() + "/" + basicProjectItem.getProjectName();
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
        getProject = githubApi.getProject(basicProjectItem, new GithubApi.ResultListener<Project>() {

            @Override
            public void received(Project result) {
                projectInformationView.updateProjectTitle(result.getProjectName());
                projectInformationView.updateOwner(result.getOwnerName());
                projectInformationView.updateDescription(result.getDescription());
                releaseListViewController.loadReleases(result);
            }

        });
        releaseListViewController.start();
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

    @Override
    public CharSequence getTitle() {
        return title;
    }
}
