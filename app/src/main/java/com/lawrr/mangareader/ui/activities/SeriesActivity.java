package com.lawrr.mangareader.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.fragments.ChaptersFragment;
import com.lawrr.mangareader.ui.fragments.DetailsFragment;
import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.ChapterItem;
import com.lawrr.mangareader.ui.items.SeriesItem;
import com.lawrr.mangareader.web.mangasite.SiteWrapper;

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity
        implements SiteWrapper.SeriesListener,
                   DetailsFragment.DetailsInteractionListener,
                   ChaptersFragment.ChaptersInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    private DetailsFragment detailsFragment;
    private ChaptersFragment chaptersFragment;
    private CatalogItem catalogItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_page);

        // Get parcelable
        catalogItem = getIntent().getParcelableExtra("catalogItem");
        setTitle(catalogItem.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the fragments
        detailsFragment = DetailsFragment.newInstance();
        chaptersFragment = ChaptersFragment.newInstance(1);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Load series
        SiteWrapper.getSeries(this, catalogItem.getUrlId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manga_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return detailsFragment;
                case 1:
                    return chaptersFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Details";
                case 1:
                    return "Chapters";
            }
            return null;
        }
    }

    public void onRetrievedSeries(SeriesItem item) {
        detailsFragment.setView(item);
    }

    public void onRetrievedChapters(List<ChapterItem> items) {
        chaptersFragment.setView(items);
    }

    public void onChapterItemSelected(ChapterItem item) {

    }

}
