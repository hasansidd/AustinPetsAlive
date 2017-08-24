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
    static ArrayList<Dog> dogInfo = new ArrayList<>();
    DownloadDogAdoptionInfo downloadDogInfoTask;

    static ArrayList<String> catNamesArray = new ArrayList<>();
    static ArrayList<String> catURLsArray = new ArrayList<>();
    DownloadCatAdoptionInfo downloadCatInfoTask;

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

        downloadDogInfoTask = new DownloadDogAdoptionInfo();
        downloadDogInfoTask.execute("https://www.austinpetsalive.org/adopt/dogs/");

        //DownloadDogIDInfo task = new DownloadDogIDInfo();
       //task.execute(24);

        downloadCatInfoTask = new DownloadCatAdoptionInfo();
        downloadCatInfoTask.execute("https://www.austinpetsalive.org/adopt/cats/");
    }

    public class DownloadDogAdoptionInfo extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements dogNames = doc.select("h3");
                Elements dogURLs = doc.select("img[class=photo]");
                Elements dogStats = doc.select("div[class=legend-box]");

                //Dog URL - find URL for each dog and create Dog object
                for (Element e : dogURLs) {
                    Dog dog = new Dog();
                    dog.addURL(e.attr("src"));
                    dogInfo.add(dog);
                }

                //Dog Name - find name for each dog. For loop restricted to size of dogInfo array to prevent extra <h3> content at end of document (i.e. sign up for updates, find us on facebook)
                for (int i = 0; i < dogInfo.size(); i++) {
                    dogInfo.get(i).setName(dogNames.get(i).text());
                }

                //Dog Levels - find compatibility level table for each dog and parses it into object
                for (int i = 0; i < dogStats.size(); i++) {
                    String dogStatsString = dogStats.get(i).text();
                    String temp[] = dogStatsString.split(":");

                    for (int k = 0; k < temp.length; k++) {
                        temp[k] = temp[k].substring(1, 2);
                    }
                    dogInfo.get(i).setEnergyLevel(temp[1]);
                    dogInfo.get(i).setChildLevel(temp[2]);
                    dogInfo.get(i).setCatLevel(temp[3]);
                    dogInfo.get(i).setDogLevel(temp[4]);
                    dogInfo.get(i).setHomeAlone(temp[5]);
                }

                //Dog ID - uses same element as compatibility level table, but instead parses ID
                for (int i = 0; i < dogStats.size(); i++) {
                    String temp = dogStats.get(i).attr("id").toString();
                    temp = temp.replace("LegendBox", "");
                    dogInfo.get(i).setID(temp);
                }

                //check to see if all arrays match, -5 to avoid <h3> elements such as i.e. sign up for updates, find us on facebook
                if ((dogNames.size() - 5) == dogURLs.size() && dogURLs.size() == dogStats.size()) {
                    Log.i("All sizes match", String.valueOf(dogNames.size()-5));
                } else {
                    Log.i("Mismatch in sizes", "0");
                    Log.i("Dog Names", String.valueOf(dogNames.size()));
                    Log.i("Dog URLs", String.valueOf(dogURLs.size()));
                    Log.i("Dog Stats", String.valueOf(dogStats.size()));
                }

                return null;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class DownloadDogIDInfo extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... i) {
            //Details about each animal
            try {
                String URL = "https://www.austinpetsalive.org/adopt/available-dog-details/?ID=" + dogInfo.get(i[0]).getID();
                Document docID = Jsoup.connect(URL).get();
                Elements dogIDStats = docID.select("td");
                Elements dogDescription = docID.select("span[id=lbDescription]");
                Elements dogExtraUrls = docID.select("img[id=Photo1]");

                dogInfo.get(i[0]).setSex(dogIDStats.get(1).text());
                dogInfo.get(i[0]).setBreed(dogIDStats.get(2).text());
                dogInfo.get(i[0]).setWeight(dogIDStats.get(3).text());
                dogInfo.get(i[0]).setDOB(dogIDStats.get(4).text());
                dogInfo.get(i[0]).setAge(dogIDStats.get(5).text());
                dogInfo.get(i[0]).setLocation(dogIDStats.get(6).text());
                dogInfo.get(i[0]).setDescription(dogDescription.text());

                for (int k = 0; k < dogExtraUrls.size(); k++) {
                    dogInfo.get(i[0]).URL.add(dogExtraUrls.get(k).attr("src"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return i[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            printDogInfo(integer);
            super.onPostExecute(integer);
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