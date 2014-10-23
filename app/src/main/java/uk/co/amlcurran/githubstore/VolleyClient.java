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
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                clientListener.success(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                clientListener.failure(volleyError);
            }
        });
        requestQueue.add(request);
        return new VolleyRequestAsyncTask(request);
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
