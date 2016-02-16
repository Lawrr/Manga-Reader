package com.lawrr.mangareader.web.mangasite;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.lawrr.mangareader.ui.items.ChapterItem;
import com.lawrr.mangareader.ui.items.SeriesItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeriesParser extends AsyncTask<String, Void, Void> {
    private SiteWrapper.SeriesListener listener;

    private SeriesItem series;
    private List<ChapterItem> chapters;

    private long start;
    private long end;

    public SeriesParser(SiteWrapper.SeriesListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... urls) {
        try {
            start = System.currentTimeMillis();
            Document page = Jsoup.connect(urls[0]).maxBodySize(0).get();
            series = getSeries(page);
            chapters = getChapters(page);
            end = System.currentTimeMillis();
        } catch (IOException e) {
            // Error
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        long duration = end - start;
		Toast.makeText(((Activity) listener), "Time taken: " + String.valueOf(duration) + " milliseconds.", Toast.LENGTH_LONG).show();

        listener.onRetrievedSeries(series);
        listener.onRetrievedChapters(chapters);
    }

    public SeriesItem getSeries(Document page) throws IOException {
        String author = page.select("td a[href^=/search/author/").text();
        String artist = page.select("td a[href^=/search/artist/").text();
        String imageUrl = page.select(".cover img").attr("src");
        String summary = page.select(".summary").text();
        return new SeriesItem(author, artist, imageUrl, summary);
    }

    public List<ChapterItem> getChapters(Document page) throws IOException {
        List<ChapterItem> chapters = new ArrayList<>();
        Elements chapterElements = page.select("#chapters li");
        for (Element e : chapterElements) {
            String name = e.select(".tips").text();
            String title = e.select(".title").text();
            String url = e.select(".tips").attr("href");
            String date = e.select(".date").text();
            chapters.add(new ChapterItem(name, title, url, date));
        }
        return chapters;
    }

}
