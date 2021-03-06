package com.game.training.programmer.android.tic_tac_toe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_singleGame, button_multiGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_singleGame = (Button) findViewById(R.id.button_singleGame);
        button_singleGame.setOnClickListener(this);
        button_multiGame = (Button) findViewById(R.id.button_multiGame);
        button_multiGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_singleGame:
                intent = new Intent(this, SingleLogic.class);
                startActivity(intent);
                break;
            case R.id.button_multiGame:
                intent = new Intent(this, MultiLogic.class);
                startActivity(intent);
                break;
        }
    }
}
