package com.hasan.austinpetsalive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hasan.austinpetsalive.model.Cat;
import com.hasan.austinpetsalive.model.Dog;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    static ArrayList<Dog> dogInfo = new ArrayList<>();
    static ArrayList<Cat> catInfo = new ArrayList<>();

    String dogJSON;
    String catJSON;
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

    public void printCatInfo(int index) {
        Log.i("Name", catInfo.get(index).getName());
        Log.i("Sex", catInfo.get(index).getSex());
        Log.i("Breed", catInfo.get(index).getBreed());
        Log.i("Weight", catInfo.get(index).getWeight());
        Log.i("DOB", catInfo.get(index).getDOB());
        Log.i("Age", catInfo.get(index).getAge());
        Log.i("Location", catInfo.get(index).getLocation());
        Log.i("ID", catInfo.get(index).getID());
        Log.i("URL", catInfo.get(index).getURL().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadDogJSONData dogTask = new DownloadDogJSONData();

        try {
            dogJSON = dogTask.execute("https://raw.githubusercontent.com/hasansidd/petScraper/master/dogScaperJSON.txt").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class DownloadDogJSONData extends AsyncTask<String, Void, String> {

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
            dogInfo = gson.fromJson(dogJSON, new TypeToken<ArrayList<Dog>>() {
            }.getType());

            DownloadCatJSONData catTask = new DownloadCatJSONData();
            try {
                catJSON = catTask.execute("https://raw.githubusercontent.com/hasansidd/petScraper/master/catScaperJSON.txt").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class DownloadCatJSONData extends AsyncTask<String, Void, String> {

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
            catInfo = gson.fromJson(catJSON, new TypeToken<ArrayList<Cat>>() {
            }.getType());

            Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
            startActivity(intent);
            finish();

        }
    }

}

