package com.example.bksh1.dogcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.example.bksh1.dogcare.ExpenseTracker.ExpenseActivity;
import com.example.bksh1.dogcare.ExpenseTracker.ExpenseListFragment;
import com.example.bksh1.dogcare.LoginRegister.LoginActivity;

public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //all child item is CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (finalI == 0) //Dog Breed
                    {
                        Intent intent = new Intent(MainActivity.this, DogBreedActivity.class);
                        startActivity(intent);
                    }

                   else if (finalI == 1) //Appointment
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 2) //Clicker
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 3) //Track Expenses
                    {
                        Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 4) //Pet Hospital
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    else if (finalI == 5) //About
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
