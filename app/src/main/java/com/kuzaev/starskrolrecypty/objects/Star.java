package com.kuzaev.starskrolrecypty.objects;

import android.graphics.Bitmap;

public class Star {

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

    public String getStarName() {
        return starName;
    }

    public String getStarImage() {
        return starImage;
    }

    public Bitmap getBitmapStarImage() {
        return bitmapStarImage;
    }
}
