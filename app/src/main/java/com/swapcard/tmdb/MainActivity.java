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
import models.GenreModel;
import models.SeriesModel;
import web_handlers.VolleyHandler;
import web_handlers.interfaces.IGetGenresList;
import web_handlers.interfaces.IGetSeriesList;

public class MainActivity extends AppCompatActivity implements IGetSeriesList<SeriesModel>, IGetGenresList<GenreModel> {
    private static final int PAGE_FRACTION = 20;
    private RecyclerView recyclerView;
    private SeriesAdapter seriesAdapter;
    private List<SeriesModel> seriesModels;
    private int pageNum = 1;
    private ProgressBar pb1, pb2;
    private boolean isLoadingMoreORnoItem = false;
    private List<GenreModel> genreModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seriesModels = new ArrayList<>();
        genreModels = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        pb1 = findViewById(R.id.pb1);
        pb2 = findViewById(R.id.pb2);
        pb1.setVisibility(View.VISIBLE);
        seriesAdapter = new SeriesAdapter(this, recyclerView);

        VolleyHandler.getInstance(this).getGenres(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(seriesAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (isLoadingMoreORnoItem)
                    return;
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int passedVisibleItems = layoutManager.findLastVisibleItemPosition();

                if (totalItemCount % PAGE_FRACTION != 0) {
                    isLoadingMoreORnoItem = true;
                    recyclerView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.half_base_margin), 0, 0);
                } else if (passedVisibleItems + visibleItemCount >= totalItemCount) {
                    pb2.setVisibility(View.VISIBLE);
                    isLoadingMoreORnoItem = true;
                    pageNum++;
                    VolleyHandler.getInstance(MainActivity.this).getSeries(MainActivity.this, pageNum);
                    pb2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }


    @Override
    public void getSeriesListResponse(final List<SeriesModel> respList, int statusCode, String statusMsg) {
        pb1.setVisibility(View.GONE);
        pb2.setVisibility(View.GONE);

        Toast.makeText(this, statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
        if (statusCode == 1) {

            if (seriesModels == null)
                seriesModels = respList;
            else
                seriesModels.addAll(respList);

            seriesAdapter.setGenre(genreModels);
            seriesAdapter.setDataSet(seriesModels);
            seriesAdapter.notifyDataSetChanged();
        }
        isLoadingMoreORnoItem = false;
    }

    @Override
    public void getGenreListResponse(List<GenreModel> respList, int statusCode, String statusMsg) {
        Toast.makeText(this, statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
        if (statusCode == 1) {

            if (genreModels == null)
                genreModels = respList;
            else
                genreModels.addAll(respList);

            VolleyHandler.getInstance(MainActivity.this).getSeries(MainActivity.this, pageNum);

        }

    }
}

