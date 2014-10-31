package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.android.volley.toolbox.Volley;

import uk.co.amlcurran.githubstore.release.Downloader;
import uk.co.amlcurran.githubstore.release.Installer;
import uk.co.amlcurran.githubstore.release.ReleaseInfoRepository;
import uk.co.amlcurran.githubstore.release.ReleaseListViewController;
import uk.co.amlcurran.viewcontroller.TransitionManager;


public class BasicQueryActivity extends ActionBarActivity {

    private GithubApi githubApi;
    private TransitionManager transitionManager;
    private ReleaseInfoRepository releaseInfoRepository;
    private Toaster toaster;
    private Installer installer;
    private Downloader downloader;

    public BasicQueryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);
        releaseInfoRepository = new InMemoryReleaseInfoRepository();
        toaster = new SimpleToaster(this);
        downloader = new DownloadServiceDownloader((DownloadManager) getSystemService(DOWNLOAD_SERVICE), releaseInfoRepository);
        installer = new AndroidInstaller(this, releaseInfoRepository);
        githubApi = new GithubApi(new VolleyClient(Volley.newRequestQueue(BasicQueryActivity.this)), new GsonJsonConverter(), new ViewControllerErrorListener());
        transitionManager = new TransitionManager(BasicQueryActivity.this, (ViewGroup) findViewById(R.id.content));

        showReleases(new BasicProjectItem("funkyandroid", "iosched"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        transitionManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        transitionManager.stop();
    }

    @Override
    public void onBackPressed() {
        if (!transitionManager.pop()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_query, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (validatedStructure(s)) {
                    showProject(s);
                    return true;
                } else {
                    toaster.incorrectSearchStructure();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private boolean validatedStructure(String s) {
        String[] split = s.split("/");
        boolean rightNumber = split.length == 2;
        return rightNumber && split[0].length() > 0 && split[1].length() > 0;
    }

    private void showProject(String searchQuery) {
        String[] strings = searchQuery.split("/");
        BasicProjectItem item = new BasicProjectItem(strings[0], strings[1]);
        showReleases(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_gs_update:
                showReleases(new BasicProjectItem("amlcurran", "GithubStore"));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showReleases(BasicProjectItem basicProjectItem) {
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(githubApi, downloader, toaster, installer);
        transitionManager.push(new ProjectInformationViewController(githubApi, basicProjectItem, releaseListViewController));
    }

    private class ViewControllerErrorListener implements GithubApi.ErrorListener {
        @Override
        public void apiError(Exception errorException) {
            transitionManager.push(new ErrorViewController());
        }
    }

}
