package com.mayuresh.annapurnata.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mayuresh.annapurnata.R;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;


public class ScratchActivity extends AppCompatActivity {

    private boolean scratchRevealed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scratch);

        final ScratchCardLayout scratchCardLayout=findViewById(R.id.scratchCard);
        final ImageView closeBtn = findViewById(R.id.closeBtn);
        final TextView winAmount=findViewById(R.id.wonAmount);

        //  scratchCardLayout.setScratchWidthDip(15);

        final String getPosition=getIntent().getStringExtra("position");
        final String getWinAmount=getIntent().getStringExtra("win_amount");

        winAmount.setText(getWinAmount);

        scratchCardLayout.setScratchListener(new ScratchListener() {
            @Override
            public void onScratchStarted() {

            }

            @Override
            public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int i) {

                if(i>50 && !scratchRevealed){
                    scratchRevealed=true;
                    scratchCardLayout.revealScratch();

                    MyConstants.onScratchListener.onScratched(Integer.parseInt(getPosition));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onScratchComplete() {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}