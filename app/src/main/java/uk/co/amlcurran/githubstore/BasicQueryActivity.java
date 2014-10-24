package uk.co.amlcurran.githubstore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

import com.android.volley.toolbox.Volley;

import uk.co.amlcurran.viewcontroller.TransitionManager;


public class BasicQueryActivity extends ActionBarActivity {

    private GithubApi githubApi;
    private TransitionManager transitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);
        githubApi = new GithubApi(new VolleyClient(Volley.newRequestQueue(this)), new GsonJsonConverter());
        transitionManager = new TransitionManager(this, ((ViewGroup) findViewById(R.id.content)));

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

}
