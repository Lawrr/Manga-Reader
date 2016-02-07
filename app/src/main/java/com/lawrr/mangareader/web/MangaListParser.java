package com.lawrr.mangareader.web;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.lawrr.mangareader.ui.items.CatalogItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MangaListParser extends AsyncTask<String, Void, List<CatalogItem>> {
    private MangaListParserInteractionListener listener;
    private long start;
    private long end;

    public MangaListParser(MangaListParserInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<CatalogItem> doInBackground(String... urls) {
        try {
            start = System.currentTimeMillis();
            List<CatalogItem> list = getMangaList(urls[0]);
            end = System.currentTimeMillis();
            return list;
        } catch (IOException e) {
            Log.e("Manga", "IOException loading manga list", e);
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<CatalogItem> list) {
        super.onPostExecute(list);
        long duration = end - start;
		Toast.makeText(((Fragment) listener).getActivity(), "Time taken: " + String.valueOf(duration) + " milliseconds.", Toast.LENGTH_LONG).show();
        listener.onRetrievedMangaList(list);
    }

    public List<CatalogItem> getMangaList(String url) throws IOException {
        List<CatalogItem> list = new ArrayList<>();
        Log.d("Manga", "Downloading page");
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Log.d("Manga", "Selecting elements");
        Elements manga = doc.select(".manga_list li a");
        Log.d("Manga", "Creating items");
        for (Element m : manga) {
            boolean isOngoing = m.attr("class").equals("series_preview manga_open");
            CatalogItem item = new CatalogItem(m.text(), m.attr("href"), isOngoing);
            list.add(item);
        }
        Log.d("Manga", "Returning list");
        return list;
    }

    public interface MangaListParserInteractionListener {
        void onRetrievedMangaList(List<CatalogItem> item);
    }
}
