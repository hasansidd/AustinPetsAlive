package com.hasan.austinpetsalive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    static ArrayList<Dog> dogInfo = new ArrayList<>();

    static ArrayList<String> catNamesArray = new ArrayList<>();
    static ArrayList<String> catURLsArray = new ArrayList<>();
    DownloadCatAdoptionInfo downloadCatInfoTask;

    String JSONurl;
    OkHttpClient client = new OkHttpClient();

    public void printDogInfo(int index) {
        Log.i("Name", dogInfo.get(index).getName());
        Log.i("Sex", dogInfo.get(index).getSex());
        Log.i("Breed", dogInfo.get(index).getBreed());
        Log.i("Weight", dogInfo.get(index).getWeight());
        Log.i("DOB", dogInfo.get(index).getDOB());
        Log.i("Age", dogInfo.get(index).getAge());
        Log.i("Location", dogInfo.get(index).getLocation());
        Log.i("ID", dogInfo.get(index).getID());
        Log.i("URL", dogInfo.get(index).getURL().toString());
        Log.i("Energy Level", dogInfo.get(index).getEnergyLevel());
        Log.i("Child Level", dogInfo.get(index).getChildLevel());
        Log.i("Cat Level", dogInfo.get(index).getCatLevel());
        Log.i("Dog Level", dogInfo.get(index).getDogLevel());
        Log.i("Home Alone", dogInfo.get(index).getHomeAlone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadJSONData task = new DownloadJSONData();
        try {
            JSONurl = task.execute("https://raw.githubusercontent.com/hasansidd/petScraper/master/petScaperJSON.txt").get();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //downloadCatInfoTask = new DownloadCatAdoptionInfo();
        //downloadCatInfoTask.execute("https://www.austinpetsalive.org/adopt/cats/");



    }


    public class DownloadJSONData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {
            Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Log.i("test",JSONurl);

            dogInfo = gson.fromJson(JSONurl, new TypeToken<ArrayList<Dog>>() {
            }.getType());

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
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

                catNamesArray = new ArrayList<>(catNamesArray.subList(0, (catNamesArray.size() - 5))); //gets rid of non-dog names at the end of HTML source (stuff like "sign up for updates", etc)

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