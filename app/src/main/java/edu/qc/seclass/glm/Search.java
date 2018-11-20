package edu.qc.seclass.glm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {


    //The list view component.
    ListView searchListView;

    //The arrayAdapter for the list view component.
    static ArrayAdapter arrayAdapter;

    //The arrayList that will be displayed to the user.
    static ArrayList<String> searchList = new ArrayList<>();

    //Getting the database that was created in the Welcome Activity.
    DBHelper dbHelper = WelcomeActivity.dbHelper;

    //This is the intent variable.
    Intent intent;

    //This is the variable that holds the string of what the user searched. Will be used for query.
    String searchQuery;


    //Runs when the activity is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Gets the intent from the previous activity.
        intent = getIntent();

        //Gets the text to search in the database.
        searchQuery = intent.getStringExtra("search");

        //Displays the search query in the logs.
        Log.i("searchQuery", searchQuery);

        //searches the database.
        searchList = dbHelper.searchDB(searchQuery);

        //finds the listView component.
        searchListView = findViewById(R.id.searchListView);

        //attaches the array adapter.
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchList);

        //sets the adapter to the list.
        searchListView.setAdapter(arrayAdapter);

        //updates the arrayList and listView.
        arrayAdapter.notifyDataSetChanged();


    }
}
