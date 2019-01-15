
package com.infosys.androidassignment.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.infosys.androidassignment.R;
import com.infosys.androidassignment.network.data.Facts;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;



public class FactsRecyclerAdapter extends RecyclerView.Adapter<FactsRecyclerAdapter.FactsHolder> {
    private ArrayList<Facts> factsDetailsList;
    private Context context;
    private Typeface boldTypeface;
    private Typeface regularTypeface;

    public FactsRecyclerAdapter(Context context, ArrayList<Facts> factsDetailsList) {
        this.factsDetailsList = factsDetailsList;
        this.context = context;
        boldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Vollkorn-Bold.ttf");
        regularTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Vollkorn-Regular.ttf");
    }

    @NonNull
    @Override
    public FactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.facts_list_item, parent, false);
        return new FactsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactsHolder holder, int position) {
        // get facts by current position
        Facts facts = factsDetailsList.get(position);

        holder.bindData(facts);
    }

    @Override
    public int getItemCount() {
        return factsDetailsList.size();
    }

    protected class FactsHolder extends RecyclerView.ViewHolder {

        // bind views using ButterKnife
        @BindView(R.id.title)
        TextView mTvTitle;
        @BindView(R.id.description)
        TextView mTvDescription;
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.loading)
        ProgressBar loadingProgressBar;
        private View itemView;

        private FactsHolder(View itemView) {
            super(itemView);

            // initiate ButterKnife
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
        private void bindData(Facts facts) {

            // check if title, description and image url is null
            if (TextUtils.isEmpty(facts.getTitle()) && TextUtils.isEmpty(facts.getDescription())
                    && TextUtils.isEmpty(facts.getTitle())) {
                itemView.setVisibility(View.GONE);
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
            /*if(facts.getTitle().equalsIgnoreCase("Flag")){
                Log.d(TAG, "Flag");
            }*/
            // show loading progress bar while loading image
            String imageUrl = facts.getImageHref();
            if (imageUrl != null) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                // initiate picasso to load image
                Picasso.get()
                        .load(imageUrl)
                        .into(mImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                loadingProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                loadingProgressBar.setVisibility(View.GONE);
                                mImage.setVisibility(View.GONE);
                            }

                        });
            } else {
                // image url is null, hide image view
                mImage.setVisibility(View.GONE);
            }

            // check if title is null
            if (TextUtils.isEmpty(facts.getTitle())) {
                mTvTitle.setVisibility(View.GONE);
            } else {
                mTvTitle.setText(facts.getTitle());
                mTvTitle.setTypeface(boldTypeface);
            }

            // check if description is null
            if (TextUtils.isEmpty(facts.getDescription())) {
                mTvDescription.setVisibility(View.GONE);
            } else {
                mTvDescription.setText(facts.getDescription());
                mTvDescription.setTypeface(regularTypeface);
            }

        }
    }
}
