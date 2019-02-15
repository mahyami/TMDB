package com.swapcard.tmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.SeriesAdapter;
import models.SeriesModel;
import utils.EndlessRecyclerViewScrollListener;
import web_handlers.VolleyHandler;
import web_handlers.interfaces.ISeriesList;

public class MainActivity extends AppCompatActivity implements ISeriesList<SeriesModel> {
    private RecyclerView recyclerView;
    private SeriesAdapter seriesAdapter;
    private List<SeriesModel> seriesModels;
    private int pageNum = 1;
    private ProgressBar pb1, pb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seriesModels = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        pb1 = findViewById(R.id.pb1);
        pb2 = findViewById(R.id.pb2);
        pb1.setVisibility(View.VISIBLE);
        seriesAdapter = new SeriesAdapter(this, recyclerView);

        VolleyHandler.getInstance(this).getSeries(this, pageNum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(seriesAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(this)) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pb2.setVisibility(View.VISIBLE);
                VolleyHandler.getInstance(MainActivity.this).getSeries(MainActivity.this, page);
            }

        });


    }


    @Override
    public void seriesListResponse(final List<SeriesModel> seriesList, int statusCode, String statusMsg) {
        pb1.setVisibility(View.GONE);
        pb2.setVisibility(View.GONE);

        Toast.makeText(this, statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
        seriesModels = seriesList;
        seriesAdapter.setDataSet(seriesModels);
        seriesAdapter.notifyDataSetChanged();


    }
}
