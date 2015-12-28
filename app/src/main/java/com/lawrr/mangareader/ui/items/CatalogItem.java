package com.lawrr.mangareader.ui.items;

import android.os.Parcel;
import android.os.Parcelable;

public class CatalogItem implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.urlId);
        dest.writeByte(isOngoing ? (byte) 1 : (byte) 0);
    }

    protected CatalogItem(Parcel in) {
        this.name = in.readString();
        this.urlId = in.readString();
        this.isOngoing = in.readByte() != 0;
    }

    public static final Creator<CatalogItem> CREATOR = new Creator<CatalogItem>() {
        public CatalogItem createFromParcel(Parcel source) {
            return new CatalogItem(source);
        }

        public CatalogItem[] newArray(int size) {
            return new CatalogItem[size];
        }
    };
}
