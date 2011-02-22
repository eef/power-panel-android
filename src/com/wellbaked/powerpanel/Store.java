package com.wellbaked.powerpanel;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.HashMap;

public class Store {

   private static final String DATABASE_NAME = "powerpanel";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "computers";

   private Context context;
   private SQLiteDatabase db;

   private SQLiteStatement insertStmt;
   private static final String INSERT = "insert into " + TABLE_NAME + "(private_key, mac_address, host_name, os_info, display_name, last_ip) values (?, ?, ?, ?, ?, ?)";

   public Store(Context context) {
      this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
   }

   public long insert(String private_key, String mac_address, String host_name, String os_info, String display_name, String last_ip) {
	  this.insertStmt = this.db.compileStatement(INSERT);
      this.insertStmt.bindString(1, private_key);
      this.insertStmt.bindString(2, mac_address);
      this.insertStmt.bindString(3, host_name);
      this.insertStmt.bindString(4, os_info);
      this.insertStmt.bindString(5, display_name);
      this.insertStmt.bindString(6, last_ip);
      return this.insertStmt.executeInsert();
   }

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }
   

   public HashMap<String, String> selectAll() {
      HashMap<String, String> list = new HashMap<String, String>();
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "private_key, mac_address, host_name, os_info, display_name, last_ip" }, 
        null, null, null, null, "host_name desc");
      if (cursor.moveToFirst()) {
         do {
        	 System.out.println(cursor.getString(0));
        	 System.out.println(cursor.getString(2));
        	 list.put(cursor.getString(0), cursor.getString(2));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }
   
   public String computerCount() {
	   SQLiteStatement count_statement = this.db.compileStatement("SELECT count(*) FROM " + TABLE_NAME);
	   return count_statement.simpleQueryForString();
   }

   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, private_key TEXT, mac_address TEXT, host_name TEXT, os_info TEXT, display_name TEXT, last_ip TEXT)");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}