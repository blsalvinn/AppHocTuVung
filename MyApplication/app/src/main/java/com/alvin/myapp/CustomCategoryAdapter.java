package com.alvin.myapp;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomCategoryAdapter extends ArrayAdapter<Category> {
    public CustomCategoryAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_template, parent, false);
        }

        TextView categoryTitle = (TextView) convertView.findViewById(R.id.card_title);
        TextView categoryHighscore = (TextView) convertView.findViewById(R.id.card_highscore);
        ImageView categoryImage = (ImageView) convertView.findViewById(R.id.card_image);
        RelativeLayout categoryLayout = (RelativeLayout) convertView.findViewById(R.id.cardLayout);

        categoryTitle.setText(category.title);
        categoryHighscore.setText("Highscore: " + category.highscore);
        categoryImage.setImageResource(category.image);
        categoryLayout.setBackgroundColor(category.color);

        return convertView;
    }
}