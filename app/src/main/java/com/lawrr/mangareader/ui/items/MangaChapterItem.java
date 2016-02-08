package com.lawrr.mangareader.ui.items;

public class MangaChapterItem {

    private int id;
    private String content;

    public MangaChapterItem(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
