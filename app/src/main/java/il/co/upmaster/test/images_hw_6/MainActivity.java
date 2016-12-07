package il.co.upmaster.test.images_hw_6;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import il.co.upmaster.test.images_hw_6.data.DbHandler;

import static android.provider.BaseColumns._ID;
import static il.co.upmaster.test.images_hw_6.data.FilesContract.RobotEntry.COLUMNS_NAME;

public class MainActivity extends AppCompatActivity {

    private FileManager fileManager;
    private DbHandler dbHandler;
    private CursorAdapter adapter;
    private List<ImageFile> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDbHandler();
        setupUi();
    }

    private void setupUi() {

        adapter = getSimpleCursorAdapter();
        //robotsListView.setAdapter(adapter);

        //Find and define the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainRecycler);
        recyclerView.setHasFixedSize(true);

        //Define the Layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Define the adapter
        recyclerView.setAdapter(new ImageFilesAdapter(getImageFiles(), this));
    }

    private SimpleCursorAdapter getSimpleCursorAdapter() {

        String[] from = {COLUMNS_NAME,};
        int[] to = {R.id.linearListItemImageView};

        return new SimpleCursorAdapter(this, //The Context
                R.layout.linear_list_item,//Each item's layout
                null, //Cursor will be updated at onStart
                from, //The Table Cols
                to, //The views' ids to load the cols content by the SAME ORDER
                0); //Flag for querying, almost always unnecessary
    }

    private List<ImageFile> getImageFiles() {

        Cursor cursor = dbHandler.queryAll();
        ImageFile imageFile = null;

        while (cursor.moveToNext()){
            //If there are results, fetch the data and create the robot!
            long id = cursor.getLong(cursor.getColumnIndex(_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMNS_NAME));
            imageFile = new ImageFile(name, id);
            files.add(imageFile);
        }

        cursor.close();

        return files;
    }

    private void setupDbHandler() {

        dbHandler = new DbHandler(this);
    }

    private void updateAdapterCursor(@Nullable Cursor cursor) {
        Cursor oldCursor = adapter.swapCursor(cursor);
        if (oldCursor != null)
            oldCursor.close();
    }

    @Override
    protected void onStart() {

        super.onStart();
        updateAdapterCursor(dbHandler.queryAll());
    }

    @Override
    protected void onStop() {

        super.onStop();
        updateAdapterCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuCamera:
                boolean hasCamera = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
                if (!hasCamera) {
                    Toast.makeText(this, "You have no camera", Toast.LENGTH_SHORT).show();
                    return false;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(this, "You have no camera APP", Toast.LENGTH_SHORT).show();
                    return false;
                }

                startActivityForResult(intent, App.CODE_CAMERA_OK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != App.CODE_CAMERA_OK)
            return;

        if (resultCode != RESULT_OK)
            return;

        Bundle extras = data.getExtras();
        Bitmap resultImage = (Bitmap) extras.get("data");//"data" is the key provided by the android native camera app, maybe other apps use different key!!!

        String name = null;
        try {
            name = saveFileNameDB(generateFileName());
            fileManager = new FileManager(name, this, resultImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(1000);
        return String.valueOf(randomNumber);
    }

    private String saveFileNameDB(String name) throws Exception {
        //Check if the name is exists

        dbHandler = new DbHandler(this);

        ImageFile imageFile = new ImageFile(name);
        long id = dbHandler.insert(imageFile);

        int checkIfDbAlive = 0;
        while (id < 0){
            imageFile.setName(generateFileName());
            id = dbHandler.insert(imageFile);
            if (checkIfDbAlive++ > App.TIMES_CHECK_DB){
                throw new Exception();
            }
        }

        return imageFile.getName();
    }

    private void deleteAll() throws Exception {
        //Clean all files
        throw new Exception();





        //Clean db
        //dbHandler = new DbHandler(this);
        //dbHandler.clearAll();
    }
}
