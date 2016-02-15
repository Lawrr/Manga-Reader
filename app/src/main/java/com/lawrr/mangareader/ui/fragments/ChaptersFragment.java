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
import android.widget.ProgressBar;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.adapters.ChapterItemAdapter;
import com.lawrr.mangareader.ui.decorations.DividerItemDecoration;
import com.lawrr.mangareader.ui.items.ChapterItem;

import java.util.ArrayList;
import java.util.List;

public class ChaptersFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column_count";

    private int columnCount = 1;
    private ChaptersInteractionListener listener;

    // List
    private List<ChapterItem> items = new ArrayList<>();
    private ChapterItemAdapter listAdapter;

    // Views
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public ChaptersFragment() {
    }

    @SuppressWarnings("unused")
    public static ChaptersFragment newInstance(int columnCount) {
        ChaptersFragment fragment = new ChaptersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        listAdapter = new ChapterItemAdapter(items, listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_chapter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // View lookups
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_manga_chapter_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        // Set the adapter
        Context context = view.getContext();
        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        recyclerView.setAdapter(listAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChaptersInteractionListener) {
            listener = (ChaptersInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement ChaptersInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setView(List<ChapterItem> items) {
        // Remove progress bar
        progressBar.setVisibility(View.GONE);

        // Update items
        this.items.clear();
        this.items.addAll(items);
        listAdapter.notifyDataSetChanged();
    }

    public interface ChaptersInteractionListener {
        void onChapterItemSelected(ChapterItem item);
    }
}
