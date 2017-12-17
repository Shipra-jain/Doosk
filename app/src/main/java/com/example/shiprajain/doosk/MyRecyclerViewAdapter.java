package com.example.shiprajain.doosk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    private ImageLoader mImageLoader = AppController.getInstance().getImageLoader();
    public MyRecyclerViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        if (mImageLoader == null)
            mImageLoader = AppController.getInstance().getImageLoader();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        FeedItem feedItem = feedItemList.get(position);
        customViewHolder.bindData(feedItem);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected NetworkImageView imageView;
        private FeedItem mFeedItem;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (NetworkImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }

        public void bindData(FeedItem feedItem) {
            mFeedItem = feedItem;
            imageView.setImageUrl(mFeedItem.getThumbnailUrl(), mImageLoader);
        }

        @Override
        public void onClick(View v) {
            // Handle the onClick event for the ViewHolder
            // Send single item click data to SingleItemView Class
            Intent intent = new Intent(mContext, SingleItemView.class);
            intent.putExtra("originalImage", mFeedItem.getOriginalSizeUrl()); //Original size url here
            // Start SingleItemView Class
            mContext.startActivity(intent);
        }
    }
}
