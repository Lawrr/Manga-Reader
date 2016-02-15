package com.lawrr.mangareader.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.SeriesItem;
import com.lawrr.mangareader.web.VolleySingleton;
import com.lawrr.mangareader.web.mangasite.SiteWrapper;

public class DetailsFragment extends Fragment implements SiteWrapper.SeriesListener {
    private static final String ARG_CATALOG_ITEM = "catalog_item";

    private CatalogItem catalogItem;
    private DetailsInteractionListener listener;

    // Views
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView infoView;
    private TextView summaryView;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(CatalogItem catalogItem) {
        DetailsFragment fragment = new DetailsFragment();
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

            // Load page details
            SiteWrapper.getSeries(this, catalogItem.getUrlId());
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
        infoView = (TextView) view.findViewById(R.id.fragment_manga_details_info);
        summaryView = (TextView) view.findViewById(R.id.fragment_manga_details_summary);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailsInteractionListener) {
            listener = (DetailsInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement DetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void onRetrievedSeries(SeriesItem item) {
        updateView(item);
    }

    private void updateView(final SeriesItem item) {
        ImageRequest request = new ImageRequest(item.getImageUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        // Show views
                        imageView.setImageBitmap(bitmap);
                        String infoText = "<b>Author</b>: " + item.getAuthor() + "<br /><b>Artist</b>: " + item.getArtist();
                        infoView.setText(Html.fromHtml(infoText));
                        summaryView.setText(item.getSummary());

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(request);
    }

    public interface DetailsInteractionListener {
    }
}
