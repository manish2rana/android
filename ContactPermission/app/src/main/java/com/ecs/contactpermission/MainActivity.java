package com.ecs.contactpermission;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ecs.adapter.ContactAdapter;
import com.ecs.pojo.ContactDetail;

import java.util.ArrayList;
import java.util.List;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public static final int READ_CONTACT_PERMISSION = 101;
    public static final int READ_ALL_CONTACT_PERMISSION = 102;
    public static final int REQUEST_READ_CONTACT = 201;
    public static final int WRITE_CONTACT_PERMISSION = 103;


    private ListView lv_contactList;

    private ExtendedEditText eetContactName;
    private ExtendedEditText eetContactNo;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eetContactName = findViewById(R.id.eetContactName);
        eetContactNo = findViewById(R.id.eetContactNo);
        layout = findViewById(R.id.layout);
    }

    public void writeContact(View view) {
        if (eetContactName.getText().toString().length() > 0 &&
                eetContactNo.getText().toString().length() == 10) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                getContacts(0);
                return;
            }

            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS);
                if (flag) {
                    doWriteContact();
                } else {
                    showSnackbar("WRITE CONTACTS PERMISSION ALREADY ENABLED");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_CONTACTS},
                            WRITE_CONTACT_PERMISSION);
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, WRITE_CONTACT_PERMISSION);
            }
        } else {
            showSnackbar("Enter Contact Name/Number");
        }
    }

    public void doWriteContact() {
        boolean success = insertContact(getContentResolver(), eetContactName.getText().toString().trim(), eetContactNo.getText().toString().trim());
        if (success) {
            showSnackbar("Contact details Inserted");
            getContacts(1);         // update contact list
        } else {
            showSnackbar("Unable to insert Contact details");
        }
    }

    public static boolean insertContact(ContentResolver contactAdder, String firstName, String mobileNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName).build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, TYPE_MOBILE).build());
        try {
            contactAdder.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
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
                showSnackbar("READ CONTACTS PERMISSION ALREADY ENABLED");
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
                showSnackbar("READ CONTACTS PERMISSION ALREADY ENABLED");
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        READ_ALL_CONTACT_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_ALL_CONTACT_PERMISSION);
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

                    lv_contactList = findViewById(R.id.lv_contactList);
                    ContactAdapter adapter = new ContactAdapter(getApplicationContext(), contactList);
                    lv_contactList.setAdapter(adapter);
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
            case READ_CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts(0);
                } else {
                    showSnackbar("READ CONTACTS PERMISSION DENIED");
                }
                break;

            case READ_ALL_CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts(1);
                } else {
                    showSnackbar("READ CONTACTS PERMISSION DENIED");
                }
                break;

            case WRITE_CONTACT_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteContact();
                } else {
                    showSnackbar("WRITE CONTACTS PERMISSION DENIED");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                        String txt = "Contact Name = " + name + "\nContact Number = " + number;
                        textView.setText(txt);
                        textView.setVisibility(View.VISIBLE);
                    }
                    c.close();
                }
                break;
        }
    }
}
