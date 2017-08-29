package com.hasan.austinpetsalive;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasan.austinpetsalive.model.Cat;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CatRVAdapter extends RecyclerView.Adapter<CatRVAdapter.CatViewHolder> {
    int clickCounter=1;
    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView catNameTextView;
        ImageView catImageView;
        TextView catImageCounter;
        TextView catSexTextView;
        TextView catBreedTextView;
        TextView catWeightTextView;
        TextView catAgeTextView;
        TextView catDescriptionTextView;
        CardView cardView;

        @Override
        public void onClick(View v) {
            clickCounter++;
            Log.i("clickcounter1",String.valueOf(clickCounter));

            if (catInfo.get(getAdapterPosition()).getURL().size()-1 < clickCounter) {
                clickCounter=1;
            }

            Log.i("clickcounter2",String.valueOf(clickCounter)+"/"+(catInfo.get(getAdapterPosition()).getURL().size()-1));

            Log.i("Index position is: ", String.valueOf(getAdapterPosition()));
            catImageCounter.setText(clickCounter + "/" + (catInfo.get(getAdapterPosition()).URL.size()-1));
            Picasso.with(v.getContext()).load(catInfo.get(getAdapterPosition()).getURL().get(clickCounter)).into(catImageView);
        }

        public CatViewHolder(View itemView) {
            super(itemView);
            Log.i("test", "8");
            catImageView = (ImageView) itemView.findViewById(R.id.Image);
            catImageCounter = (TextView) itemView.findViewById(R.id.imageCounter);
            catNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            catSexTextView = (TextView) itemView.findViewById(R.id.sexTextView);
            catBreedTextView = (TextView) itemView.findViewById(R.id.breedTextView);
            catWeightTextView = (TextView) itemView.findViewById(R.id.weightTextView);
            catAgeTextView = (TextView) itemView.findViewById(R.id.ageTextView);
            catDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            Log.i("test", "9");

            itemView.setOnClickListener(this);
        }
    }

    List<Cat> catInfo;
    Context context;

    CatRVAdapter(List<Cat> catInfo, Context context) {
        this.catInfo = catInfo;
        this.context = context;
    }

    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tabbed, viewGroup, false);
        Log.i("test", "6");
        CatViewHolder cvh = new CatViewHolder(v);
        Log.i("test", "7");
        return cvh;
    }

    @Override
    public void onBindViewHolder(CatViewHolder catViewHolder, final int position) {
        clickCounter=1;
        Picasso.with(catViewHolder.cardView.getContext()).load(catInfo.get(position).getURL().get(0)).into(catViewHolder.catImageView);
        catViewHolder.catImageCounter.setText(clickCounter + "/" + (catInfo.get(position).URL.size()-1));
        catViewHolder.catNameTextView.setText(catInfo.get(position).getName());
        catViewHolder.catSexTextView.setText("Sex: " + catInfo.get(position).getSex());
        catViewHolder.catBreedTextView.setText("Breed: " + catInfo.get(position).getBreed());
        catViewHolder.catWeightTextView.setText("Weight: " + catInfo.get(position).getWeight());
        catViewHolder.catAgeTextView.setText("Age: " + catInfo.get(position).getAge());
        catViewHolder.catDescriptionTextView.setText(catInfo.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return catInfo.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
