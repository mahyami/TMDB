package fragments;

import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.swapcard.tmdb.AppController;
import com.swapcard.tmdb.MainActivity;
import com.swapcard.tmdb.R;

import java.util.ArrayList;
import java.util.List;

import adapters.GenericAdapter;
import adapters.GenreAdapter;
import adapters.SeriesAdapter;
import models.GenreModel;
import models.SeriesDetailModel;
import models.SeriesModel;
import web_handlers.URLs;
import web_handlers.VolleyHandler;
import web_handlers.interfaces.IGetGenresList;
import web_handlers.interfaces.IGetSeriesDetail;
import web_handlers.interfaces.IGetSeriesList;

public class SeriesDetailFragment extends Fragment implements IGetSeriesDetail, IGetSeriesList<SeriesModel>, IGetGenresList<GenreModel> {
    private ImageView imgPoster, imgBack, line;
    private TextView txtLang, txtVote, txtOverview, title, creator, similar, txtTryAgain, txtTryAgainSimilar;
    private RecyclerView rlGenre, rlCreators, rlProductionComp, rlSimilar;
    private GenericAdapter adapterCreator, adapterProductionComp;
    private GenreAdapter adapterGenre;
    private SeriesDetailModel seriesDetailModel;
    private int seriesID;
    private List<SeriesModel> seriesModels;
    private List<GenreModel> genreModels;
    private SeriesAdapter seriesAdapter;
    private ProgressBar pb;
    private LinearLayout llMaster;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppController.currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_series_detail, container, false);

        seriesModels = new ArrayList<>();
        genreModels = new ArrayList<>();

        getIntent();
        initView(view);
        sendDetailReq();
        setListener();
        pb.setVisibility(View.VISIBLE);

        ((MainActivity) getActivity()).getSupportActionBar().hide();
        title.setText("");

        return view;
    }

    private void setListener() {
        txtTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Fragment fragment = new SeriesDetailFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMain, fragment);
                fragmentTransaction.commit();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment seriesList = new SeriesListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameMain, seriesList);
                fragmentTransaction.commit();
            }
        });

        txtTryAgainSimilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGenreReq();
            }
        });
    }

    private void sendGenreReq() {
        VolleyHandler.getInstance(getContext()).getGenres(SeriesDetailFragment.this);

    }

    private void sendSimilarReq() {
        if (seriesID > 0)
            VolleyHandler.getInstance(getContext()).getSimilarSeries(SeriesDetailFragment.this, seriesID);
    }

    private void sendDetailReq() {
        if (seriesID > 0)
            VolleyHandler.getInstance(getContext()).getSeriesDetails(SeriesDetailFragment.this, seriesID);
    }

    private void getIntent() {
        if (getArguments() != null)
            seriesID = getArguments().getInt("series_id");
    }

    private void setView() {
        llMaster.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);

        if (seriesDetailModel.getCreatorModels().size() == 0) {
            creator.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        title.setText(seriesDetailModel.getSeriesModel().getName());
        Glide.with(getContext()).load(URLs.SERIESCOVER + seriesDetailModel.getSeriesModel().getPosterPath()).into(imgPoster);

        adapterGenre = new GenreAdapter(getContext(), seriesDetailModel.getGenreModels());
        rlGenre.setAdapter(adapterGenre);
        rlGenre.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapterCreator = new GenericAdapter(getContext(), seriesDetailModel.getCreatorModels());
        rlCreators.setAdapter(adapterCreator);
        rlCreators.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        adapterProductionComp = new GenericAdapter(getContext(), seriesDetailModel.getCompanyModels());
        rlProductionComp.setAdapter(adapterProductionComp);
        rlProductionComp.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        seriesAdapter = new SeriesAdapter(getContext(), rlSimilar);
        rlSimilar.setAdapter(seriesAdapter);
        rlSimilar.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        txtVote.setText(seriesDetailModel.getSeriesModel().getVoteAvg() + "");
        txtOverview.setText(seriesDetailModel.getOverview());
        txtLang.setText(seriesDetailModel.getSeriesModel().getOrigLang());

    }

    private void initView(View view) {
        imgPoster = view.findViewById(R.id.img_poster);
        txtLang = view.findViewById(R.id.txt_lang);
        txtVote = view.findViewById(R.id.txt_votes);
        rlGenre = view.findViewById(R.id.rl_genre);
        txtOverview = view.findViewById(R.id.txt_overview);
        rlCreators = view.findViewById(R.id.rl_creators);
        rlProductionComp = view.findViewById(R.id.rl_production_comp);
        title = view.findViewById(R.id.title);
        creator = view.findViewById(R.id.creator);
        rlSimilar = view.findViewById(R.id.rl_similar);
        similar = view.findViewById(R.id.similar);
        pb = view.findViewById(R.id.pb);
        imgBack = view.findViewById(R.id.img_back);
        txtTryAgain = view.findViewById(R.id.txt_try_again);
        llMaster = view.findViewById(R.id.ll_master);
        line = view.findViewById(R.id.line);
        txtTryAgainSimilar = view.findViewById(R.id.txt_try_again_similar);
    }

    @Override
    public void getGenreListResponse(List<GenreModel> respList, int statusCode, String statusMsg) {
        if (statusCode == 1) {
            if (genreModels == null)
                genreModels = respList;
            else
                genreModels.addAll(respList);

            sendSimilarReq();

        } else {
            txtTryAgainSimilar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSeriesListResponse(List<SeriesModel> respList, int statusCode, String statusMsg) {

        if (statusCode == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.new_data), Toast.LENGTH_SHORT).show();

            seriesModels = respList;

            if (seriesModels.size() == 0)
                similar.setVisibility(View.GONE);

            seriesAdapter.setGenre(genreModels);
            seriesAdapter.setDataSet(seriesModels);
            seriesAdapter.notifyDataSetChanged();
            txtTryAgainSimilar.setVisibility(View.GONE);

        } else {
            Toast.makeText(getContext(), statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
            txtTryAgainSimilar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSeriesDetailResponse(SeriesDetailModel resp, int statusCode, String statusMsg) {
        if (statusCode == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.new_data), Toast.LENGTH_SHORT).show();
            seriesDetailModel = resp;
            setView();
            sendGenreReq();

        } else {
            Toast.makeText(getContext(), statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();
            txtTryAgain.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
        }

    }
}
