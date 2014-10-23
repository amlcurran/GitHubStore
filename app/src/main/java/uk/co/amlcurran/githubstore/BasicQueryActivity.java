package uk.co.amlcurran.githubstore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

import com.android.volley.toolbox.Volley;


public class BasicQueryActivity extends ActionBarActivity {

    private GithubApi api;
    private TransitionManager transitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);
        api = new GithubApi(new VolleyClient(Volley.newRequestQueue(this)), new GsonJsonConverter());
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
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(api);
        transitionManager.push(releaseListViewController);
    }

}
