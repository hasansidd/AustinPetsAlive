package com.hasan.austinpetsalive;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hasan.austinpetsalive.model.Cat;

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
    static ArrayList<Cat> catInfo = new ArrayList<>();

    static ArrayList<String> catNamesArray = new ArrayList<>();
    static ArrayList<String> catURLsArray = new ArrayList<>();
    DownloadCatAdoptionInfo downloadCatInfoTask;

    String dogJSON;
    String catJson;
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
            dogJSON = task.execute("https://raw.githubusercontent.com/hasansidd/petScraper/master/petScaperJSON.txt").get();
        } catch (Exception e) {
            e.printStackTrace();
        }


        downloadCatInfoTask = new DownloadCatAdoptionInfo();
        downloadCatInfoTask.execute("https://www.austinpetsalive.org/adopt/cats/");

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
            dogInfo = gson.fromJson(dogJSON, new TypeToken<ArrayList<Dog>>() {}.getType());

            gson = new Gson();
            dogInfo = gson.fromJson(catJson, new TypeToken<ArrayList<Cat>>() {}.getType());

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
                Elements catID =doc.select("a[rel=bookmark]");

                for (Element e : catURLs) {
                    Cat cat = new Cat();
                    cat.addURL(e.attr("src"));
                    catInfo.add(cat);
                }

                for (int i = 0; i < catInfo.size(); i++) {
                    catInfo.get(i).setName(catNames.get(i).text());
                }

                Log.i("catinfosize", String.valueOf(catInfo.size()));
                for (int i = 0; i < catInfo.size(); i++) {
                    catInfo.get(i).setIDURL("https://www.austinpetsalive.org" + catID.get(i).attr("href"));
                }

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

    public static void getIDinfo(Integer... i) {
        //Details about each animal
        try {
            String URL = catInfo.get(i[0]).getIDURL();
            Document docID = Jsoup.connect(URL).get();
            Elements catIDStats = docID.select("td");
            Elements catDescription = docID.select("span[id=lbDescription]");
            Elements catExtraUrls = docID.select("img[id=Photo1]");

            catInfo.get(i[0]).setSex(catIDStats.get(1).text());
            catInfo.get(i[0]).setBreed(catIDStats.get(2).text());
            catInfo.get(i[0]).setWeight(catIDStats.get(3).text());
            catInfo.get(i[0]).setDOB(catIDStats.get(4).text());
            catInfo.get(i[0]).setAge(catIDStats.get(5).text());
            catInfo.get(i[0]).setLocation(catIDStats.get(6).text());

            catInfo.get(i[0]).setDescription(catDescription.text());

            for (int k = 0; k < catExtraUrls.size(); k++) {
                catInfo.get(i[0]).URL.add(catExtraUrls.get(k).attr("src"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

