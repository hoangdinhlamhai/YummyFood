package com.example.yummyfood.Adapter;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper {
    private static final String DATABASE_NAME = "dbYummyFood.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public void processCopy() {
        // Luôn luôn sao chép lại cơ sở dữ liệu từ assets với mỗi lần chạy ứng dụng
        try {
            deleteOldDatabase();  // Xóa cơ sở dữ liệu cũ nếu có
            copyDatabaseFromAsset();  // Sao chép cơ sở dữ liệu mới từ assets
            Log.d("DatabaseHelper", "Database copied and overwritten successfully");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error copying and overwriting database", e);
        }
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    // Phương thức xóa cơ sở dữ liệu cũ nếu có
    private void deleteOldDatabase() {
        File dbFile = new File(getDatabasePath());
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                Log.d("DatabaseHelper", "Old database deleted successfully");
            } else {
                Log.e("DatabaseHelper", "Failed to delete old database");
            }
        }
    }

    // Phương thức sao chép cơ sở dữ liệu từ thư mục assets
    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();  // Tạo thư mục nếu chưa có
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


