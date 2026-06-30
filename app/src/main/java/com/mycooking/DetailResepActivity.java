package com.mycooking;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;

public class DetailResepActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int resepId = getIntent().getIntExtra(EXTRA_ID, -1);
        if (resepId == -1) { finish(); return; }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ResepModel     resep    = dbHelper.getResepById(resepId);
        if (resep == null)  { finish(); return; }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(resep.getNamaMenu());

        TextView  tvKategori = findViewById(R.id.tvKategori);
        TextView  tvBahan    = findViewById(R.id.tvBahan);
        TextView  tvHarga    = findViewById(R.id.tvHarga);
        RatingBar ratingBar  = findViewById(R.id.ratingBar);
        ImageView imgFoto    = findViewById(R.id.imgFoto);

        tvKategori.setText(resep.getKategoriMenu());
        tvBahan   .setText(resep.getBahanResep());
        tvHarga   .setText("Rp " + String.format("%,d", resep.getEstimasiHarga()));
        ratingBar .setRating(resep.getSkorRating());

        // Glide: load gambar async dari URI yang tersimpan
        Glide.with(this)
             .load(Uri.parse(resep.getFotoUriPath()))
             .placeholder(R.drawable.ic_placeholder_food)
             .error(R.drawable.ic_placeholder_food)
             .centerCrop()
             .into(imgFoto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
