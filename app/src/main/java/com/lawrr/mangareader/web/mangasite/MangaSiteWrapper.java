package com.lawrr.mangareader.web.mangasite;

import com.lawrr.mangareader.ui.items.CatalogItem;
import com.lawrr.mangareader.ui.items.MangaSeriesItem;

import java.util.List;

public class MangaSiteWrapper {

    public static void GetMangaList(MangaListListener listener, String url) {
        (new MangaListParser(listener)).execute(url);
    }

    public static void GetMangaPage(MangaPageListener listener, String url) {
        (new MangaPageParser(listener)).execute(url);
    }

    public interface MangaListListener {
        void onRetrievedMangaList(List<CatalogItem> item);
    }

    public interface MangaPageListener {
        void onRetrievedMangaPage(MangaSeriesItem item);
    }

}
