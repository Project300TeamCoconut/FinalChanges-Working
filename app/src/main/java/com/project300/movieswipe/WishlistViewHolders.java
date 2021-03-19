package com.project300.movieswipe;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishlistViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchName;
    public ImageButton mDeleteButton;
    public Integer counter;



    public WishlistViewHolders(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        // mMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        mMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        mDeleteButton = (ImageButton) itemView.findViewById(R.id.btnDelete);


    }

    @Override
    public void onClick(View v) {
//        counter = counter + 1;
        itemView.setBackgroundColor(Color.parseColor("#348ceb"));

//        if(counter > 0) {
//        }
//        if(counter == 2) {
//            itemView.setBackgroundColor(Color.parseColor(" #ffffff00"));
//            counter = 0;
//        }
    }
}
