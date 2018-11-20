package edu.qc.seclass.glm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class EditReminder extends AppCompatActivity {

    //The text that holds the reminder text that the user is trying to edit.
    EditText editText;

    //This holds the reminderId key that is used for the database query.
    int reminderId;

    //This is the intent variable.
    Intent intent;



    //Runs when the activity is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        //finds the edit view from the layout.
        editText = findViewById(R.id.editText2);

        //gets the intent from the previous activity.
        intent = getIntent();

        //gets the reminder id from the previous activity.
        reminderId = intent.getIntExtra("reminderId", -1);


        if(reminderId != -1){

            //sets the text that was selected in the previous activity.
            editText.setText(Reminders.reminderList.get(reminderId));

        } else{

            //adds empty message is default value is selected.
            Reminders.reminderList.add("");

            //decreases the size.
            reminderId = Reminders.reminderList.size() - 1;

            //updates the listView.
            Reminders.arrayAdapter.notifyDataSetChanged();
        }

        //runs when the text is edited/changed.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //when text changes, add to database/arrayList.
                Reminders.reminderList.set(reminderId, String.valueOf(charSequence));

                //update the listView.
                Reminders.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
