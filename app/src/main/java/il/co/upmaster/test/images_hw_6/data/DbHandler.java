package il.co.upmaster.test.images_hw_6.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import il.co.upmaster.test.images_hw_6.App;
import il.co.upmaster.test.images_hw_6.ImageFile;

import static android.provider.BaseColumns._ID;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.RobotEntry.COLUMNS_NAME;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.RobotEntry.TABLE_NAME;

public class DbHandler {

    private DbOpenHelper dbOpenHelper;

    public DbHandler(Context context) {

        dbOpenHelper = new DbOpenHelper(context);
    }

    public long insert(ImageFile imageFile) {

        //Get The DB
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return App.ERROR_DB;

        //Special type of KV object, perfect for working with DB.
        ContentValues values = generateContentValues(imageFile.getName());

        //Perform the insertion
        long id = -1;
        try {
            id = db.insert(TABLE_NAME, null, values);
        } finally {
            //Must be performed
            db.close();
        }
        if (id != App.ERROR_DB)
            imageFile.setId(id);
        else
            return App.ERROR_DB;

        return id;
    }

    public void delete(long id) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        try {
            db.delete(TABLE_NAME, _ID + "=?", new String[]{String.valueOf(id)});
        } finally {
            db.close();
        }
    }

    public void clearAll() {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db == null)
            return;

        try {
            db.delete(TABLE_NAME, null, null);
        } finally {
            db.close();
        }
    }

    private ContentValues generateContentValues(String name) {
        ContentValues values = new ContentValues();
        values.put(COLUMNS_NAME, name);
        return values;
    }
}
