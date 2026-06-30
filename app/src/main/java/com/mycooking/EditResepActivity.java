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
 * EditResepActivity — Form edit resep yang sudah ada.
 * Data lama di-load otomatis dari SQLite berdasarkan id yang dikirim via Intent.
 */
public class EditResepActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";

    private TextInputLayout   layoutNama, layoutBahan, layoutHarga;
    private TextInputEditText editNama, editBahan, editHarga;
    private Spinner           spinnerKategori;
    private RatingBar         ratingBar;
    private MaterialButton    btnPilihFoto, btnUpdate;
    private String            fotoUriString = "";
    private DatabaseHelper    dbHelper;
    private int               resepId;

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
                        btnPilihFoto.setText("🖼️  Foto Diganti ✓");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resep);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Resep");
        }

        dbHelper        = new DatabaseHelper(this);
        layoutNama      = findViewById(R.id.layoutNama);
        layoutBahan     = findViewById(R.id.layoutBahan);
        layoutHarga     = findViewById(R.id.layoutHarga);
        editNama        = findViewById(R.id.editNama);
        editBahan       = findViewById(R.id.editBahan);
        editHarga       = findViewById(R.id.editHarga);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        ratingBar       = findViewById(R.id.ratingBar);
        btnPilihFoto    = findViewById(R.id.btnPilihFoto);
        btnUpdate       = findViewById(R.id.btnUpdate);

        // Setup Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this, R.array.kategori_menu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);

        resepId = getIntent().getIntExtra(EXTRA_ID, -1);
        if (resepId == -1) { finish(); return; }

        // Isi field dengan data lama
        isiDataLama();

        btnPilihFoto.setOnClickListener(v -> bukaGallery());
        btnUpdate.setOnClickListener(v -> updateData());
    }

    private void isiDataLama() {
        ResepModel data = dbHelper.getResepById(resepId);
        if (data == null) { finish(); return; }

        editNama.setText(data.getNamaMenu());
        editBahan.setText(data.getBahanResep());
        editHarga.setText(String.valueOf(data.getEstimasiHarga()));
        ratingBar.setRating(data.getSkorRating());
        fotoUriString = data.getFotoUriPath();
        if (!fotoUriString.isEmpty()) btnPilihFoto.setText("🖼️  Foto Dipilih ✓");

        // Set spinner ke kategori tersimpan
        String[] cats = getResources().getStringArray(R.array.kategori_menu);
        for (int i = 0; i < cats.length; i++) {
            if (cats[i].equals(data.getKategoriMenu())) {
                spinnerKategori.setSelection(i);
                break;
            }
        }
    }

    private void bukaGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                       | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryLauncher.launch(intent);
    }

    private void updateData() {
        String nama     = editNama.getText().toString().trim();
        String bahan    = editBahan.getText().toString().trim();
        String kategori = spinnerKategori.getSelectedItem().toString();
        float  rating   = ratingBar.getRating();

        layoutNama.setError(null);
        layoutBahan.setError(null);
        layoutHarga.setError(null);

        if (nama.isEmpty())  { layoutNama.setError("Nama menu wajib diisi!");            return; }
        if (bahan.isEmpty()) { layoutBahan.setError("Bahan-bahan tidak boleh kosong!");  return; }

        int harga;
        try {
            harga = Integer.parseInt(editHarga.getText().toString().trim());
        } catch (NumberFormatException e) {
            layoutHarga.setError("Masukkan nominal angka yang valid!");
            return;
        }

        int rows = dbHelper.updateData(resepId, nama, kategori, bahan,
                                       harga, fotoUriString, rating);
        if (rows > 0) {
            Toast.makeText(this, "Resep berhasil diperbarui! ✅",
                           Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal memperbarui resep.",
                           Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
