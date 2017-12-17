/*
* Whenever an image is tapped in the Recycler View, an intent is fired to open original image.
* SingleItemView handles this intent and displays the selected image in a new activity.
* */
package com.example.shiprajain.doosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class SingleItemView extends AppCompatActivity{
    String originalImageUrl;
    private ImageLoader mImageLoader = AppController.getInstance().getImageLoader();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        // Get the result of thumbnail
        originalImageUrl = i.getStringExtra("originalImage");

        // Locate the ImageView in singleitemview.xml
        NetworkImageView img = (NetworkImageView) findViewById(R.id.originalImage);

        img.setImageUrl(originalImageUrl, mImageLoader);
    }
}
