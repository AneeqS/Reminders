package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Reminder.db";

    //Table Names
    private static final String List_Table = "List";
    private static final String Type_Table = "Type";
    private static final String Reminder_Table = "Reminder";



    //Database creation for tables sql statement
    private static final String DATABASE_CREATE_REMINDER_LIST_TABLE = "CREATE TABLE List(listID INTEGER PRIMARY KEY AUTOINCREMENT, listName text NOT NULL);";
    private static final String DATABASE_CREATE_REMINDER_TYPE_TABLE = "CREATE TABLE Type(typeID INTEGER PRIMARY KEY AUTOINCREMENT, typeName text NOT NULL, listID INTEGER, CONSTRAINT fk_type_listID FOREIGN KEY (listID) REFERENCES List(listID)););";
    private static final String DATABASE_CREATE_REMINDER_TABLE = "CREATE TABLE Reminder(reminderID INTEGER PRIMARY KEY AUTOINCREMENT, reminderName text NOT NULL, typeID INTEGER, listID INTEGER NOT NULL, date DATE, CONSTRAINT fk_reminder_typeID FOREIGN KEY (typeID) REFERENCES Type(typeID), CONSTRAINT fk_reminder_listID FOREIGN KEY (listID) REFERENCES List(listID));";
    //private static final String DATABASE_CREATE_ACTIVE_REMINDER_TABLE = "CREATE TABLE ActiveReminder(activeReminderID int PRIMARY KEY AUTOINCREMENT, listID int NOT NULL, reminderID int NOT NULL, CONSTRAINT fk_activeReminder_listID FOREIGN KEY (listID) REFERENCES List(listID), CONSTRAINT fk_activeReminder_reminderID FOREIGN KEY (reminderID) REFERENCES Reminder(reminderID) );";

    // Predefined data
    private static final String DATABASE_CREATE_PREDEFINED_LIST = "INSERT INTO List(listName) VALUES('School'), ('List2'), ('List 3'), ('List 4'), ('List 5');";
    private static final String DATABASE_CREATE_PREDEFINED_TYPE = "INSERT INTO Type(typeName, listID) VALUES('HW', 1) ,('Exam', 1) ;";
    private static final String DATABASE_CREATE_PREDEFINED_REMINDER = "INSERT INTO Reminder(reminderName, typeID, listID, date) VALUES('OS', 1, 1, 12-11-2018 ), ('Java', 1, 1, 12-12-2018), ('CPP', 1, 1, 12-13-2018) ;";

    //Database object
    private static SQLiteDatabase db;

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        initDB();
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_REMINDER_LIST_TABLE);
        database.execSQL(DATABASE_CREATE_REMINDER_TYPE_TABLE);
        database.execSQL(DATABASE_CREATE_REMINDER_TABLE);
        database.execSQL(DATABASE_CREATE_PREDEFINED_LIST);
        database.execSQL(DATABASE_CREATE_PREDEFINED_TYPE);
        database.execSQL(DATABASE_CREATE_PREDEFINED_REMINDER );
        Log.d("DB Helper", "Created Table");
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DBHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS Reminder");
        database.execSQL("DROP TABLE IF EXISTS Type");
        database.execSQL("DROP TABLE IF EXISTS List");
        onCreate(database);
    }

    private void initDB(){
        if(db == null){
            Log.d("DB Helper", "Got DB");
            db = getWritableDatabase();
        }
    }

    public boolean makeNewList(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put("listName", name);
        long result = db.insert(List_Table, null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }

    public boolean makeNewType(int listID,String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put("typeName", name);
        contentValues.put("listID", listID);
        long result = db.insert(Type_Table, null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }

    public boolean makeNewReminder(String name, int typeID, int listID){
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderName", name);
        contentValues.put("typeID", typeID);
        contentValues.put("listID", listID);
        long result = db.insert(Reminder_Table, null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }


    public ArrayList<String> getTypesInList(int listKey){
        Cursor cursor = db.rawQuery("SELECT DISTINCT Type.typeName FROM Type INNER JOIN Reminder ON Type.typeID == Reminder.typeID " +
                " INNER JOIN List ON List.listID = Reminder.listID WHERE List.listID = " + listKey, null);
        ArrayList<String> output = new ArrayList<>();
        if( cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                output.add(cursor.getString(cursor.getColumnIndex("typeName")));
                cursor.moveToNext();
            }
            System.out.println(Arrays.toString(output.toArray()));
            cursor.close();
        }
        return output;
    }

    public ArrayList<String> getAllListNames(){
        Cursor cursor = db.rawQuery("SELECT List.listName FROM List", null);
        ArrayList<String> output = new ArrayList<>();
        if( cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                output.add(cursor.getString(cursor.getColumnIndex("listName")));
                cursor.moveToNext();
            }
            System.out.println(Arrays.toString(output.toArray()));
            cursor.close();
        }
        return output;
    }

    public ArrayList<String> getAllRemindersFromListAndType(int listKey, int typeKey){
        Cursor cursor = db.rawQuery("SELECT Reminder.reminderName FROM Reminder WHERE Reminder.typeID = " + typeKey + " AND " +  " Reminder.listID = " + listKey , null);
        ArrayList<String> output = new ArrayList<>();
        if( cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                output.add(cursor.getString(cursor.getColumnIndex("reminderName")));
                cursor.moveToNext();
            }
            System.out.println(Arrays.toString(output.toArray()));
            cursor.close();
        }
        return output;
    }

    public void deleteReminder() {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete()
    }

    public  ArrayList<String> searchDB(String searchQuery){
        Cursor cursor = db.rawQuery("SELECT Reminder.reminderName FROM Reminder WHERE LOWER(Reminder.reminderName) LIKE " + "'"+ searchQuery.toLowerCase() +"'", null);
        ArrayList<String> output = new ArrayList<>();
        if( cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                output.add(cursor.getString(cursor.getColumnIndex("reminderName")));
                cursor.moveToNext();
            }
            System.out.println(Arrays.toString(output.toArray()));
            cursor.close();
        }
        return output;
    }

}
