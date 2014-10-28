package uk.co.amlcurran.githubstore;

import android.content.Context;
import android.widget.Toast;

class SimpleToaster implements Toaster {
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
