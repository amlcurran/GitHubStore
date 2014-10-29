package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import uk.co.amlcurran.viewcontroller.TransitionManager;


public class BasicQueryActivity extends ActionBarActivity {

    private GithubApi githubApi;
    private TransitionManager transitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);
        githubApi = new GithubApi(new VolleyClient(Volley.newRequestQueue(BasicQueryActivity.this)), new GsonJsonConverter(), new ViewControllerErrorListener());
        transitionManager = new TransitionManager(BasicQueryActivity.this, ((ViewGroup) findViewById(R.id.content)));

        showReleases();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_query, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showReleases() {
        ReleaseInfoRepository releaseInfoRepository = new InMemoryReleaseInfoRepository();
        Downloader downloader = new DownloadServiceDownloader((DownloadManager) getSystemService(DOWNLOAD_SERVICE), releaseInfoRepository);
        Toaster toaster = new SimpleToaster(this);
        Installer installer = new AndroidInstaller(this, releaseInfoRepository);
        BasicProjectItem basicProjectItem = new BasicProjectItem("amlcurran", "Messages");
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(githubApi, basicProjectItem, downloader, toaster, installer);
        transitionManager.push(new ProjectInformationViewController(githubApi, basicProjectItem, releaseListViewController));
    }

    private class ToastErrorListener implements GithubApi.ErrorListener {
        @Override
        public void apiError(Exception errorException) {
            Toast.makeText(BasicQueryActivity.this, errorException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewControllerErrorListener implements GithubApi.ErrorListener {
        @Override
        public void apiError(Exception errorException) {
            transitionManager.push(new ErrorViewController());
        }
    }

}
