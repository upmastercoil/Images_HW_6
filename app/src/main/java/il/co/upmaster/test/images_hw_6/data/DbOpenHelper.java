package il.co.upmaster.test.images_hw_6.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.DB_NAME;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.DB_VERSION;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.RobotEntry.COLUMNS_NAME;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.RobotEntry.TABLE_NAME;

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createSql = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMNS_NAME + " TEXT UNIQUE ," +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String deleteSql = "DROP TABLE IF EXIST " + COLUMNS_NAME + ";";
        sqLiteDatabase.execSQL(deleteSql);
        onCreate(sqLiteDatabase);
    }
}
