package com.kuzaev.starskrolrecypty.objects;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Star implements Serializable, Parcelable {

    private String starName;
    private String starImage;
    private Bitmap bitmapStarImage;

    public Star(String starName, String starImage) {
        this.starName = starName;
        this.starImage = starImage;
    }

    public Star(String starName, Bitmap bitmapStarImage) {
        this.starName = starName;
        this.bitmapStarImage = bitmapStarImage;
    }

    protected Star(Parcel in) {
        starName = in.readString();
        starImage = in.readString();
        bitmapStarImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Star> CREATOR = new Creator<Star>() {
        @Override
        public Star createFromParcel(Parcel in) {
            return new Star(in);
        }

        @Override
        public Star[] newArray(int size) {
            return new Star[size];
        }
    };

    public String getStarName() {
        return starName;
    }

    public String getStarImage() {
        return starImage;
    }

    public Bitmap getBitmapStarImage() {
        return bitmapStarImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(starName);
        dest.writeString(starImage);
        dest.writeParcelable(bitmapStarImage, flags);
    }
}
