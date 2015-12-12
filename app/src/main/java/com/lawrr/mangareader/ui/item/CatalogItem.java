package com.lawrr.mangareader.ui.item;

public class CatalogItem {
    public final String id;
    public final String content;
    public final String details;

    public CatalogItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}
