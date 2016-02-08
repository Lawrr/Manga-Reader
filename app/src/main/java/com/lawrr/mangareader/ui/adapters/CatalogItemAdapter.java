package com.lawrr.mangareader.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.fragments.CatalogFragment;
import com.lawrr.mangareader.ui.items.CatalogItem;

import java.util.ArrayList;
import java.util.List;

public class CatalogItemAdapter extends RecyclerView.Adapter<CatalogItemAdapter.ViewHolder> implements Filterable {

    // Items being shown on list (changes with filter)
    private List<CatalogItem> items;
    // Original list of items
    private final List<CatalogItem> originalItems;
    private final CatalogFragment.CatalogInteractionListener mListener;

    // Filter
    private final Object filterLock = new Object();
    private CatalogItemFilter itemFilter;

    public CatalogItemAdapter(List<CatalogItem> items, CatalogFragment.CatalogInteractionListener listener) {
        this.items = items;
        originalItems = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_catalog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.nameView.setText(items.get(position).getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCatalogItemSelected(holder.item);
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
        public final TextView nameView;
        public CatalogItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            nameView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + nameView.getText();
        }
    }

    //Return filter
	@Override
	public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new CatalogItemFilter();
        }
        return itemFilter;
    }

	/**
	 * Custom Filter implementation for the adapter.
	 *
	 */
	private class CatalogItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            FilterResults results = new FilterResults();
            List<CatalogItem> filteredItems = new ArrayList<>();

            // No query is sent to filter so send back the original list
            if (query == null || query.length() == 0) {
                synchronized (filterLock) {
                    filteredItems = originalItems;
                }
            } else {
                // Filter from original list of items
                for (CatalogItem item : originalItems) {
                    final String text = item.getName().toLowerCase();
                    if (text.contains(query.toString().toLowerCase())) {
                        filteredItems.add(item);
                    }
                }
            }
            results.values = filteredItems;

            return results;
        }

        @Override
        protected void publishResults(CharSequence query, FilterResults results) {
            items = (ArrayList<CatalogItem>) results.values;
            notifyDataSetChanged();
        }
    }
}
