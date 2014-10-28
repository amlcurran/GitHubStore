package uk.co.amlcurran.githubstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URI;

class AndroidInstaller implements Installer {
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
