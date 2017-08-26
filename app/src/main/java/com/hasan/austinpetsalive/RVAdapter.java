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

    public class DogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dogNameTextView;
        ImageView dogImageView;
        TextView dogEnergyLevelTextView;
        TextView dogChildLevelTextView;
        TextView dogCatLevelTextView;
        TextView dogDogLevelTextView;
        TextView dogHomeAloneTextView;
        CardView cardView;
        int clickCounter=1;

        @Override
        public void onClick(View v) {
            clickCounter++;
            Log.i("clickcounter1",String.valueOf(clickCounter));

            if (dogInfo.get(getAdapterPosition()).getURL().size()-1 < clickCounter) {
                clickCounter=1;
            }

            Log.i("clickcounter2",String.valueOf(clickCounter)+"/"+(dogInfo.get(getAdapterPosition()).getURL().size()-1));

            Log.i("Index position is: ", String.valueOf(getAdapterPosition()));
            Picasso.with(v.getContext()).load(dogInfo.get(getAdapterPosition()).getURL().get(clickCounter)).into(dogImageView);
        }

        public DogViewHolder(View itemView) {
            super(itemView);
            Log.i("test", "8");
            dogImageView = (ImageView) itemView.findViewById(R.id.Image);
            dogNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            dogEnergyLevelTextView = (TextView) itemView.findViewById(R.id.energyLevel);
            dogChildLevelTextView = (TextView) itemView.findViewById(R.id.childLevel);
            dogCatLevelTextView = (TextView) itemView.findViewById(R.id.catLevel);
            dogDogLevelTextView = (TextView) itemView.findViewById(R.id.dogLevel);
            dogHomeAloneTextView = (TextView) itemView.findViewById(R.id.homeAlone);
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
        Picasso.with(dogViewHolder.cardView.getContext()).load(dogInfo.get(position).getURL().get(0)).into(dogViewHolder.dogImageView);
        dogViewHolder.dogNameTextView.setText(dogInfo.get(position).getName());
        dogViewHolder.dogChildLevelTextView.setText("Sex: " + dogInfo.get(position).getSex());
        dogViewHolder.dogEnergyLevelTextView.setText("Breed: " + dogInfo.get(position).getBreed());

        dogViewHolder.dogCatLevelTextView.setText("Weight: " + dogInfo.get(position).getWeight());
        dogViewHolder.dogDogLevelTextView.setText("Age: " + dogInfo.get(position).getAge());
        dogViewHolder.dogHomeAloneTextView.setText(dogInfo.get(position).getDescription());
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
