package com.example.yummyfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Adapter.TableListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ListTableActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "qlYF.db";
    ListView lv;
    ArrayList<String> myList;
    TableListAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        // Khởi tạo ListView
        lv = findViewById(R.id.rvTableList);
        myList = new ArrayList<>();
        myadapter = new TableListAdapter(this, myList);
        lv.setAdapter(myadapter);

        processCopy();

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor c = database.query("tbBan", new String[]{"tenBan"}, null, null, null, null, null); // Chỉ lấy cột tên bàn
        String data = "";
        c.moveToFirst();
        while(!c.isAfterLast()) {
            // data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2) + "-" + c.getString(3);
            data = c.getString(0);  // Chỉ lấy giá trị từ cột tên bàn
            myList.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    /////////////////khong co sua phan nay

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDatabaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void CopyDatabaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();
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
    ////////////////////////////////////////
}
