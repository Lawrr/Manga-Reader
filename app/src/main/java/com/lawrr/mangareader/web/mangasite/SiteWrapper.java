package com.lawrr.mangareader.web.mangasite;

import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.ChapterItem;
import com.lawrr.mangareader.ui.items.SeriesItem;

import java.util.List;

public class SiteWrapper {

    public static void getCatalog(CatalogListener listener, String url) {
        (new CatalogParser(listener)).execute(url);
    }

    public static void getSeries(SeriesListener listener, String url) {
        (new SeriesParser(listener)).execute(url);
    }

    public interface CatalogListener {
        void onRetrievedCatalog(List<CatalogItem> items);
    }

    public interface SeriesListener {
        void onRetrievedSeries(SeriesItem item);
    }

}
