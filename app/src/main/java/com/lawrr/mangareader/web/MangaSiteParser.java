package com.lawrr.mangareader.web;

import android.os.AsyncTask;
import android.util.Log;

import com.lawrr.mangareader.ui.items.CatalogItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MangaSiteParser extends AsyncTask<String, Void, List<CatalogItem>> {
    private MangaSiteParserInteractionListener listener;

    public MangaSiteParser(MangaSiteParserInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<CatalogItem> doInBackground(String... urls) {
        try {
            return getMangaList(urls[0]);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<CatalogItem> list) {
        super.onPostExecute(list);
        listener.onRetrievedMangaList(list);
    }

    public List<CatalogItem> getMangaList(String url) throws IOException {
        List<CatalogItem> list = new ArrayList<>();
        Document doc = Jsoup.connect(url).maxBodySize(20*1024*1024).get();
        Elements manga = doc.select(".manga_list li a");
        for (Element m : manga) {
            boolean isOngoing = m.attr("class").equals("series_preview manga_open") ? true : false;
            CatalogItem item = new CatalogItem(m.text(), m.attr("href"), isOngoing);
            list.add(item);
        }
        return list;
    }

    public interface MangaSiteParserInteractionListener {
        void onRetrievedMangaList(List<CatalogItem> item);
    }
}
