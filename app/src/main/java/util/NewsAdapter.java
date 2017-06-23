package util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fugi.newsapp.R;

import java.util.ArrayList;

import models.NewsItem;

/**
 * Created by Fugi on 6/22/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{
    ArrayList<NewsItem> data;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView desc;
        TextView date;

        ItemHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            desc = (TextView)view.findViewById(R.id.desc);
            date = (TextView)view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            NewsItem article = data.get(pos);
            title.setText(article.getTitle());
            date.setText(article.getDate());
            desc.setText(article.getDesc());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}
