package il.co.upmaster.test.images_hw_6;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class FileManager {

    private ImageFile imageFile;
    //private String name;
    private Context context;
    private Bitmap resultImage;

    public FileManager(String name, Context context, Bitmap resultImage){
        imageFile = new ImageFile(name + App.EXTENSION_FILE, -1);
        this.context = context;
        this.resultImage = resultImage;

        askForPermission();

        if (isExternalStorageAvailable())
            writeToExternalStorage();
        else
            writeToInternalStorage();
    }

    private void writeToExternalStorage() {

        File file = getFile(imageFile.getName(), App.PATH_DIR);
        if (file == null)
            return;
        try {
            write(new FileOutputStream(file));
        } catch (IOException e) {
            Log.e(App.LOG_TAG, e.getMessage());
        }
    }

    private void writeToInternalStorage() {

        try {
            write(context.openFileOutput(imageFile.getName(), MODE_PRIVATE));
        } catch (IOException e) {
            Log.e(App.LOG_TAG, e.getMessage());
        }
    }

    private void write(FileOutputStream out) throws IOException {

        try {
            resultImage.compress(Bitmap.CompressFormat.PNG, 100, out); // resultImage is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Load the user input from a file
     *
     * @param view the clicked view
     */

    public String loadClickListener(View view) {

        askForPermission();
        if (isExternalStorageAvailable())
            return readFromExternalStorage();
        else
            return readFromInternalStorage();
    }

    private String readFromExternalStorage() {

        File file = getFile(imageFile.getName(), App.PATH_DIR);
        if (file == null)
            return null;
        try {
            return read(new FileInputStream(file));
        } catch (IOException e) {
            Log.e(App.LOG_TAG, e.getMessage());
        }
        return null;
    }

    private String readFromInternalStorage() {

        try {
            return read(context.openFileInput(imageFile.getName()));
        } catch (IOException e) {
            Log.e(App.LOG_TAG, e.getMessage());
        }
        return null;
    }

    @NonNull
    private String read(FileInputStream in) throws IOException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            builder.append(reader.readLine()).append("\n");
        } finally {
            if (reader != null)
                reader.close();
        }
        return builder.toString();
    }

    private boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private File getFile(String fileName, String parentDirectory) {

        File file = new File(Environment.getExternalStoragePublicDirectory(parentDirectory), fileName);

        //Check the parenthood of this bastardo file
        if (!file.getParentFile().exists()) {
            boolean mkdirsSuccess = file.mkdirs();
            if (!mkdirsSuccess) {
                Toast.makeText(context, "Failed To Get the Downloads Folder", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        //Check out the file
        if (!file.exists()) {
            try {
                boolean createNewFileSuccess = file.createNewFile();
                if (!createNewFileSuccess) {
                    Toast.makeText(context, "Failed To Create The New shuki.txt file", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (IOException e) {
                Toast.makeText(context, "Failed To Create The New shuki.txt file", Toast.LENGTH_SHORT).show();
                Log.e(App.LOG_TAG, e.getMessage());
            }
        }

        return file;
    }

    private void askForPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            if (checkSelfPermission(context, writeExternalStorage) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions((Activity) context, new String[]{writeExternalStorage}, App.WRITE_EXTERNAL_PERMISSION);
            }
        }
    }
}
