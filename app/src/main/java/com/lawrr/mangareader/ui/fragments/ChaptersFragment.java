package com.lawrr.mangareader.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.adapters.ChapterItemAdapter;
import com.lawrr.mangareader.ui.decorations.DividerItemDecoration;
import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChaptersFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column_count";
    private static final String ARG_CATALOG_ITEM = "catalog_item";

    private int mColumnCount = 1;
    private ChaptersInteractionListener mListener;
    private CatalogItem catalogItem;

    // List
    private List<ChapterItem> items = new ArrayList<>();
    private ChapterItemAdapter listAdapter;

    // Views
    private RecyclerView recyclerView;

    public ChaptersFragment() {
    }

    @SuppressWarnings("unused")
    public static ChaptersFragment newInstance(int columnCount, CatalogItem catalogItem) {
        ChaptersFragment fragment = new ChaptersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putParcelable(ARG_CATALOG_ITEM, catalogItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            catalogItem = getArguments().getParcelable(ARG_CATALOG_ITEM);
        }

        for(int i = 0; i < 20; i++) {
            items.add(new ChapterItem(i, "yes"));
        }
        listAdapter = new ChapterItemAdapter(items, mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_chapter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // View lookups
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

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
        if (activity instanceof ChaptersInteractionListener) {
            mListener = (ChaptersInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement ChaptersInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ChaptersInteractionListener {
        void onChapterItemSelected(ChapterItem item);
    }
}
