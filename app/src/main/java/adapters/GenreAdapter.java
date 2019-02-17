package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swapcard.tmdb.R;

import java.util.List;

import models.GenericModel;
import models.GenreModel;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    private Context context;
    private List<GenreModel> genreModels;

    public GenreAdapter(Context context, List<GenreModel> genericModels) {
        this.context = context;
        this.genreModels = genericModels;
    }


    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_genre, parent, false);

        return new GenreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GenreAdapter.ViewHolder holder, final int position) {
        holder.fill(position);

    }

    @Override
    public int getItemCount() {
        if (genreModels == null)
            return 0;
        else
            return genreModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtGenre;

        public ViewHolder(View view) {
            super(view);
            txtGenre = view.findViewById(R.id.txt_genre);
        }

        public void fill(int position) {
            txtGenre.setText(genreModels.get(position).getValue());
        }
    }
}

