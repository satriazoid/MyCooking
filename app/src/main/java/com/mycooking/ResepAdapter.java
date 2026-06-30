package com.mycooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.ViewHolder> {

    private final Context          ctx;
    private final List<ResepModel> list;
    private final DatabaseHelper   db;
    private final Runnable         refresh;

    public ResepAdapter(Context ctx, List<ResepModel> list,
                        DatabaseHelper db, Runnable refresh) {
        this.ctx     = ctx;
        this.list    = list;
        this.db      = db;
        this.refresh = refresh;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx)
                     .inflate(R.layout.item_resep, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ResepModel r = list.get(pos);

        h.tvNama    .setText(r.getNamaMenu());
        h.tvKategori.setText(r.getKategoriMenu());
        h.tvHarga   .setText("Rp " + String.format("%,d", r.getEstimasiHarga()));
        h.ratingBar .setRating(r.getSkorRating());

        // Glide — load gambar secara asinkron (anti-lag & anti-OOM)
        Glide.with(ctx)
             .load(Uri.parse(r.getFotoUriPath()))
             .placeholder(R.drawable.ic_placeholder_food)
             .error(R.drawable.ic_placeholder_food)
             .centerCrop()
             .into(h.imgFoto);

        // Klik item -> Detail
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, DetailResepActivity.class);
            i.putExtra(DetailResepActivity.EXTRA_ID, r.getIdResep());
            ctx.startActivity(i);
        });

        // Tombol Edit
        h.btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(ctx, EditResepActivity.class);
            i.putExtra(EditResepActivity.EXTRA_ID, r.getIdResep());
            ctx.startActivity(i);
        });

        // Tombol Hapus — konfirmasi dialog dulu
        h.btnHapus.setOnClickListener(v ->
            new AlertDialog.Builder(ctx)
                .setTitle("🚮 Hapus Resep")
                .setMessage("Hapus \"" + r.getNamaMenu() + "\" secara permanen?")
                .setPositiveButton("Hapus", (d, w) -> {
                    db.deleteData(r.getIdResep());
                    refresh.run();
                })
                .setNegativeButton("Batal", null)
                .show()
        );
    }

    @Override public int getItemCount() { return list.size(); }

    // ============ ViewHolder ============
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto;
        TextView  tvNama, tvKategori, tvHarga;
        RatingBar ratingBar;
        View      btnEdit, btnHapus;

        ViewHolder(@NonNull View v) {
            super(v);
            imgFoto   = v.findViewById(R.id.imgFoto);
            tvNama    = v.findViewById(R.id.tvNama);
            tvKategori= v.findViewById(R.id.tvKategori);
            tvHarga   = v.findViewById(R.id.tvHarga);
            ratingBar = v.findViewById(R.id.ratingBar);
            btnEdit   = v.findViewById(R.id.btnEdit);
            btnHapus  = v.findViewById(R.id.btnHapus);
        }
    }
}
