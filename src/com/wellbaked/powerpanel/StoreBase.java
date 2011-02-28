package com.wellbaked.powerpanel;

import java.util.HashMap;

import com.wellbaked.powerpanel.StoreBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class StoreBase {
	
	private Context context;
	private String table;
	private static final String DATABASE_NAME = "powerpanel";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private Cursor result;
	private String[] columns;
	private Integer column_count;
	private String insert;
	
	/**
	 * 
	 * StoreBase
	 * StoreBase can be extended to give a sort of active record pattern
	 * 
	 * @param context the parent context
	 * @param table the models table
	 * @param columns the columns which will be selected
	 */
	public StoreBase(Context context, String table, String[] columns, String insert) {
		this.context = context;
	    this.table = table;
	    this.columns = columns;
	    this.insert = insert;
		OpenHelper openHelper = new OpenHelper(this.context);
	    this.db = openHelper.getWritableDatabase();
	}
	
	/**
	 * 
	 * @param id
	 * @return HashMap containing a column name => field value mapping
	 */
	public HashMap<String, String> edit(String id){
		HashMap<String, String> ret = new HashMap<String, String>();
		result = this.db.query(this.table, this.columns, "id = ?", new String[] {id}, null, null, null, "1");
		column_count = result.getColumnCount();
		if(result.moveToFirst()) {
			 ret = parseFields(result);
		}
		return ret;
	}
	
	public long insert(String[] values) {
		this.insertStmt = this.db.compileStatement(this.insert);
		Integer no_values = values.length;
		for(Integer i = 1; i <= no_values; i++) {
			this.insertStmt.bindString(i, values[i-1]);
		}
	    return this.insertStmt.executeInsert();
	}
	
	/**
	 * 
	 * @return HashMap this returns all the records for the specific model
	 */
	public HashMap<String, HashMap<String, String>> all() {
		HashMap<String, HashMap<String, String>> ret = new HashMap<String, HashMap<String, String>>();
		Cursor result = this.db.query(this.table, this.columns, null, null, null, null, null);
		column_count = result.getColumnCount();
	    if (result.moveToFirst()) {
	    	do {
	    		HashMap<String, String> fields = parseFields(result);
 	    		ret.put(result.getString(4), fields);
	    	} while (result.moveToNext());
	    }
	    if (result != null && !result.isClosed()) {
	    	result.close();
	    }
	    return ret;
	}
	
	/**
	 * Delete all the records for this model
	 */
	public void deleteAll() {
		this.db.delete(this.table, null, null);
	}
	
	/**
	 * @return String number of records
	 */
	public String count() {
		SQLiteStatement count_statement = this.db.compileStatement("SELECT count(*) FROM " + this.table);
		return count_statement.simpleQueryForString();
	}
	
	public void update(String id, Object record){
		System.out.println("update Object called");
	}
	
	public void destroy(String id) {
		
	}
	
	public void create() {
		
	}
	
	/**
	 * @param fields a record from the database
	 * @return HashMap mapping of column name => field value
	 */
	private HashMap<String, String> parseFields(Cursor fields) {
		HashMap<String, String> res = new HashMap<String, String>();
		for(Integer i = 0; i < column_count; i++) {
			res.put(fields.getColumnName(i), fields.getString(i));
		}
		return res;
	}
	
	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE  computers (id INTEGER PRIMARY KEY, private_key TEXT, mac_address TEXT, host_name TEXT, os_info TEXT, display_name TEXT, last_ip TEXT)");
			db.execSQL("CREATE TABLE  events (id INTEGER PRIMARY KEY, event_date TEXT, event_type TEXT");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS computers");
			db.execSQL("DROP TABLE IF EXISTS events");
			onCreate(db);
		}
	}
}
