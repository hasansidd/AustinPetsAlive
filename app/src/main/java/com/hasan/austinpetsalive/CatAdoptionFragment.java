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

import com.hasan.austinpetsalive.model.Cat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatAdoptionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CatAdoptionFragment() {
    }

    public static CatAdoptionFragment newInstance(int sectionNumber) {
        CatAdoptionFragment fragment = new CatAdoptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        RecyclerView rvCat = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        rvCat.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvCat.setLayoutManager(llm);

        CatAdapter adapter = new CatAdapter(SplashActivity.catInfo,getContext());
        rvCat.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

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
        int clickCounter=1;
        Cat mCat;

        @Override
        public void onClick(View v) {
            clickCounter++;

            if (mCat.getURL().size()-1 < clickCounter) {
                clickCounter=1;
            }

            catImageCounter.setText(clickCounter + "/" + (mCat.URL.size()-1));
            Picasso.with(v.getContext()).load(mCat.getURL().get(clickCounter)).into(catImageView);
        }

        public CatViewHolder(View itemView) {
            super(itemView);
            catImageView = (ImageView) itemView.findViewById(R.id.Image);
            catImageCounter = (TextView) itemView.findViewById(R.id.imageCounter);
            catNameTextView = (TextView) itemView.findViewById(R.id.NameTextView);
            catSexTextView = (TextView) itemView.findViewById(R.id.sexTextView);
            catBreedTextView = (TextView) itemView.findViewById(R.id.breedTextView);
            catWeightTextView = (TextView) itemView.findViewById(R.id.weightTextView);
            catAgeTextView = (TextView) itemView.findViewById(R.id.ageTextView);
            catDescriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);

            itemView.setOnClickListener(this);
        }

        public void bind (Cat cat) {
            mCat = cat;
            clickCounter=1;
            Picasso.with(cardView.getContext()).load(mCat.getURL().get(0)).into(catImageView);
            catImageCounter.setText(clickCounter + "/" + (mCat.URL.size()-1));
            catNameTextView.setText(mCat.getName());
            catSexTextView.setText("Sex: " + mCat.getSex());
            catBreedTextView.setText("Breed: " + mCat.getBreed());
            catWeightTextView.setText("Weight: " + mCat.getWeight());
            catAgeTextView.setText("Age: " + mCat.getAge());
            catDescriptionTextView.setText(mCat.getDescription());
        }
    }

    public class CatAdapter extends RecyclerView.Adapter<CatViewHolder> {
        List<Cat> catInfo;
        Context context;

        CatAdapter(List<Cat> catInfo, Context context) {
            this.catInfo = catInfo;
            this.context = context;
        }

        @Override
        public CatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tabbed, viewGroup, false);
            CatViewHolder cvh = new CatViewHolder(v);
            return cvh;
        }

        @Override
        public void onBindViewHolder(CatViewHolder catViewHolder, final int position) {
            catViewHolder.bind(catInfo.get(position));

        }

        @Override
        public int getItemCount() {
            return catInfo.size();

        }
    }
}
