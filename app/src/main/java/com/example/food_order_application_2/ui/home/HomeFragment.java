package com.example.food_order_application_2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_order_application_2.R;

public class HomeFragment extends Fragment {

    ViewFlipper v_flipper;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        int images[] = {R.drawable.beef, R.drawable.noodles, R.drawable.chicken};

        v_flipper = view.findViewById(R.id.v_flipper);

        //loop
        /*for (i = 0; i<image.length; i++)
        {
            flipperImages(images[1]);
        }*/

        //Foreach
        for (int image: images) {
            flipperImages(image);
        }
        return view;
    }
    public void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000); //4sec
        v_flipper.setAutoStart(true);

        //animation
        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setInAnimation(getContext(), android.R.anim.slide_out_right);
    }
}
