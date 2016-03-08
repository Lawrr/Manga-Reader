package com.lawrr.mangareader.ui.items;

import android.os.Parcel;
import android.os.Parcelable;

public class ChapterItem implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.date);
    }

    protected ChapterItem(Parcel in) {
        this.name = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<ChapterItem> CREATOR = new Parcelable.Creator<ChapterItem>() {
        public ChapterItem createFromParcel(Parcel source) {
            return new ChapterItem(source);
        }

        public ChapterItem[] newArray(int size) {
            return new ChapterItem[size];
        }
    };
}
