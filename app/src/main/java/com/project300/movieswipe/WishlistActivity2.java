package com.project300.movieswipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity2 extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mWishlistAdapter;

    private RecyclerView.LayoutManager mWishlistLayoutManager;

    private String currentUserID, currentMovie;

    TextView matchName;

    Context context;

    FirebaseUser currentFirebaseUser;

    ImageButton btnDelete;

    FrameLayout progressOverlay;

    Integer counter = 0;

    String friendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        progressOverlay = findViewById(R.id.progress_overlay);

        context = this.context;
        btnDelete = findViewById(R.id.btnDelete);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            currentUserID = user.getUid();
        }

        friendID = getIntent().getStringExtra("FRIENDID");

        matchName = findViewById(R.id.MatchName);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);


//        mRecyclerView.setFocusable(false);

//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        currentMovie = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.MatchName)).getText().toString();
//                        Toast.makeText(getApplicationContext(), currentMovie, Toast.LENGTH_SHORT).show();
//                        counter = counter + 1;
//
//                    }
//                })
//        );

        //this will allow us to scroll freely through the recycler view with no hicups
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);


        //set a linear layout manager
        mWishlistLayoutManager = new LinearLayoutManager(WishlistActivity2.this);

        //pass this layout manager to the recycler view
        mRecyclerView.setLayoutManager(mWishlistLayoutManager);


        //adapter
        mWishlistAdapter = new WishlistAdapter(getDataSetWishlist(), WishlistActivity2.this);


        //set the adapter to the recycler view
        mRecyclerView.setAdapter(mWishlistAdapter);

        getUserMatchId();

        for(int i=0; i< 100; i++)
        {

        }


    }

    private void getUserMatchId() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("WatchList");

        //if the user wants to get the most updated matchlist then they must go back and re enter this activity
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //it is looking through //will pass the first match to the "match" variable
                    //use get key to get value
                    for (DataSnapshot match : snapshot.getChildren()) {

                        //key is movie name in this case
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        private void FetchMatchInformation(String key) {

        String userID = friendID;
        //"YR0YNOCdvBVWOLVtKuY9tdQCU8c2";


//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentFirebaseUser.getUid()).child("connections").child("WatchList");

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(currentFirebaseUser.getUid()).child("connections").child("WatchList");

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // String userIDD = snapshot.getKey();
                    String name = "";
                    name = key;


                    //  if(snapshot.child("name").getValue()!= null){
                    //
                    //   name = snapshot.child("Users").child(userID).child("connections").child("matches").toString();
                    //   }

                    WishlistObject obj = new WishlistObject(name);
                    resultsWishlist.add(obj);

                    mWishlistAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void deleteFirebase(View view) {

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        currentMovie = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.MatchName)).getText().toString();
//                        Toast.makeText(getApplicationContext(), currentMovie, Toast.LENGTH_SHORT).show();
//
//                        Toast.makeText(MatchesActivity.this, "working",
//                                Toast.LENGTH_LONG).show();

//                        btnDelete.setText("Are You Sure?");
                        // Hannah UserID
                        String userID = "MrdUgrlSthVgF2qaMXGljrWD00w2";
//                        progressOverlay.setVisibility(View.VISIBLE);
                        DatabaseReference userDbWishlist = FirebaseDatabase.getInstance().getReference().child("Users").child(currentFirebaseUser.getUid()).child("connections").child("WatchList");

//        DatabaseReference currentUserConnectionsDB = usersDb.child(userID).child("connections").child("yep").child(FilmName);

                        DatabaseReference driverRefWishlist = userDbWishlist.child(currentMovie);

                        driverRefWishlist.removeValue();

                        //        mMatchesAdapter.notifyItemRemoved(position);
//        mMatchesAdapter.notifyDataSetChanged();

                        Intent i = new Intent(WishlistActivity2.this, WishlistActivity2.class);

                        overridePendingTransition(0, 0);


                        progressOverlay.setVisibility(View.VISIBLE);

//                        progressOverlay.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                startActivity(i);
                            }

                        }, 2000);
//                        progressOverlay.setVisibility(View.INVISIBLE);
                        overridePendingTransition(0, 0);

//                        progressOverlay.setVisibility(View.INVISIBLE);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
    private ArrayList<WishlistObject> resultsWishlist = new ArrayList<WishlistObject>();

    private List<WishlistObject> getDataSetWishlist() {

        return resultsWishlist;

    }

//    @Override
//    public void onBackPressed() {
//        progressOverlay.setVisibility(View.GONE);
////        progressOverlay.setVisibility(View.INVISIBLE);
//        Intent i = new Intent(MatchesActivity.this, MainActivity.class);
////        startActivity(i);
//
//    }

    public void goHome(View view) {
        Intent intent = new Intent(WishlistActivity2.this, MainActivity.class);
        startActivity(intent);
        return;
    }
}