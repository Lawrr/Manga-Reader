package com.lawrr.mangareader.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.MangaSeriesItem;
import com.lawrr.mangareader.web.MangaPageParser;
import com.lawrr.mangareader.web.VolleySingleton;

public class MangaDetailsFragment extends Fragment
        implements MangaPageParser.MangaPageParserInteractionListener {
    private static final String ARG_CATALOG_ITEM = "catalog_item";

    private CatalogItem catalogItem;
    private MangaDetailsInteractionListener mListener;

    // Views
    private ProgressBar progressBar;
    private ImageView imageView;

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

            loadMangaDetails(catalogItem.getUrlId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // View lookups
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_manga_details_progress_bar);
        imageView = (ImageView) view.findViewById(R.id.fragment_manga_details_image);
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

    public void onRetrievedMangaPage(MangaSeriesItem item) {
        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(item.getImageUrl(),
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                }
            }, 0, 0, null,
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                }
            });
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(request);
    }

    private void loadMangaDetails(String url) {
        (new MangaPageParser(this)).execute(url);
    }

    public interface MangaDetailsInteractionListener {
    }
}
