package uk.co.amlcurran.githubstore;

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
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(githubApi);
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
}
