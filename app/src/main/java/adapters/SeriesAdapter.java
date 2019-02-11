package adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapcard.tmdb.R;

import java.util.List;

import interfaces.OnLoadMoreListener;
import models.SeriesModel;


public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<SeriesModel> seriesModels;
    private Context context;
    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMoreListener;


    public SeriesAdapter(Context context, List<SeriesModel> seriesModels, RecyclerView recyclerView) {
        this.seriesModels = seriesModels;
        this.context = context;
        this.recyclerView = recyclerView;


        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

//                    totalItemCount = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager
//                            .findLastVisibleItemPosition();
//                    if ( totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        // End has been reached
//                        // Do something
//                        if (onLoadMoreListener != null) {
//                            onLoadMoreListener.onLoadMore();
//                        }
//                    }
                }
            });
        }
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
            txtGenres.setText(seriesModels.get(position).getGenres().get(0));
            txtLang.setText(seriesModels.get(position).getOrigLang());
            txtVotes.setText(seriesModels.get(position).getVoteAvg() + "");
            //TODO:: set image
        }
    }
}

