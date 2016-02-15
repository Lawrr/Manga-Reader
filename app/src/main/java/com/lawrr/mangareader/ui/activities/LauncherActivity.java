package com.lawrr.mangareader.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lawrr.mangareader.R;
import com.lawrr.mangareader.ui.fragments.CatalogFragment;
import com.lawrr.mangareader.ui.items.CatalogItem;

public class LauncherActivity extends AppCompatActivity
        implements CatalogFragment.CatalogInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCatalogItemSelected(CatalogItem item) {
        Intent i = new Intent(this, SeriesActivity.class);
        i.putExtra("catalogItem", item);
        startActivity(i);
    }
}
