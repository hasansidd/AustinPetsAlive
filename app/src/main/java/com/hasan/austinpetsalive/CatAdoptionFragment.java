package com.hasan.austinpetsalive;

import android.content.Context;
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

public class CatAdoptionFragment extends Fragment {
    int clickCounter = 0;

    private TextView catNameTextView;
    private ImageView catImageView;

    public CatAdoptionFragment() {
    }

    public static CatAdoptionFragment newInstance(int sectionNumber) {
        CatAdoptionFragment fragment = new CatAdoptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Log.i("section number", Integer.toString(sectionNumber));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

        catNameTextView = (TextView) rootView.findViewById(R.id.NameTextView);
        catImageView = (ImageView) rootView.findViewById(R.id.Image);

//        catNameTextView.setText(SplashActivity.catNamesArray.get(0));
 //       Picasso.with(this.getContext()).load(SplashActivity.catURLsArray.get(0)).into(catImageView);
        clickCounter=0;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        catImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter++;
                Log.i("click counter after", Integer.toString(clickCounter));
                if (clickCounter == SplashActivity.catNamesArray.size() - 1) {
                    clickCounter = 0;
                }
                try {
                    Log.i("image counter", "inside");
                    Picasso.with(getActivity()).load(SplashActivity.catURLsArray.get(clickCounter)).into(catImageView);
                    catNameTextView.setText(SplashActivity.catNamesArray.get(clickCounter));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
