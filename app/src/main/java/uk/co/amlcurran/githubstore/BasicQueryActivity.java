package uk.co.amlcurran.githubstore;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.amlcurran.githubstore.core.AccessStore;
import uk.co.amlcurran.githubstore.core.AuthenticationCallback;


public class BasicQueryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_query);

        AccessStore accessStore = new AccessStore();
        accessStore.authenticate(clientId(), clientSecret(), new AuthenticationCallback() {});
    }

    private String clientSecret() {
        return Secrets.CLIENT_SECRET;
    }

    private String clientId() {
        return Secrets.CLIENT_ID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_query, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
