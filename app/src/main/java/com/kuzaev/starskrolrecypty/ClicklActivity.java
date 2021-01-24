package com.kuzaev.starskrolrecypty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuzaev.starskrolrecypty.objects.Star;

import java.io.Serializable;

public class ClicklActivity extends AppCompatActivity implements Serializable {

    Star star;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickl);
        imageView = findViewById(R.id.imageViewStar);
        textView = findViewById(R.id.textViewStar);

        star = getIntent().getParcelableExtra("star");
        textView.setText(star.getStarName());
        imageView.setImageBitmap(star.getBitmapStarImage());
    }
}