package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.swapcard.tmdb.AppController;
import com.swapcard.tmdb.MainActivity;
import com.swapcard.tmdb.R;

import java.util.ArrayList;
import java.util.List;

import adapters.SeriesAdapter;
import models.GenreModel;
import models.SeriesModel;
import util.ItemClickListener;
import util.RecyclerTouchListener;
import web_handlers.VolleyHandler;
import web_handlers.interfaces.IGetGenresList;
import web_handlers.interfaces.IGetSeriesList;

public class SeriesListFragment extends Fragment implements IGetSeriesList<SeriesModel>, IGetGenresList<GenreModel>, ItemClickListener {
    private static final int PAGE_FRACTION = 20;
    private RecyclerView recyclerView;
    private SeriesAdapter seriesAdapter;
    private List<SeriesModel> seriesModels;
    private int pageNum = 1;
    private ProgressBar pb1, pb2;
    private boolean isLoadingMoreORnoItem = false;
    private List<GenreModel> genreModels;
    private TextView txtTryAgain;

    public SeriesListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppController.currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_series_list, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().show();


        seriesModels = new ArrayList<>();
        genreModels = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        pb1 = view.findViewById(R.id.pb1);
        pb2 = view.findViewById(R.id.pb2);
        txtTryAgain = view.findViewById(R.id.txt_try_again);

        pb1.setVisibility(View.VISIBLE);


        VolleyHandler.getInstance(getContext()).getGenres(this);

        seriesAdapter = new SeriesAdapter(getContext(), recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    VolleyHandler.getInstance(getContext()).getSeries(SeriesListFragment.this, pageNum);
                    pb2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(AppController.getInstance(), recyclerView, this));


        txtTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment seriesListFragment = new SeriesListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMain, seriesListFragment);
                fragmentTransaction.commit();

            }
        });

        return view;
    }


    @Override
    public void getGenreListResponse(List<GenreModel> respList, int statusCode, String statusMsg) {
        if (statusCode == 1) {

            if (genreModels == null)
                genreModels = respList;
            else
                genreModels.addAll(respList);

            VolleyHandler.getInstance(getContext()).getSeries(SeriesListFragment.this, pageNum);

        }
    }

    @Override
    public void getSeriesListResponse(List<SeriesModel> respList, int statusCode, String statusMsg) {
        pb1.setVisibility(View.GONE);
        pb2.setVisibility(View.GONE);

        if (statusCode == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.new_data), Toast.LENGTH_SHORT).show();

            if (seriesModels == null)
                seriesModels = respList;
            else
                seriesModels.addAll(respList);

            seriesAdapter.setGenre(genreModels);
            seriesAdapter.setDataSet(seriesModels);
            seriesAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
            txtTryAgain.setVisibility(View.VISIBLE);
        }

        isLoadingMoreORnoItem = false;
    }

    @Override
    public void onItemClick(View child, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("series_id", seriesAdapter.getId(position));
        ((MainActivity) getActivity()).manageFragment(
                SeriesDetailFragment.class, true, bundle, true);

    }

    @Override
    public void onItemLongClick(View child, int position) {

    }
}
