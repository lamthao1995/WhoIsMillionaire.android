package com.example.demoaltp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DatabaseManager {
	private SQLiteDatabase sqlDB;
	private String pathDB;
	private Context mContext;
	private static final String DB_NAME = "ALTP.sqlite";
	private static final String TAG = "DatabaseManager";
	private ContentValues contentVL = new ContentValues();

	public DatabaseManager(Context context) {
		Log.i(TAG, "DatabaseManager is created...");
		mContext = context;

		pathDB = Environment.getDataDirectory() + "/data/"
				+ mContext.getPackageName() + "/databases/";
		copyDB();
	}

	private void copyDB() {
		try {
			File file = new File(pathDB);
			file.mkdir();

			file = new File(pathDB + DB_NAME);
			if (file.exists()) {
				Log.i(TAG, "Database was exist...");
				return;
			}
			InputStream input = mContext.getAssets().open(DB_NAME);
			FileOutputStream output = new FileOutputStream(file);
			int len;
			byte b[] = new byte[1024];
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.close();
			input.close();
			Log.i(TAG, "copyDB is done...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openDB() {
		if (sqlDB == null || !sqlDB.isOpen())
			sqlDB = SQLiteDatabase.openDatabase(pathDB + DB_NAME, null,
					SQLiteDatabase.OPEN_READWRITE);
	}

	public void closeDB() {
		if (sqlDB != null && sqlDB.isOpen())
			sqlDB.close();
	}

	public void insertDB(String tableName, String... value) {
		contentVL.clear();
		for (int i = 0; i < value.length; i+=2) {
			contentVL.put(value[i], value[i + 1]);
		}
		openDB();
		if (sqlDB.insert(tableName, null, contentVL) > -1) {
			Toast.makeText(mContext, "Insert value is successful",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Insert value is successful...");
		} else {
			Toast.makeText(mContext, "Insert value is failed",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Insert value is failed...");
		}
	}

	public void upDateDB(String tableName, String whereClause,
			String[] whereArgs, String... value) {
		contentVL.clear();
		for (int i = 0; i < value.length; i++) {
			contentVL.put(value[i], value[i + 1]);
		}
		openDB();
		if (sqlDB.update(tableName, contentVL, whereClause, whereArgs) > 0) {
			Toast.makeText(mContext, "Update value is successful",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Update value is successful...");
		} else {
			Toast.makeText(mContext, "Update value is failed",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Update value is failed...");
		}
	}

	public void DeleteDB(String tableName, String whereClause,
			String[] whereArgs) {

		openDB();
		if (sqlDB.delete(tableName, whereClause, whereArgs) > 0) {
			Toast.makeText(mContext, "Delete value is successful",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Delete value is successful...");
		} else {
			Toast.makeText(mContext, "Delete value is failed",
					Toast.LENGTH_SHORT).show();
			Log.i(TAG, "Delete value is failed...");
		}
	}

	public ArrayList<User> getUser(String sql) {
		ArrayList<User> arr = new ArrayList<User>();
		openDB();
		Cursor c = sqlDB.rawQuery(sql, null);

		if (c == null)
			return null;

		c.moveToFirst();

		User user;
		String id, name, level;
		int score, numberQ;

		while (c.isAfterLast() == false) {
			id = c.getString(c.getColumnIndex("ID"));
			name = c.getString(c.getColumnIndex("Name"));
			level = c.getString(c.getColumnIndex("Level"));
			score = c.getInt(c.getColumnIndex("Score"));
			numberQ = c.getInt(c.getColumnIndex("NumberQ"));
			//user = new User(id, name, level, score, numberQ);
			//arr.add(user);
			c.moveToNext();
		}
		c.close();
		return arr;
	}

	public ArrayList<Question> getQuestion(String sql) {
		ArrayList<Question> arr = new ArrayList<Question>();
		openDB();
		Cursor c = sqlDB.rawQuery(sql, null);
		if (c == null)
			return null;
		c.moveToFirst();
		Question q;
		String question, level, casea, caseb, casec, cased;

		while (c.isAfterLast() == false) {
			question = c.getString(c.getColumnIndex("question"));
			level = c.getString(c.getColumnIndex("level"));
			casea = c.getString(c.getColumnIndex("casea"));
			caseb = c.getString(c.getColumnIndex("caseb"));
			casec = c.getString(c.getColumnIndex("casec"));
			cased = c.getString(c.getColumnIndex("cased"));
			q = new Question(question, level, casea, caseb, casec, cased);
			arr.add(q);
			c.moveToNext();
		}
		c.close();
		return arr;
	}
}
