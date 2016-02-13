package com.lawrr.mangareader.web.mangasite;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.MangaSeriesItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class MangaPageParser extends AsyncTask<String, Void, MangaSeriesItem> {
    private MangaPageParserInteractionListener listener;
    private long start;
    private long end;

    public MangaPageParser(MangaPageParserInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    protected MangaSeriesItem doInBackground(String... urls) {
        try {
            start = System.currentTimeMillis();
            MangaSeriesItem item = getMangaPage(urls[0]);
            end = System.currentTimeMillis();
            return item;
        } catch (IOException e) {
            Log.e("Manga", "IOException loading manga list", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(MangaSeriesItem item) {
        super.onPostExecute(item);
        long duration = end - start;
		Toast.makeText(((Fragment) listener).getActivity(), "Time taken: " + String.valueOf(duration) + " milliseconds.", Toast.LENGTH_LONG).show();
        listener.onRetrievedMangaPage(item);
    }

    public MangaSeriesItem getMangaPage(String url) throws IOException {
        MangaSeriesItem item = new MangaSeriesItem();
        Document doc = Jsoup.connect(url).maxBodySize(0).timeout(10 * 1000).get();
        item.setAuthor(doc.select("td a[href^=/search/author/").text());
        item.setArtist(doc.select("td a[href^=/search/artist/").text());
        item.setImageUrl(doc.select(".cover img").attr("src"));
        item.setSummary(doc.select(".summary").text());
        return item;
    }

    public interface MangaPageParserInteractionListener {
        void onRetrievedMangaPage(MangaSeriesItem item);
    }
}
