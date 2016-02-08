package com.lawrr.mangareader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.fragments.MangaChapterFragment;
import com.lawrr.mangareader.ui.items.MangaChapterItem;

import java.util.List;

public class MangaChapterItemAdapter extends RecyclerView.Adapter<MangaChapterItemAdapter.ViewHolder> {

    private final List<MangaChapterItem> items;
    private final MangaChapterFragment.MangaChapterInteractionListener mListener;

    public MangaChapterItemAdapter(List<MangaChapterItem> items, MangaChapterFragment.MangaChapterInteractionListener listener) {
        this.items = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_manga_chapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.idView.setText(String.valueOf(items.get(position).getId()));
        holder.contentView.setText(items.get(position).getContent());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnMangaChapterItemSelected(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView idView;
        public final TextView contentView;
        public MangaChapterItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.id);
            contentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
