package pro.com.ecs.handlemultiplepermissionpro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * handle permission related to CALL_PHONE, CAMERA, READ_EXTERNAL_STORAGE
 */
public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleMultiplePermission(View view) {
        int cameraPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        int callPermission = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE);
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED ||
                callPermission != PackageManager.PERMISSION_GRANTED ||
                readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CODE_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CAMERA permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                }
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CALL_PHONE permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "CALL_PHONE permission denied", Toast.LENGTH_SHORT).show();
                }
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "READ_EXTERNAL_STORAGE permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "READ_EXTERNAL_STORAGE permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
