package com.ecs.runtimepermissions;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecs.pojo.ContactDetail;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int OPEN_CAMERA_PERMISSION = 100;
    public static final int READ_CONTACT_PERMISSION = 101;
    public static final int READ_ALL_CONTACT_PERMISSION = 102;


    public static final int REQUEST_IMAGE_CAPTURE = 200;
    public static final int REQUEST_READ_CONTACT = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void captureCameraImage(View view) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openCamera();
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
            if (flag) {
                openCamera();
            } else {
                Toast.makeText(this, "OPEN CAMERA PERMISSION ALREADY ENABLED", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        OPEN_CAMERA_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, OPEN_CAMERA_PERMISSION);
        }
    }


    public void readSingleContact(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getContacts(0);
            return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
            if (flag) {
                getContacts(0);
            } else {
                Toast.makeText(this, "READ CONTACTS PERMISSION ALREADY ENABLED", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACT_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_PERMISSION);
        }
    }

    public void readAllContact(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getContacts(1);
            return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
            if (flag) {
                getContacts(1);
            } else {
                Toast.makeText(this, "READ CONTACTS PERMISSION ALREADY ENABLED", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        READ_ALL_CONTACT_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_ALL_CONTACT_PERMISSION);
        }
    }

    public void openCamera() {
        Toast.makeText(this, "CAMERA PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getContacts(int value) {
        if (value == 0) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, REQUEST_READ_CONTACT);
        } else {
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            List<ContactDetail> contactList = new ArrayList<>();
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    ContactDetail detail = new ContactDetail();
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        assert pCur != null;
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            detail.setContactName(name);
                            detail.setContactNumber(phoneNo);
                            contactList.add(detail);

                        }
                        pCur.close();
                    }
                }

                if (contactList.size() > 0) {
                    Intent intent = new Intent(this, SampleActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) contactList);
                    startActivity(intent);
                }
            }
            if (cur != null) {
                cur.close();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case OPEN_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            case READ_CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts(0);
                } else {
                    Toast.makeText(this, "READ CONTACTS PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;

            case READ_ALL_CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts(1);
                } else {
                    Toast.makeText(this, "READ CONTACTS PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = null;
                    if (extras != null) {
                        imageBitmap = (Bitmap) extras.get("data");
                    }
                    ImageView mImageView = findViewById(R.id.imgCapture);
                    mImageView.setImageBitmap(imageBitmap);
                    mImageView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, "Not able to capture Image", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_READ_CONTACT:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    assert contactData != null;
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    assert c != null;
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        TextView textView = findViewById(R.id.tvContact);
                        textView.setText(getString(R.string.contact_name) + name + getString(R.string.contact_number) + number);
                        textView.setVisibility(View.VISIBLE);
                    }
                    c.close();
                }
                break;
        }
    }
}
