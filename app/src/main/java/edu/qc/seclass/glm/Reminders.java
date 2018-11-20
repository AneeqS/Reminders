package edu.qc.seclass.glm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Reminders extends AppCompatActivity {


    //The list view component.
    ListView reminderListView;

    //The arrayAdapter for the list view component.
    static ArrayAdapter arrayAdapter;

    //The arrayList that will be displayed to the user.
    static ArrayList<String> reminderList;

    //Getting the database that was created in the Welcome Activity.
    DBHelper dbHelper = WelcomeActivity.dbHelper;

    //This holds the typeId key that is used for the database query.
    int typeId;

    //This holds the listId key that is used for the database query.
    int listId;

    //This is the intent variable.
    Intent intent;

    //The view for the alert Dialog.
    public View view;

    //The text the user writes for the add/search option.
    EditText editText;


    //This method creates the options menu and attaches it to the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //This method is called when the user selects a certain option from the menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        //This code runs when user selects the add option.
        if(item.getItemId() == R.id.add_new){

            //This is a variable for the Alert dialog that is displayed to the user.
            AlertDialog dialog;

            //Building the Alert Dialog with certain attributes.
            AlertDialog.Builder builder = new AlertDialog.Builder(Reminders.this);

            // Get the layout inflater
            LayoutInflater inflater = Reminders.this.getLayoutInflater();

            //Sets the view to the layout for the Alert Dialog.
            view = inflater.inflate(R.layout.dialog_layout, null);

            //Finds the editText from the Alert Dialog.
            editText = view.findViewById(R.id.add_text);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)

                    //Sets the title of the Alert Dialog.
                    .setTitle("Add")

                    // Add action buttons
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                        //Runs when Add button is selected.
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            //Shows the user written text in logs.
                            Log.i("text", editText.getText().toString());

                            //Adds the text to the database/list.
                            reminderList.add(editText.getText().toString());
                            dbHelper.makeNewReminder(editText.getText().toString(), ++typeId, listId);
                            reminderList = dbHelper.getAllRemindersFromListAndType(listId, typeId);
                            //Updates the listView component.
                            arrayAdapter.notifyDataSetChanged();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        //Does nothing when the Cancel button is selected.
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            //Creates the dialog.
            dialog = builder.create();

            //Display the dialog.
            dialog.show();

            //Sets the background color.
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));

        }


        //This code runs when user selects the search option.
        if(item.getItemId() == R.id.search){

            //This is a variable for the Alert dialog that is displayed to the user.
            AlertDialog dialog;

            //Building the Alert Dialog with certain attributes.
            AlertDialog.Builder builder = new AlertDialog.Builder(Reminders.this);

            // Get the layout inflater
            LayoutInflater inflater = Reminders.this.getLayoutInflater();

            //Sets the view to the layout for the Alert Dialog.
            view = inflater.inflate(R.layout.dialog_layout, null);

            //Finds the editText from the Alert Dialog.
            editText = view.findViewById(R.id.add_text);


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)

                    //Sets the title of the Alert Dialog.
                    .setTitle("Search")


                    // Add action buttons
                    .setPositiveButton("Search", new DialogInterface.OnClickListener() {

                        //Runs when Search button is selected.
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            //Shows the user written text in logs.
                            Log.i("text", editText.getText().toString());

                            //Goes to the search activity.
                            Intent intent = new Intent(getApplicationContext(), Search.class);
                            intent.putExtra("search", editText.getText().toString());
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            //Creates the dialog.
            dialog = builder.create();

            //Display the dialog.
            dialog = builder.show();

            //Sets the background color.
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        }

        return false;
    }


    //Runs when the activity is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        //Gets the intent from the previous activity.
        intent = getIntent();

        //Gets listId from the previous activity.
        typeId = intent.getIntExtra("typeId", -1);

        //Gets typeId from the previous activity.
        listId = intent.getIntExtra("listId", -1);

        //Gets all the list from the database.
        reminderList = dbHelper.getAllRemindersFromListAndType(listId, ++typeId);

        //Finds the list view component.
        reminderListView = findViewById(R.id.reminderListView);

        //attaches the array adapter.
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reminderList);

        //sets the adapter to the list.
        reminderListView.setAdapter(arrayAdapter);

        //updates the arrayList and listView.
        arrayAdapter.notifyDataSetChanged();


        //Runs when the user clicks on an item from the listView
        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Goes to the Edit Reminder activity.
                Intent intent = new Intent(getApplicationContext(), EditReminder.class);
                intent.putExtra("reminderId", i);
                startActivity(intent);

            }
        });

        //Runs when the user long clicks on an item from the listView.
        reminderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //index of the item that will be deleted.
                final int itemToDelete = i;

                //Makes a new dialog.
                new AlertDialog.Builder(Reminders.this)

                        //Sets the icon.
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        //Sets the title.
                        .setTitle("Are you sure?")

                        //Sets the message that is displayed.
                        .setMessage("Do you want to delete this")

                        //Adds the Yes button.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Removes the selected item from the database/arrayList.
                                reminderList.remove(itemToDelete);

                                //Updates the listView.
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)

                        //Displays the dialog.
                        .show();

                return true;


            }
        });
    }
}
