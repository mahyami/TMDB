package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swapcard.tmdb.R;

import java.util.List;

import models.GenericModel;
import web_handlers.URLs;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {

    private Context context;
    private List<GenericModel> genericModels;

    public GenericAdapter(Context context, List<GenericModel> genericModels) {
        this.context = context;
        this.genericModels = genericModels;
    }


    @Override
    public GenericAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_generic, parent, false);

        return new GenericAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GenericAdapter.ViewHolder holder, final int position) {
        holder.fill(position);

    }

    @Override
    public int getItemCount() {
        if (genericModels == null)
            return 0;
        else
            return genericModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txt_title);
            img = view.findViewById(R.id.img);
        }

        public void fill(int position) {
            txtTitle.setText(genericModels.get(position).getName());
            Glide.with(context).load(URLs.SERIESCOVER + genericModels.get(position).getImg()).into(img);
        }
    }
}

