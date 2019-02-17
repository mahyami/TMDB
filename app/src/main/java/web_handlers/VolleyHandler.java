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

import models.GenericModel;
import models.GenreModel;
import models.SeriesDetailModel;
import models.SeriesModel;
import web_handlers.interfaces.IGetGenresList;
import web_handlers.interfaces.IGetSeriesDetail;
import web_handlers.interfaces.IGetSeriesList;

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
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void getSeries(final IGetSeriesList callback, int pageNum) {
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

                    callback.getSeriesListResponse(seriesModels, 1, "");


                } catch (Exception e) {
                    e.printStackTrace();
                    callback.getSeriesListResponse(null, -6, "");
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            callback.getSeriesListResponse(null, -1, "");
                        } else if (error instanceof ServerError) {
                            callback.getSeriesListResponse(null, -2, "");
                        } else if (error instanceof NetworkError) {
                            callback.getSeriesListResponse(null, -3, "");
                        } else {
                            callback.getSeriesListResponse(null, -5, "");
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
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        addToRequestQueue(jsonRequest);
    }

    public void getGenres(final IGetGenresList callback) {
        String url = URLs.GENRES;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array = response.getJSONArray("genres");
                    List<GenreModel> genreModels = new ArrayList<>();
                    GenreModel genreModel;
                    JSONObject tempObject;
                    final int numberOfItemsInResp = array.length();

                    for (int i = 0; i < numberOfItemsInResp; i++) {
                        tempObject = array.getJSONObject(i);

                        genreModel = new GenreModel(tempObject.getInt("id"),
                                tempObject.getString("name")
                        );

                        genreModels.add(genreModel);
                    }

                    callback.getGenreListResponse(genreModels, 1, "");


                } catch (Exception e) {
                    e.printStackTrace();
                    callback.getGenreListResponse(null, -6, "");
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            callback.getGenreListResponse(null, -1, "");
                        } else if (error instanceof ServerError) {
                            callback.getGenreListResponse(null, -2, "");
                        } else if (error instanceof NetworkError) {
                            callback.getGenreListResponse(null, -3, "");
                        } else {
                            callback.getGenreListResponse(null, -5, "");
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
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addToRequestQueue(jsonRequest);
    }


    public void getSeriesDetails(final IGetSeriesDetail callback, int id) {
        String url = URLs.ROOT_URL + "/tv/" + id + URLs.SECOND_PART_OF_URL;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    SeriesDetailModel seriesDetailModel;
                    SeriesModel seriesModel;
                    List<GenreModel> genresList = new ArrayList<>();
                    List<GenericModel> cretorModels = new ArrayList<>();
                    List<GenericModel> companyModels = new ArrayList<>();


                    for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                        JSONObject jsonObject = response.getJSONArray("genres").getJSONObject(i);
                        GenreModel genreModel = new GenreModel(jsonObject.getInt("id"),
                                jsonObject.getString("name"));

                        genresList.add(genreModel);
                    }

                    seriesModel = new SeriesModel(response.getInt("id"),
                            response.getString("name"),
                            null,
                            response.getString("original_language"),
                            response.getString("poster_path"),
                            response.getDouble("vote_average")
                    );


                    for (int i = 0; i < response.getJSONArray("created_by").length(); i++) {
                        JSONObject jsonObject = response.getJSONArray("created_by").getJSONObject(i);
                        GenericModel creatorModel = new GenericModel(jsonObject.getString("profile_path"),
                                jsonObject.getString("name"));

                        cretorModels.add(creatorModel);
                    }

                    for (int i = 0; i < response.getJSONArray("production_companies").length(); i++) {
                        JSONObject jsonObject = response.getJSONArray("production_companies").getJSONObject(i);
                        GenericModel companyModel = new GenericModel(jsonObject.getString("logo_path"),
                                jsonObject.getString("name"));

                        companyModels.add(companyModel);
                    }

                    seriesDetailModel = new SeriesDetailModel(seriesModel,
                            response.getString("backdrop_path"),
                            response.getString("overview"),
                            genresList, cretorModels, companyModels);


                    callback.getSeriesDetailResponse(seriesDetailModel, 1, "");


                } catch (Exception e) {
                    e.printStackTrace();
                    callback.getSeriesDetailResponse(null, -6, "");
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            callback.getSeriesDetailResponse(null, -1, "");
                        } else if (error instanceof ServerError) {
                            callback.getSeriesDetailResponse(null, -2, "");
                        } else if (error instanceof NetworkError) {
                            callback.getSeriesDetailResponse(null, -3, "");
                        } else {
                            callback.getSeriesDetailResponse(null, -5, "");
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
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        addToRequestQueue(jsonRequest);
    }

    public void getSimilarSeries(final IGetSeriesList callback, int id) {
        String url = URLs.ROOT_URL + "/tv/" + id +"/similar"+ URLs.SECOND_PART_OF_URL +"&page=1";



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

                    callback.getSeriesListResponse(seriesModels, 1, "");


                } catch (Exception e) {
                    e.printStackTrace();
                    callback.getSeriesListResponse(null, -6, "");
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            callback.getSeriesListResponse(null, -1, "");
                        } else if (error instanceof ServerError) {
                            callback.getSeriesListResponse(null, -2, "");
                        } else if (error instanceof NetworkError) {
                            callback.getSeriesListResponse(null, -3, "");
                        } else {
                            callback.getSeriesListResponse(null, -5, "");
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
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        addToRequestQueue(jsonRequest);
    }


}
