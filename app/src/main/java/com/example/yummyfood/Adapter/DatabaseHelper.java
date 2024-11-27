package com.example.yummyfood.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbYummyFood.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        processCopy();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần tạo bảng ở đây vì bạn đang sao chép từ assets
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cập nhật cơ sở dữ liệu nếu cần
    }

    public void processCopy() {
        File dbFile = new File(getDatabasePath());
        if (!dbFile.exists()) {
            try {
                copyDatabaseFromAsset();
                Log.d("DatabaseHelper", "Database copied successfully");
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error copying database", e);
            }
        } else {
            Log.d("DatabaseHelper", "Database already exists, no need to copy");
        }
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdirs();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}