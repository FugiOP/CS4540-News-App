package util;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fugi.newsapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Fugi on 6/22/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{
    ItemClickListener listener;
    Cursor cursor;
    Context context;

    //Changed parameter ArrayList<NewsItem> to Cursor
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        //Added cursor to parameters
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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
        //changed Arraylist size to cursor count
        return cursor.getCount();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Added imageView reference
        TextView title;
        TextView desc;
        TextView date;
        ImageView img;

        ItemHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            desc = (TextView)view.findViewById(R.id.desc);
            date = (TextView)view.findViewById(R.id.date);
            img = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            //changed data.get() to cursor.getString to access data from database
            cursor.moveToPosition(pos);
            String thumbnail = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_IMAGE));
            if(thumbnail != null){
                Picasso.with(context).load(thumbnail).into(img);
            }
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_TITLE)));
            date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_DATE)));
            desc.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_DESC)));
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor,pos);
        }
    }
}
