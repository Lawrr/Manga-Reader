package com.lawrr.mangareader.web.mangasite;

import com.lawrr.mangareader.ui.items.CatalogItem;

import java.util.List;

public class MangaSiteWrapper {

    public static void GetMangaList(MangaListListener listener, String url) {
        (new MangaListParser(listener)).execute(url);
    }

    public interface MangaListListener {
        void onRetrievedMangaList(List<CatalogItem> item);
    }

}
