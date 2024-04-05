package com.mayuresh.annapurnata.Activity;


import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mayuresh.annapurnata.Adapter.CardsAdapter;
import com.mayuresh.annapurnata.Interface.OnScratchListener;
import com.mayuresh.annapurnata.R;

import java.util.ArrayList;
import java.util.List;

public class RewardActivity extends AppCompatActivity implements OnScratchListener {

    private final List<CardsList> cardsLists = new ArrayList<>();
    private TextView coins;
    private RecyclerView cardsRecyclerView;
    private CardsAdapter cardsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reward);

        coins=findViewById(R.id.coins);
        cardsRecyclerView = findViewById(R.id.cardsRecyclerView);
        cardsRecyclerView.setHasFixedSize(true);

        MyConstants.onScratchListener=this;

        cardsRecyclerView.setLayoutManager(new GridLayoutManager(RewardActivity.this, 2));


        CardsList card1 = new CardsList("1",10,false);
        cardsLists.add(card1); // adding first card to the list
        CardsList card2 = new CardsList("1", 10, false);
        cardsLists.add(card2); // adding second card to the list
        CardsList card3 = new CardsList("1", 10,false);
        cardsLists.add(card3); // adding third card to the list
        CardsList card4 = new CardsList( "1", 10, false);
        cardsLists.add(card4); // adding four card to the list
        CardsList card5 = new CardsList( "1", 10, false);
        cardsLists.add(card5); // adding first card to the list
        CardsList card6 = new CardsList("1",  10, false);
        cardsLists.add(card6);
        CardsList card7 = new CardsList("1",  10, false);
        cardsLists.add(card7);
        CardsList card8 = new CardsList("1",  10, false);
        cardsLists.add(card8);
        CardsList card9 = new CardsList("1",  10, false);
        cardsLists.add(card9);
        CardsList card10 = new CardsList("1",  10, false);
        cardsLists.add(card10);

        cardsAdapter=new CardsAdapter(cardsLists,RewardActivity.this);
        cardsRecyclerView.setAdapter(cardsAdapter);

    }
    // Static method to add a scratch card

    @Override
    public void onScratched(int scratchCardListPosition) {
        cardsLists.get(scratchCardListPosition).setScratchStatus(true);

        cardsAdapter.updateData(cardsLists);

        MyConstants.userPoints=MyConstants.userPoints+cardsLists.get(scratchCardListPosition).getWinAmount();
        coins.setText(MyConstants.userPoints+"");
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyConstants.currentActivity = this; // Update current activity when it resumes
    }
}