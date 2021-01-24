package com.kuzaev.starskrolrecypty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kuzaev.starskrolrecypty.adapter.AdapterStar;
import com.kuzaev.starskrolrecypty.objects.Star;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    List<Star> listStarObject;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {
            recyclerView = findViewById(R.id.recyclerView);
            listStarObject = new ArrayList<>();
        }
        startLogic();
        newAdapter();
    }

    public void newAdapter(){
            AdapterStar adapterStar = new AdapterStar(listStarObject);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapterStar);
            adapterStar.setOnNoteClickListener(new AdapterStar.OnNoteClickListener() {
                @Override
                public void onNoteClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), ClicklActivity.class);
                    intent.putExtra("star", (Serializable) listStarObject.get(position));
                    startActivity(intent);
                }
            });
    }

    public void startLogic() {
        String urlString = "https://www.forbes.ru/rating/403469-40-samyh-uspeshnyh-zvezd-rossii-do-40-let-reyting-forbes";
        StarDownloadTask starDownloadTask = new StarDownloadTask();
        List<Star> listStarString = new ArrayList<>();
        try {
            listStarString = starDownloadTask.execute(urlString).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Star star : listStarString) {
            Bitmap bitmap = null;
            try {
                ImageStarDownloadTask imageStarDownloadTask = new ImageStarDownloadTask();
                bitmap = imageStarDownloadTask.execute(star.getStarImage()).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listStarObject.add(new Star(star.getStarName(), bitmap));
        }
    }

    private static class StarDownloadTask extends AsyncTask<String, Void, List<Star>> {

        @Override
        protected List<Star> doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            List<Star> listStringNameStar;
            try {
                listStringNameStar = new ArrayList<>();
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String lineBuffer;
                String lineBuilder = null;
                StringBuilder stringBuilder = null;
                boolean booleanKeyToLogic = false;

                while ((lineBuffer = bufferedReader.readLine()) != null) {
                    Star star = null;
                    if (lineBuffer.contains("<div class=\"foto foto-top\">")) {
                        Log.i("build", "one");
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(lineBuffer);
                    } else if (lineBuffer.contains("</div>") && stringBuilder != null) {
                        Log.i("build", "three");
                        lineBuilder = stringBuilder.toString();
                        stringBuilder = null;
                        booleanKeyToLogic = true;
                    } else if (stringBuilder != null) {
                        Log.i("build", "two");
                        stringBuilder.append(lineBuffer);
                    }

                    if (booleanKeyToLogic) {
                        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
                        Pattern pattern1 = Pattern.compile("alt=\"(.*?)\"");
                        Matcher matcher = pattern.matcher(lineBuilder);
                        Matcher matcher1 = pattern1.matcher(lineBuilder);

                        while (matcher.find() && matcher1.find()) {
                            star = new Star(matcher1.group(1), matcher.group(1));
                        }
                        listStringNameStar.add(star);
                        booleanKeyToLogic = false;
                    }

                }
                return listStringNameStar;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    private static class ImageStarDownloadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}