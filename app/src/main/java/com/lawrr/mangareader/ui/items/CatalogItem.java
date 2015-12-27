package com.lawrr.mangareader.ui.items;

public class CatalogItem {
    private String name;
    private String urlId;
    private boolean isOngoing;

    public CatalogItem(String name, String urlId, boolean isOngoing) {
        this.name = name;
        this.urlId = urlId;
        this.isOngoing = isOngoing;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getUrlId() {
        return urlId;
    }

    public boolean isOngoing() {
        return isOngoing;
    }
}
