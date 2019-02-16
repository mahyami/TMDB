package adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swapcard.tmdb.R;

import java.util.List;

import interfaces.OnLoadMoreListener;
import models.GenreModel;
import models.SeriesModel;
import web_handlers.URLs;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<SeriesModel> seriesModels;
    private List<GenreModel> genreModels;
    private Context context;
    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMoreListener;
    private int totalItemCount, visibleThreshold, lastVisibleItem;


    public SeriesAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        visibleThreshold = 5;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });
        }
    }

    public void setDataSet(List<SeriesModel> seriesModels) {
        this.seriesModels = seriesModels;
    }

    public void setGenre(List<GenreModel> genreModels) {
        this.genreModels = genreModels;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_series, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SeriesAdapter.ViewHolder holder, final int position) {
        holder.fill(position);

    }

    @Override
    public int getItemCount() {
        if (seriesModels == null)
            return 0;
        else
            return seriesModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtLang, txtVotes, txtGenres;
        private ImageView imgPoster;


        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txt_name);
            txtGenres = view.findViewById(R.id.txt_genres);
            txtLang = view.findViewById(R.id.txt_lang);
            txtVotes = view.findViewById(R.id.txt_votes);
            imgPoster = view.findViewById(R.id.img_poster);
        }

        public void fill(int position) {
            txtName.setText(seriesModels.get(position).getName());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < seriesModels.get(position).getGenres().size(); i++) {
                for (int j = 0; j < genreModels.size(); j++) {
                    if (seriesModels.get(position).getGenres().get(i).equals(genreModels.get(j).getId())) {
                        stringBuilder.append(genreModels.get(j).getValue() + ", ");
                    }
                }
            }
            //TODO: set background for genres
            txtGenres.setText(stringBuilder);
            txtLang.setText(seriesModels.get(position).getOrigLang());
            txtVotes.setText(seriesModels.get(position).getVoteAvg() + "");
            Glide.with(context).load(URLs.SERIESCOVER + seriesModels.get(position).getPosterPath()).into(imgPoster);

        }
    }
}

