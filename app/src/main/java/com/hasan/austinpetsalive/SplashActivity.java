package com.hasan.austinpetsalive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    static ArrayList<String> dogNamesArray = new ArrayList<>();
    static ArrayList<String> dogURLsArray = new ArrayList<>();
    DownloadDogAdoptionInfo downloadDogInfoTask;

    static ArrayList<String> catNamesArray = new ArrayList<>();
    static ArrayList<String> catURLsArray = new ArrayList<>();
    DownloadCatAdoptionInfo downloadCatInfoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloadDogInfoTask = new DownloadDogAdoptionInfo();
        downloadDogInfoTask.execute("https://www.austinpetsalive.org/adopt/dogs/");

        downloadCatInfoTask = new DownloadCatAdoptionInfo();
        downloadCatInfoTask.execute("https://www.austinpetsalive.org/adopt/cats/");
    }

    public class DownloadDogAdoptionInfo extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements dogNames = doc.select("h3");
                Elements dogURLs = doc.select("img[class=photo]");
                Elements dogStats = doc.select("div[class=legend-box]");

                Log.i("dogstats",dogStats.toString());
                Log.i("dogstats", String.valueOf(dogStats.size()));


                for (Element e : dogURLs) {
                    dogURLsArray.add(e.attr("src").toString());
                }

                for (Element e : dogNames) {
                    dogNamesArray.add(e.text());
                }

                dogNamesArray=new ArrayList<>(dogNamesArray.subList(0, (dogNamesArray.size()-5))); //gets rid of non-dog names at the end of HTML source (stuff like "sign up for updates", etc)

                Log.i("DogURLS", dogURLsArray.toString());
                Log.i("DogURLS", String.valueOf(dogURLsArray.size()));

                Log.i("DogNames", dogNamesArray.toString());
                Log.i("DogNames", String.valueOf(dogNamesArray.size()));


                return dogNamesArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class DownloadCatAdoptionInfo extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements catNames = doc.select("h3");
                Elements catURLs = doc.select("img[class=photo]");

                for (Element e : catURLs) {
                    catURLsArray.add(e.attr("src").toString());
                }

                for (Element e : catNames) {
                    catNamesArray.add(e.text());
                }

                catNamesArray=new ArrayList<>(catNamesArray.subList(0, (catNamesArray.size()-5))); //gets rid of non-dog names at the end of HTML source (stuff like "sign up for updates", etc)

                Log.i("CatURLS", catURLsArray.toString());
                Log.i("CatURLS", String.valueOf(catURLsArray.size()));

                Log.i("CatNames", catNamesArray.toString());
                Log.i("CatNames", String.valueOf(catNamesArray.size()));


                return catNamesArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}