package uk.co.amlcurran.githubstore;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class VolleyClient implements HttpClient {
    private final RequestQueue requestQueue;

    public VolleyClient(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public AsyncTask get(String url, final HttpClientListener<String> clientListener) {
        StringRequest request = new StringRequest(url, new StringListener(clientListener), new DefaultErrorListener(clientListener));
        requestQueue.add(request);
        return new VolleyRequestAsyncTask(request);
    }

    private static class StringListener implements Response.Listener<String> {
        private final HttpClientListener<String> clientListener;

        public StringListener(HttpClientListener<String> clientListener) {
            this.clientListener = clientListener;
        }

        @Override
        public void onResponse(String s) {
            clientListener.success(s);
        }
    }

    private static class DefaultErrorListener implements Response.ErrorListener {
        private final HttpClientListener<String> clientListener;

        public DefaultErrorListener(HttpClientListener<String> clientListener) {
            this.clientListener = clientListener;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            clientListener.failure(volleyError);
        }
    }

    private class VolleyRequestAsyncTask implements AsyncTask {
        private final Request request;

        public VolleyRequestAsyncTask(Request request) {
            this.request = request;
        }

        @Override
        public void cancel() {
            request.cancel();
        }
    }
}
