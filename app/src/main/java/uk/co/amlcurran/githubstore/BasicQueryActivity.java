package uk.co.amlcurran.githubstore;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import java.net.URI;

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
        ReleaseInfoRepository releaseInfoRepository = new InMemoryReleaseInfoRepository();
        Downloader downloader = new DownloadServiceDownloader((DownloadManager) getSystemService(DOWNLOAD_SERVICE), releaseInfoRepository);
        Toaster toaster = new SimpleToaster(this);
        Installer installer = new AndroidInstaller(this, releaseInfoRepository);
        ReleaseListViewController releaseListViewController = new ReleaseListViewController(githubApi, downloader, toaster, installer);
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

        @Override
        public void toast(String toast) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private class InMemoryReleaseInfoRepository implements ReleaseInfoRepository {

        private SparseArray<URI> store = new SparseArray<URI>();

        @Override
        public void storeDownloadedRelease(Release release, URI apkUri) {
            store.put(release.getId(), apkUri);
        }

        @Override
        public URI getDownloadedRelease(Release release) {
            return store.get(release.getId());
        }
    }

    private class AndroidInstaller implements Installer {
        private final Context context;
        private final ReleaseInfoRepository releaseInfoRepository;

        public AndroidInstaller(Context context, ReleaseInfoRepository releaseInfoRepository) {
            this.context = context;
            this.releaseInfoRepository = releaseInfoRepository;
        }

        @Override
        public void install(Release release) {
            URI uri = releaseInfoRepository.getDownloadedRelease(release);
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(Uri.parse(uri.toString()), "application/vnd.android.package-archive");
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(installIntent);
        }
    }
}
