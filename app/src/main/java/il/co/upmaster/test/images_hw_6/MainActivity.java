package il.co.upmaster.test.images_hw_6;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import static il.co.upmaster.test.images_hw_6.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    private FileManager fileManager;
    //private HashMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        String name = generateFileName();
        fileManager = new FileManager(name, this, resultImage);
    }

    private String generateFileName() {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(1000);
        return String.valueOf(randomNumber);
    }
}
