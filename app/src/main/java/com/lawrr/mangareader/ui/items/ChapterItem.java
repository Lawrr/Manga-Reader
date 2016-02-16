package com.lawrr.mangareader.ui.items;

public class ChapterItem {

    private String name;
    private String title;
    private String url;
    private String date;

    public ChapterItem(String name, String title, String url, String date) {
        this.name = name;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
