package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.swapcard.tmdb.AppController;
import com.swapcard.tmdb.MainActivity;
import com.swapcard.tmdb.R;

import adapters.GenericAdapter;
import adapters.GenreAdapter;
import models.SeriesDetailModel;
import web_handlers.URLs;
import web_handlers.VolleyHandler;
import web_handlers.interfaces.IGetSeriesDetail;

public class SeriesDetailFragment extends Fragment implements IGetSeriesDetail {
    private Toolbar toolbar;
    private ImageView imgPoster;
    private TextView txtLang, txtVote, txtOverview, title, creator;
    private RecyclerView rlGenre, rlCreators, rlProductionComp;
    private GenericAdapter adapterCreator, adapterProductionComp;
    private GenreAdapter adapterGenre;
    private SeriesDetailModel seriesDetailModel;
    private int seriesID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppController.currentFragment = this;
        View view = inflater.inflate(R.layout.fragment_series_detail, container, false);

        getIntent();
        sendDetailReq();
        sendSimilarReq();
        initView(view);

        ((MainActivity) getActivity()).getSupportActionBar().hide();
        title.setText("");

        return view;
    }

    private void sendSimilarReq() {
    }

    private void sendDetailReq() {
        VolleyHandler.getInstance(getContext()).getSeriesDetails(SeriesDetailFragment.this, seriesID);
    }

    private void getIntent() {
        if (getArguments() != null)
            seriesID = getArguments().getInt("series_id");
    }

    private void setView() {
        if (seriesDetailModel.getCreatorModels().size() == 0)
            creator.setVisibility(View.GONE);

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
    }

    @Override
    public void getSeriesDetailResponse(SeriesDetailModel resp, int statusCode, String statusMsg) {
        if (statusCode == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.new_data), Toast.LENGTH_SHORT).show();
            seriesDetailModel = resp;
            setView();


        } else
            Toast.makeText(getContext(), statusMsg + "  " + statusCode, Toast.LENGTH_LONG).show();

    }
}
