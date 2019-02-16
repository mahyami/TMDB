package web_handlers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.SeriesModel;
import web_handlers.interfaces.ISeriesList;

public class VolleyHandler {
    private static VolleyHandler mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private VolleyHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHandler(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void getSeries(final ISeriesList callback, int pageNum) {
        String url = URLs.POPULAR_SERIES + pageNum;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("results");
                    List<SeriesModel> seriesModels = new ArrayList<>();
                    SeriesModel seriesModel;
                    JSONObject tempObject;
                    final int numberOfItemsInResp = array.length();

                    for (int i = 0; i < numberOfItemsInResp; i++) {
                        tempObject = array.getJSONObject(i);

                        JSONArray genreIdsJSON = tempObject.getJSONArray("genre_ids");
                        List<Integer> genresList = new ArrayList<>();
                        for (int j = 0; j < genreIdsJSON.length(); j++) {
                            genresList.add(genreIdsJSON.getInt(j));
                        }

                        seriesModel = new SeriesModel(tempObject.getInt("id"),
                                tempObject.getString("name"),
                                genresList,
                                tempObject.getString("original_language"),
                                tempObject.getString("poster_path"),
                                tempObject.getDouble("vote_average")
                        );

                        seriesModels.add(seriesModel);
                    }

                    callback.seriesListResponse(seriesModels, 1, "");


                } catch (Exception e) {
                    e.printStackTrace();
                    callback.seriesListResponse(null, -6, "");
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            callback.seriesListResponse(null, -1, "");
                        } else if (error instanceof ServerError) {
                            callback.seriesListResponse(null, -2, "");
                        } else if (error instanceof NetworkError) {
                            callback.seriesListResponse(null, -3, "");
                        } else {
                            callback.seriesListResponse(null, -5, "");
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy( 10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        addToRequestQueue(jsonRequest);
    }

}
