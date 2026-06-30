package com.mycooking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * TambahResepActivity — Form penambahan resep baru.
 * Validasi inline (TextInputLayout.setError) + try-catch NumberFormatException.
 */
public class TambahResepActivity extends AppCompatActivity {

    private TextInputLayout   layoutNama, layoutBahan, layoutHarga;
    private TextInputEditText editNama, editBahan, editHarga;
    private Spinner           spinnerKategori;
    private RatingBar         ratingBar;
    private MaterialButton    btnPilihFoto, btnSimpan;
    private String            fotoUriString = "";
    private DatabaseHelper    dbHelper;

    private final ActivityResultLauncher<Intent> galleryLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        getContentResolver().takePersistableUriPermission(
                            uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        fotoUriString = uri.toString();
                        btnPilihFoto.setText("🖼️  Foto Dipilih ✓");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.cafe_text_dark));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Tambah Resep");
        }

        dbHelper      = new DatabaseHelper(this);
        layoutNama    = findViewById(R.id.layoutNama);
        layoutBahan   = findViewById(R.id.layoutBahan);
        layoutHarga   = findViewById(R.id.layoutHarga);
        editNama      = findViewById(R.id.editNama);
        editBahan     = findViewById(R.id.editBahan);
        editHarga     = findViewById(R.id.editHarga);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        ratingBar     = findViewById(R.id.ratingBar);
        btnPilihFoto  = findViewById(R.id.btnPilihFoto);
        btnSimpan     = findViewById(R.id.btnSimpan);

        // Setup Spinner kategori dengan custom layout agar teks berwarna hitam/gelap
        // Baik untuk tampilan tertutup (R.layout.spinner_item) maupun dropdown (R.layout.spinner_dropdown_item)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.kategori_menu, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        btnPilihFoto.setOnClickListener(v -> bukaGallery());
        btnSimpan.setOnClickListener(v -> simpanData());
    }

    private void bukaGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                       | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryLauncher.launch(intent);
    }

    private void simpanData() {
        String nama     = editNama.getText().toString().trim();
        String bahan    = editBahan.getText().toString().trim();
        String kategori = spinnerKategori.getSelectedItem().toString();
        float  rating   = ratingBar.getRating();

        layoutNama.setError(null);
        layoutBahan.setError(null);
        layoutHarga.setError(null);

        if (nama.isEmpty())  { layoutNama.setError("Nama menu wajib diisi!");            return; }
        if (bahan.isEmpty()) { layoutBahan.setError("Bahan-bahan tidak boleh kosong!");  return; }
        if (fotoUriString.isEmpty()) {
            Toast.makeText(this, "Pilih foto menu terlebih dahulu!",
                           Toast.LENGTH_SHORT).show();
            return;
        }

        int harga;
        try {
            harga = Integer.parseInt(editHarga.getText().toString().trim());
        } catch (NumberFormatException e) {
            layoutHarga.setError("Masukkan nominal angka yang valid!");
            return;
        }

        long hasil = dbHelper.insertData(nama, kategori, bahan,
                                         harga, fotoUriString, rating);
        if (hasil != -1) {
            Toast.makeText(this, "Resep berhasil ditambahkan ke MyCooking!✨",
                           Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan resep.",
                           Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
