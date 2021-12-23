package id.prakprogmob.mangaku;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.Transliterator;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    CursorAdapter mCursorAdapter;
    Context mContext;
    private LayoutInflater layoutInflater;
    private OnMangaListener mOnMangaListener;

    public RecyclerAdapter(Context context, Cursor c, int flags, OnMangaListener onMangaListener) {

        mContext = context;
        mOnMangaListener = onMangaListener;
        mCursorAdapter = new CursorAdapter(mContext, c, flags) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                ViewHolder holder = new ViewHolder(v, mOnMangaListener);
                v.setTag(holder);
                return v;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ViewHolder holder = (ViewHolder)view.getTag();
                holder.Id.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
                holder.Judul.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_judul)));
                holder.Jenis.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_jenis)));
                holder.Genre.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_genre)));
                holder.Rating.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_rating)));
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Id, Judul, Jenis, Genre, Rating;
        OnMangaListener onMangaListener;

        public ViewHolder(View itemView, OnMangaListener onMangaListener) {
            super(itemView);
            Id = itemView.findViewById(R.id.listReview);
            Judul = itemView.findViewById(R.id.listJudul);
            Jenis = itemView.findViewById(R.id.listJenis);
            Genre = itemView.findViewById(R.id.listGenre);
            Rating = itemView.findViewById(R.id.listRating);

            this.onMangaListener = onMangaListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            onMangaListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        mCursorAdapter.getCursor().moveToPosition(position); //EDITED: added this line as suggested in the comments below, thanks :)
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ViewHolder(v, mOnMangaListener);
    }

    public interface OnMangaListener{
        void onItemClick(View view, int position);
    }
}
