package il.co.upmaster.test.images_hw_6;

import android.os.Environment;

/**
 * Created by Admin on 12/5/2016.
 */

public interface App {
    public static int CODE_CAMERA_OK = 169;
    public static final int WRITE_EXTERNAL_PERMISSION = 52;
    public static final String LOG_TAG = "hw_6";
    public static final String EXTENSION_FILE = ".png";
    public static final String PATH_DIR = Environment.DIRECTORY_PICTURES;
    public static final long ERROR_DB = -1;
    public static final int TIMES_CHECK_DB = 10;
}
