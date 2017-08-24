package com.hasan.austinpetsalive;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DogViewHolder> {

    public static class DogViewHolder extends RecyclerView.ViewHolder{
        TextView dogNameTextView;
        ImageView dogImageView;
        TextView dogEnergyLevelTextView;
        TextView dogChildLevelTextView;
        TextView dogCatLevelTextView;
        TextView dogDogLevelTextView;
        TextView dogHomeAloneTextView;
        CardView cardView;

        public DogViewHolder(View itemView) {
            super(itemView);
            Log.i("test","8");
            dogImageView = (ImageView) itemView.findViewById(R.id.Image);
            dogNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            dogEnergyLevelTextView = (TextView) itemView.findViewById(R.id.energyLevel);
            dogChildLevelTextView = (TextView) itemView.findViewById(R.id.childLevel);
            dogCatLevelTextView = (TextView) itemView.findViewById(R.id.catLevel);
            dogDogLevelTextView = (TextView) itemView.findViewById(R.id.dogLevel);
            dogHomeAloneTextView = (TextView) itemView.findViewById(R.id.homeAlone);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            Log.i("test","9");
        }
    }

    List<Dog> dogInfo;

    RVAdapter(List<Dog> dogInfo){
        Log.i("test","14");
        this.dogInfo = dogInfo;
        Log.i("test","15");
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tabbed, viewGroup, false);
        Log.i("test","6");
        DogViewHolder dvh = new DogViewHolder(v);
        Log.i("test","7");
        return dvh;
    }

    @Override
    public void onBindViewHolder(DogViewHolder dogViewHolder, int position) {
        Log.i("test","10");
        Picasso.with(dogViewHolder.cardView.getContext()).load(dogInfo.get(position).getURL().get(0)).into(dogViewHolder.dogImageView);
        dogViewHolder.dogNameTextView.setText(dogInfo.get(position).getName());
        dogViewHolder.dogEnergyLevelTextView.setText("Energy Level: " + dogInfo.get(position).getEnergyLevel());
        dogViewHolder.dogChildLevelTextView.setText("Child Level: " + dogInfo.get(position).getChildLevel());
        Log.i("test","11");
        dogViewHolder.dogCatLevelTextView.setText("Cat Level: " + dogInfo.get(position).getCatLevel());
        dogViewHolder.dogDogLevelTextView.setText("Dog Level: " + dogInfo.get(position).getDogLevel());
        dogViewHolder.dogHomeAloneTextView.setText("Home Alone Level: " + dogInfo.get(position).getHomeAlone());
        Log.i("tfdsest",dogInfo.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.i("test","12");
        Log.i("test", String.valueOf(dogInfo.size()));
        return dogInfo.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.i("test","13");
        super.onAttachedToRecyclerView(recyclerView);
    }

}
