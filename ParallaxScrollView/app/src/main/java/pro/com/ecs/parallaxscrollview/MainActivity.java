package pro.com.ecs.parallaxscrollview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView header;
    private ListView listView;
    private SupportMapFragment map;

    private View headerPlaceholder;

    private int MAX_ROWS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.iMap);
        map.getMapAsync(MainActivity.this);
        header = findViewById(R.id.header);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.list_header, null);
        headerPlaceholder = listHeader.findViewById(R.id.headerPlaceholder);

        listView.addHeaderView(listHeader);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = headerPlaceholder.getTop();
                    header.setY(Math.max(0, heroTopY + topY));

                    map.getView().setY(topY * 0.5f);
                }
            }
        });


        List<String> modelList = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            modelList.add("Place Name " + i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_row, modelList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
