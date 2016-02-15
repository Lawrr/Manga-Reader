package com.lawrr.mangareader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.fragments.ChaptersFragment;
import com.lawrr.mangareader.ui.items.ChapterItem;

import java.util.List;

public class ChapterItemAdapter extends RecyclerView.Adapter<ChapterItemAdapter.ViewHolder> {

    private final List<ChapterItem> items;
    private final ChaptersFragment.ChaptersInteractionListener listener;

    public ChapterItemAdapter(List<ChapterItem> items, ChaptersFragment.ChaptersInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chapters_item, parent, false);
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
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onChapterItemSelected(holder.item);
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
        public ChapterItem item;

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
