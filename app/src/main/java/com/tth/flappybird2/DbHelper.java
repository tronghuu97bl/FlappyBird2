package com.tth.flappybird2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "FlappyBird2_DB";
    private static final int DATABASE_VERSION = 5;
    private BirdM birdM;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE BIRD (" +
                "BIRD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "BIRD_PRICE INT, " +
                "BIRD_STATE INT, " +
                "BIRD_IMAGE_ID INT," +
                "BIRD_NAME TEXT," +
                "BIRD_UP INT, " +
                "BIRD_DOWN INT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS BIRD");
        onCreate(db);
    }

    public void addBird(BirdM birdM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("BIRD_PRICE", birdM.getPrice());
        boolean tt = birdM.getState();
        if (tt == false)
            cv.put("BIRD_STATE", 0);
        else
            cv.put("BIRD_STATE", 1);
        cv.put("BIRD_IMAGE_ID", birdM.getImageID());
        cv.put("BIRD_NAME", birdM.getName());
        cv.put("BIRD_UP", birdM.getBird_up_id());
        cv.put("BIRD_DOWN", birdM.getBird_down_id());
        long result = db.insert("BIRD", null, cv);
        if (result == -1) {
            Log.d("insert fail", "fail");
        }
        db.close();
    }

    public void updateBird(BirdM birdM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("BIRD_STATE", 1);
        db.update("BIRD", cv, "BIRD_ID = ?", new String[]{String.valueOf(birdM.getBird_id())});
        db.close();
    }

    public void deleteBird() {

    }

    public void deleteListBird() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("BIRD", "BIRD_ID" + " > 0", null);
        db.close();
    }

    public List<BirdM> getListBirdM() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BirdM> list = new ArrayList<>();
        String query = "SELECT * FROM Bird";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BirdM birdM = new BirdM();
                birdM.setBird_id(Integer.parseInt(cursor.getString(0)));
                birdM.setPrice(Integer.parseInt(cursor.getString(1)));
                int tt = Integer.parseInt(cursor.getString(2));
                if (tt == 0)
                    birdM.setState(false);
                else
                    birdM.setState(true);
                birdM.setImageID(Integer.parseInt(cursor.getString(3)));
                birdM.setName(cursor.getString(4));
                birdM.setBird_up_id(Integer.parseInt(cursor.getString(5)));
                birdM.setBird_down_id(Integer.parseInt(cursor.getString(6)));
                list.add(birdM);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void creatListBird() {
        birdM = new BirdM(50, false, R.drawable.bird2, "Chim Vàng", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(100, false, R.drawable.bird3, "Chim Độc ", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(300, false, R.drawable.bird4, "Ong Vàng", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(500, false, R.drawable.bird5, "Giáo Sư", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(1000, false, R.drawable.bird6_down, "Doanh Nhân", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(2000, false, R.drawable.bird7, "Chim Hộp", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(5000, false, R.drawable.bird8_down, "Tình Yêu", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
        birdM = new BirdM(10000, true, R.drawable.bird9_up, "Chim Tuyết", R.drawable.bird_up, R.drawable.bird_down);
        this.addBird(birdM);
    }
}
