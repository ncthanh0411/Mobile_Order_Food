package com.example.food_order_application_2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_order_application_2.R;

public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        viewFlipper = (ViewFlipper)view.findViewById(R.id.flipper);
        viewFlipper.setFlipInterval(5000); //5 sec
        viewFlipper.setAutoStart(true);
        flipperImages();

        return view;
    }
    public void flipperImages() {
        //viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        //viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
}
