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

/**
 * {@link RecyclerView.Adapter} that can display a {@link CatalogItem} and makes a call to the
 * specified {@link CatalogFragment.CatalogInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
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
        holder.mItem = items.get(position);
        holder.mIdView.setText(items.get(position).id);
        holder.mContentView.setText(items.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCatalogItemSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public CatalogItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
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
                    final String text = item.content.toLowerCase();
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
