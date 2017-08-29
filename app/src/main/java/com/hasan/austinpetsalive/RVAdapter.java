package com.hasan.austinpetsalive;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DogViewHolder> {
    int clickCounter=1;
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


        @Override
        public void onClick(View v) {
            clickCounter++;
            Log.i("clickcounter1",String.valueOf(clickCounter));

            if (dogInfo.get(getAdapterPosition()).getURL().size()-1 < clickCounter) {
                clickCounter=1;
            }

            Log.i("clickcounter2",String.valueOf(clickCounter)+"/"+(dogInfo.get(getAdapterPosition()).getURL().size()-1));

            Log.i("Index position is: ", String.valueOf(getAdapterPosition()));
            dogImageCounter.setText(clickCounter + "/" + (dogInfo.get(getAdapterPosition()).URL.size()-1));
            Picasso.with(v.getContext()).load(dogInfo.get(getAdapterPosition()).getURL().get(clickCounter)).into(dogImageView);
        }

        public DogViewHolder(View itemView) {
            super(itemView);
            Log.i("test", "8");
            dogImageView = (ImageView) itemView.findViewById(R.id.Image);
            dogImageCounter = (TextView) itemView.findViewById(R.id.imageCounter);
            dogNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            dogSexTextView = (TextView) itemView.findViewById(R.id.sexTextView);
            dogBreedTextView = (TextView) itemView.findViewById(R.id.breedTextView);
            dogWeightTextView = (TextView) itemView.findViewById(R.id.weightTextView);
            dogAgeTextView = (TextView) itemView.findViewById(R.id.ageTextView);
            dogDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            Log.i("test", "9");

            itemView.setOnClickListener(this);
        }
    }

    List<Dog> dogInfo;
    Context context;

    RVAdapter(List<Dog> dogInfo, Context context) {
        this.dogInfo = dogInfo;
        this.context = context;
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tabbed, viewGroup, false);
        Log.i("test", "6");
        DogViewHolder dvh = new DogViewHolder(v);
        Log.i("test", "7");
        return dvh;
    }

    @Override
    public void onBindViewHolder(DogViewHolder dogViewHolder, final int position) {
        clickCounter=1;
        Picasso.with(dogViewHolder.cardView.getContext()).load(dogInfo.get(position).getURL().get(0)).into(dogViewHolder.dogImageView);
        dogViewHolder.dogImageCounter.setText(clickCounter + "/" + (dogInfo.get(position).URL.size()-1));
        dogViewHolder.dogNameTextView.setText(dogInfo.get(position).getName());
        dogViewHolder.dogSexTextView.setText("Sex: " + dogInfo.get(position).getSex());
        dogViewHolder.dogBreedTextView.setText("Breed: " + dogInfo.get(position).getBreed());
        dogViewHolder.dogWeightTextView.setText("Weight: " + dogInfo.get(position).getWeight());
        dogViewHolder.dogAgeTextView.setText("Age: " + dogInfo.get(position).getAge());
        dogViewHolder.dogDescriptionTextView.setText(dogInfo.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dogInfo.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.i("test", "13");
        super.onAttachedToRecyclerView(recyclerView);
    }

}
