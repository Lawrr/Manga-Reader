package com.lawrr.mangareader.web.mangasite;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChapterParser extends AsyncTask<String, Void, List<String>> {
    private SiteWrapper.ChapterListener listener;

    public ChapterParser(SiteWrapper.ChapterListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        List<String> imageUrls = null;
        try {
            imageUrls = getChapter(urls[0]);
        } catch (IOException e) {
            // Error
        }

        return imageUrls;
    }

    @Override
    protected void onPostExecute(List<String> imageUrls) {
        super.onPostExecute(imageUrls);
        listener.onRetrievedChapter(imageUrls);
    }

    public List<String> getChapter(String url) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        Document page = Jsoup.connect(url).maxBodySize(0).get();

        // Get num of pages
        int numPages = 1;
        try {
            numPages = Integer.parseInt(page.select("select[class=m]").first().nextSibling().outerHtml().replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            // Error
        }

        // Get image urls
        String pageImageUrl = page.select("#image").attr("src");
        Pattern imageUrlPattern = Pattern.compile("(.*?)(\\d+)(\\.\\w+)$");
        Matcher matcher = imageUrlPattern.matcher(pageImageUrl);
        if (matcher.find()) {
            String baseImageUrl = matcher.group(1);
            int pageNumLength = matcher.group(2).length();
            String imageExtension = matcher.group(3);

            Log.d("asdf", baseImageUrl);
            Log.d("asdf", String.valueOf(pageNumLength));
            Log.d("asdf", imageExtension);

            for (int i = 1; i <= numPages; i++) {
                imageUrls.add(String.format("%s%0" + pageNumLength + "d%s", baseImageUrl, i, imageExtension));
            }
        }

        return imageUrls;
    }

}

