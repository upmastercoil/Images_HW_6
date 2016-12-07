package il.co.upmaster.test.images_hw_6.data;

import android.provider.BaseColumns;

public interface FilesContract {
    int DB_VERSION = 1;
    String DB_NAME = "ImageFiles.db";

    interface RobotEntry extends BaseColumns {

        String TABLE_NAME = "imagefiles";
        String COLUMNS_NAME = "name";
    }
}
