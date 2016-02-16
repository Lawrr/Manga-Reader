package com.lawrr.mangareader.ui.items;

public class SeriesItem {
    private String author;
    private String artist;
    private String imageUrl;
    private String summary;

    public SeriesItem(String author, String artist, String imageUrl, String summary) {
        this.author = author;
        this.artist = artist;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSummary() {
        return summary;
    }

}
