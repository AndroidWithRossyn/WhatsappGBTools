package com.statuswa.fasttalkchat.toolsdownload.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wp_Status implements Parcelable {
    public static final Creator<Wp_Status> CREATOR = new Creator<Wp_Status>() {
        public Wp_Status createFromParcel(Parcel parcel) {
            return new Wp_Status(parcel);
        }

        public Wp_Status[] newArray(int i) {
            return new Wp_Status[i];
        }
    };
    private String filepath;
    public boolean selected = false;

    public int describeContents() {
        return 0;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public Wp_Status(String str) {
        this.filepath = str;
    }

    protected Wp_Status(Parcel parcel) {
        this.filepath = parcel.readString();
    }

    public String getFilePath() {
        return this.filepath;
    }

    public void setFilePath(String str) {
        this.filepath = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.filepath);
    }
}