package com.ecs.runtimepermissions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ecs.adapter.ContactAdapter;
import com.ecs.pojo.ContactDetail;

import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private ListView lv_contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        lv_contactList = findViewById(R.id.lv_contactList);
        List<ContactDetail> list=getIntent().getExtras().getParcelableArrayList("list");
        ContactAdapter adapter=new ContactAdapter(this,list);
        lv_contactList.setAdapter(adapter);

    }
}
