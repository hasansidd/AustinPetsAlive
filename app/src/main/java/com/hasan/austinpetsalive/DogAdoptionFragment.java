package com.hasan.austinpetsalive;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasan.austinpetsalive.model.Dog;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DogAdoptionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public DogAdoptionFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        DogAdapter adapter = new DogAdapter(SplashActivity.dogInfo,getContext());
        rv.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class DogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dogNameTextView;
        ImageView dogImageView;
        TextView dogImageCounter;
        TextView dogSexTextView;
        TextView dogBreedTextView;
        TextView dogWeightTextView;
        TextView dogAgeTextView;
        TextView dogDescriptionTextView;
        CardView cardView;
        Dog mDog;
        int clickCounter=1;

        @Override
        public void onClick(View v) {
            clickCounter++;

            if (mDog.getURL().size()-1 < clickCounter) {
                clickCounter=1;
            }
            dogImageCounter.setText(clickCounter + "/" + (mDog.getURL().size()-1));
            Picasso.with(v.getContext()).load(mDog.getURL().get(clickCounter)).into(dogImageView);
        }

        public DogViewHolder(View itemView) {
            super(itemView);
            dogImageView = (ImageView) itemView.findViewById(R.id.Image);
            dogImageCounter = (TextView) itemView.findViewById(R.id.imageCounter);
            dogNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            dogSexTextView = (TextView) itemView.findViewById(R.id.sexTextView);
            dogBreedTextView = (TextView) itemView.findViewById(R.id.breedTextView);
            dogWeightTextView = (TextView) itemView.findViewById(R.id.weightTextView);
            dogAgeTextView = (TextView) itemView.findViewById(R.id.ageTextView);
            dogDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(this);
        }

        public void bind (Dog dog) {
            mDog = dog;
            clickCounter=1;
            Picasso.with(cardView.getContext()).load(mDog.getURL().get(0)).into(dogImageView);
            dogImageCounter.setText(clickCounter + "/" + (mDog.getURL().size()-1));
            dogNameTextView.setText(mDog.getName());
            dogSexTextView.setText("Sex: " + mDog.getSex());
            dogBreedTextView.setText("Breed: " + mDog.getBreed());
            dogWeightTextView.setText("Weight: " + mDog.getWeight());
            dogAgeTextView.setText("Age: " + mDog.getAge());
            dogDescriptionTextView.setText(mDog.getDescription());
        }
    }

    public class DogAdapter extends RecyclerView.Adapter<DogViewHolder> {
        List<Dog> dogInfo;

        DogAdapter(List<Dog> dogInfo, Context context) {
            this.dogInfo = dogInfo;
        }

        @Override
        public DogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tabbed, viewGroup, false);
            DogViewHolder dvh = new DogViewHolder(v);
            return dvh;
        }

        @Override
        public void onBindViewHolder(DogViewHolder dogViewHolder, final int position) {
            dogViewHolder.bind(dogInfo.get(position));
        }

        @Override
        public int getItemCount() {
            return dogInfo.size();

        }
    }
}