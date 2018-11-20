package edu.qc.seclass.glm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    //Creates the database.
    static DBHelper dbHelper;

    //Variable for the button.
    public Button bEnter;

    //runs when the activity is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //makes the database.
        dbHelper = new DBHelper(this.getApplicationContext());

        //adds to the logs upon creating the database.
        Log.i("Welcome Activity", "Finished creating");

        //finds the button component.
        bEnter = (Button)findViewById(R.id.buttonEnter);

        //runs when the button is pressed.
        bEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //goes to the reminder list activity.
                Intent buenter = new Intent(WelcomeActivity.this, ReminderListActivity.class);
                startActivity(buenter);
            }
        });
    }
}
