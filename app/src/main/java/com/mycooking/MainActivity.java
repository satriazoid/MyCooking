package com.mycooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView   recyclerView;
    private TextView       tvKosong;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION.SDK_INT) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.cafe_primary));

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setTitleTextColor(getResources().getColor(R.color.cafe_text_dark));
        }

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewResep);
        tvKosong = findViewById(R.id.tvKosong);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        FloatingActionButton fabTambah = findViewById(R.id.fabTambah);
        if (fabTambah != null) {
            fabTambah.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TambahResepActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        muatData();
    }

    private void muatData() {
        List<ResepModel> list = dbHelper.getAllResep();

        if (list == null || list.isEmpty()) {
            if (tvKosong != null) tvKosong.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
        } else {
            if (tvKosong != null) tvKosong.setVisibility(View.GONE);
            if (recyclerView != null) {
                recyclerView.setVisibility(View.VISIBLE);
                ResepAdapter adapter = new ResepAdapter(this, list, dbHelper, this::muatData);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}