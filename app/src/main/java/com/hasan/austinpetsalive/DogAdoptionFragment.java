package com.hasan.austinpetsalive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DogAdoptionFragment extends Fragment {
    int clickCounter = 0;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView dogNameTextView;
    private ImageView dogImageView;
    private TextView dogEnergyLevelTextView;
    private TextView dogChildLevelTextView;
    private TextView dogCatLevelTextView;
    private TextView dogDogLevelTextView;
    private TextView dogHomeAloneTextView;

    public DogAdoptionFragment() {
    }

    public void setInfo (int Index) {
        Log.i("click counter", Integer.toString(clickCounter));
        if (clickCounter == SplashActivity.dogInfo.size() - 1) {
            clickCounter = 0;
        }
        Picasso.with(this.getContext()).load(SplashActivity.dogInfo.get(clickCounter).getURL().get(0)).into(dogImageView);
        dogNameTextView.setText(SplashActivity.dogInfo.get(clickCounter).getName());
        dogEnergyLevelTextView.setText("Energy Level: " + SplashActivity.dogInfo.get(clickCounter).getEnergyLevel());
        dogChildLevelTextView.setText("Child Level: " + SplashActivity.dogInfo.get(clickCounter).getChildLevel());
        dogCatLevelTextView.setText("Cat Level: " + SplashActivity.dogInfo.get(clickCounter).getCatLevel());
        dogDogLevelTextView.setText("Dog Level: " + SplashActivity.dogInfo.get(clickCounter).getDogLevel());
        dogHomeAloneTextView.setText("Home Alone Level: " + SplashActivity.dogInfo.get(clickCounter).getHomeAlone());
    }

    public static DogAdoptionFragment newInstance(int sectionNumber) {
        DogAdoptionFragment fragment = new DogAdoptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        Log.i("section number", Integer.toString(sectionNumber));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

        dogImageView = (ImageView) rootView.findViewById(R.id.Image);
        dogNameTextView = (TextView) rootView.findViewById(R.id.NameTextView);
        dogEnergyLevelTextView = (TextView) rootView.findViewById(R.id.energyLevel);
        dogChildLevelTextView = (TextView) rootView.findViewById(R.id.childLevel);
        dogCatLevelTextView = (TextView) rootView.findViewById(R.id.catLevel);
        dogDogLevelTextView = (TextView) rootView.findViewById(R.id.dogLevel);
        dogHomeAloneTextView = (TextView) rootView.findViewById(R.id.homeAlone);

        setInfo(clickCounter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter++;
                try {
                    setInfo(clickCounter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}