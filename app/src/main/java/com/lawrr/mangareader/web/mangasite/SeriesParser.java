package com.lawrr.mangareader.web.mangasite;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lawrr.mangareader.ui.items.SeriesItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SeriesParser extends AsyncTask<String, Void, SeriesItem> {
    private SiteWrapper.SeriesListener listener;
    private long start;
    private long end;

    public SeriesParser(SiteWrapper.SeriesListener listener) {
        this.listener = listener;
    }

    @Override
    protected SeriesItem doInBackground(String... urls) {
        try {
            start = System.currentTimeMillis();
            SeriesItem item = getSeriesPage(urls[0]);
            end = System.currentTimeMillis();
            return item;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(SeriesItem item) {
        super.onPostExecute(item);
        long duration = end - start;
		Toast.makeText(((Fragment) listener).getActivity(), "Time taken: " + String.valueOf(duration) + " milliseconds.", Toast.LENGTH_LONG).show();
        listener.onRetrievedSeries(item);
    }

    public SeriesItem getSeriesPage(String url) throws IOException {
        SeriesItem item = new SeriesItem();
        Document doc = Jsoup.connect(url).maxBodySize(0).timeout(10 * 1000).get();
        item.setAuthor(doc.select("td a[href^=/search/author/").text());
        item.setArtist(doc.select("td a[href^=/search/artist/").text());
        item.setImageUrl(doc.select(".cover img").attr("src"));
        item.setSummary(doc.select(".summary").text());
        return item;
    }

}
