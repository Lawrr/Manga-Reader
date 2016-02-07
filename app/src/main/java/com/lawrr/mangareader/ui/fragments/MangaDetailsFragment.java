package com.lawrr.mangareader.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.items.CatalogItem;

public class MangaDetailsFragment extends Fragment {
    private static final String ARG_CATALOG_ITEM = "catalog_item";

    private CatalogItem catalogItem;
    private MangaDetailsInteractionListener mListener;

    public MangaDetailsFragment() {
        // Required empty public constructor
    }

    public static MangaDetailsFragment newInstance(CatalogItem catalogItem) {
        MangaDetailsFragment fragment = new MangaDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATALOG_ITEM, catalogItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            catalogItem = getArguments().getParcelable(ARG_CATALOG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga_details, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MangaDetailsInteractionListener) {
            mListener = (MangaDetailsInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement MangaDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface MangaDetailsInteractionListener {
    }
}
