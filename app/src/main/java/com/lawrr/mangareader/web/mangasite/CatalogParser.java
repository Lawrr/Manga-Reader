package com.lawrr.mangareader.web.mangasite;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lawrr.mangareader.ui.items.CatalogItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogParser extends AsyncTask<String, Void, List<CatalogItem>> {
    private SiteWrapper.CatalogListener listener;
    private long start;
    private long end;

    public CatalogParser(SiteWrapper.CatalogListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<CatalogItem> doInBackground(String... urls) {
        try {
            start = System.currentTimeMillis();
            List<CatalogItem> list = getCatalog(urls[0]);
            end = System.currentTimeMillis();
            return list;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<CatalogItem> list) {
        super.onPostExecute(list);
        long duration = end - start;
		Toast.makeText(((Fragment) listener).getActivity(), "Time taken: " + String.valueOf(duration) + " milliseconds.", Toast.LENGTH_LONG).show();
        listener.onRetrievedCatalog(list);
    }

    public List<CatalogItem> getCatalog(String url) throws IOException {
        List<CatalogItem> list = new ArrayList<>();
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        Elements manga = doc.select(".manga_list li a");
        for (Element m : manga) {
            boolean isOngoing = m.attr("class").equals("series_preview manga_open");
            CatalogItem item = new CatalogItem(m.text(), m.attr("href"), isOngoing);
            list.add(item);
        }
        return list;
    }

}
