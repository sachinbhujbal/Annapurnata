package com.mayuresh.annapurnata.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mayuresh.annapurnata.Activity.CardsList;
import com.mayuresh.annapurnata.Activity.ScratchActivity;
import com.mayuresh.annapurnata.R;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {


    private List<CardsList> cardsLists;
    private final Context context;

    public CardsAdapter(List<CardsList> cardsLists, Context context) {
        this.cardsLists = cardsLists;
        this.context = context;
    }

    @NonNull
    @Override
    public CardsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.MyViewHolder holder, int position) {

        CardsList list2 = cardsLists.get(position);

        if(list2.isScratchStatus()) {
            holder.cardImageView.setImageResource(R.drawable.scratched_card);
            holder.wonLayout.setVisibility(View.VISIBLE);
            holder.wonAmount.setText(String.valueOf(list2.getWinAmount()));

        }
        else{
            holder.cardImageView.setImageResource(R.drawable.scratch_card);
            holder.wonLayout.setVisibility(View.GONE);
        }

        holder.rootCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list2.isScratchStatus()){
                    Toast.makeText(context,"Already Scratched",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(context, ScratchActivity.class);
                    intent.putExtra("position",position+"");

                    intent.putExtra("win_amount",list2.getWinAmount()+"");
                    context.startActivity(intent);
                }

            }
        });
    }

    public void updateData(List<CardsList> cardsLists){
        this.cardsLists=cardsLists;

        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return cardsLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final LinearLayout wonLayout;
        private final ImageView cardImageView;
        private final TextView wonAmount;
        private final CardView rootCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wonLayout=itemView.findViewById(R.id.wonLayout);
            cardImageView=itemView.findViewById(R.id.cardImageView);
            wonAmount=itemView.findViewById(R.id.wonAmount);
            rootCardView=itemView.findViewById(R.id.roodCardView);
        }
    }
}