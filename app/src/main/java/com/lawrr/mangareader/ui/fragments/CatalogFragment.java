package com.lawrr.mangareader.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.adapters.CatalogItemAdapter;
import com.lawrr.mangareader.ui.decorations.DividerItemDecoration;
import com.lawrr.mangareader.ui.items.CatalogItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link CatalogInteractionListener}
 * interface.
 */
public class CatalogFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private CatalogInteractionListener mListener;

    // List
    private List<CatalogItem> items = new ArrayList<>();
    private CatalogItemAdapter listAdapter;

    // Views
    private SearchView searchView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CatalogFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CatalogFragment newInstance(int columnCount) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        setHasOptionsMenu(true);

        // Generate list
        for(int i = 0; i < 5000; i++) {
            items.add(new CatalogItem(String.valueOf(i), "Item " + i, "Item details"));
        }
        listAdapter = new CatalogItemAdapter(items, mListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_catalog, menu);
        super.onCreateOptionsMenu(menu, inflater);

        // Set up search view
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        initSearchViewListener(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get view
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // View lookups
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_catalog_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        // List ready - remove progress bar
        progressBar.setVisibility(View.GONE);

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(listAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CatalogInteractionListener) {
            mListener = (CatalogInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement CatalogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface to be implemented by activities which use this fragment
     */
    public interface CatalogInteractionListener {
        void onCatalogItemSelected(CatalogItem item);
    }

    private void initSearchViewListener(final SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String query) {
                // Apply filter
                listAdapter.getFilter().filter(query);

                // Scroll list to top
                recyclerView.scrollToPosition(0);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Change focus to list (so when you go back the keyboard doesn't open)
                getView().requestFocus();

                // Close keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                return true;
            }
        });
    }
}
