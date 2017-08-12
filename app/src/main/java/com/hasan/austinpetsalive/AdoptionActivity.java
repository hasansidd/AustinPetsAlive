package com.hasan.austinpetsalive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdoptionActivity extends AppCompatActivity {
    ArrayList<Bitmap> animalImages = new ArrayList<>();
    ImageView animalImageView;
    TextView animalNameTextView;
    ImageDownloader imageDownloader;
    String animal;
    ArrayList<String> animalNamesArray = new ArrayList<>();
    ArrayList<String> animalURLsArray = new ArrayList<>();

    int imageCounter = 0;
    int clickCounter = 0;

    public void nextDogClick(View view) {
        Log.i("click counter", Integer.toString(clickCounter));
        Log.i("image counter", Integer.toString(imageCounter));
        if (clickCounter < imageCounter-1) {
            if (clickCounter == animalNamesArray.size() -1) {
                clickCounter = 0;
            }
            try {
                Log.i("image counter", "inside");
                clickCounter++;
                animalImageView.setImageBitmap(animalImages.get(clickCounter));
                animalNameTextView.setText(animalNamesArray.get(clickCounter));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);
        animalImageView = (ImageView) findViewById(R.id.Image);
        animalNameTextView = (TextView) findViewById(R.id.NameTextView);
        Intent intent = getIntent();
        animal = intent.getStringExtra("animal");


        switch (animal) {
            case "dog":
                animalNamesArray = SplashActivity.dogNamesArray;
                animalURLsArray = SplashActivity.dogURLsArray;
                break;
            case "cat":
                animalNamesArray = SplashActivity.catNamesArray;
                animalURLsArray = SplashActivity.catURLsArray;
                break;
            default:
                Log.e("No animal parameter","0");
        }

        animalNameTextView.setText(animalNamesArray.get(imageCounter));
        imageDownloader = new ImageDownloader();
        imageDownloader.execute(animalURLsArray.get(imageCounter));
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                animalImages.add(myBitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.i("counter",Integer.toString(imageCounter));
            if (imageCounter == 0) {
                animalImageView.setImageBitmap(animalImages.get(imageCounter));
            }

            if (imageCounter == animalNamesArray.size() - 1) {
                return;
            }

            imageCounter++;
            imageDownloader = new ImageDownloader();
            imageDownloader.execute(animalURLsArray.get(imageCounter));
        }
    }
    public void onBackPressed() {
        imageDownloader.cancel(true);
        finish();
    }

}
