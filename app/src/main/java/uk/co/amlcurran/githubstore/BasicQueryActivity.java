package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

    private void showReleases() {
        DownloadServiceDownloader downloader = new DownloadServiceDownloader((DownloadManager) getSystemService(DOWNLOAD_SERVICE));
        SimpleToaster toaster = new SimpleToaster(this);
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(githubApi, downloader, toaster);
        transitionManager.push(releaseListViewController);
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

    private class SimpleToaster implements Toaster {
        private final Context context;

        public SimpleToaster(Context context) {
            this.context = context;
        }

        @Override
        public void noApksAvailable() {
            Toast.makeText(context, R.string.no_apks, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void multipleApksAvailable() {
            Toast.makeText(context, R.string.multiple_apks, Toast.LENGTH_SHORT).show();
        }
    }
}
