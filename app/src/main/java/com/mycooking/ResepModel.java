package com.mycooking;

/**
 * Model / POJO untuk satu baris data resep di SQLite.
 * Digunakan oleh DatabaseHelper dan ResepAdapter.
 */
public class ResepModel {

    private int    idResep;
    private String namaMenu;
    private String kategoriMenu;
    private String bahanResep;
    private int    estimasiHarga;
    private String fotoUriPath;
    private float  skorRating;

    public ResepModel(int idResep, String namaMenu, String kategoriMenu,
                      String bahanResep, int estimasiHarga,
                      String fotoUriPath, float skorRating) {
        this.idResep       = idResep;
        this.namaMenu      = namaMenu;
        this.kategoriMenu  = kategoriMenu;
        this.bahanResep    = bahanResep;
        this.estimasiHarga = estimasiHarga;
        this.fotoUriPath   = fotoUriPath;
        this.skorRating    = skorRating;
    }

    // ---------- Getters ----------
    public int    getIdResep()        { return idResep; }
    public String getNamaMenu()       { return namaMenu; }
    public String getKategoriMenu()   { return kategoriMenu; }
    public String getBahanResep()     { return bahanResep; }
    public int    getEstimasiHarga()  { return estimasiHarga; }
    public String getFotoUriPath()    { return fotoUriPath; }
    public float  getSkorRating()     { return skorRating; }

    // ---------- Setters ----------
    public void setIdResep(int v)        { this.idResep = v; }
    public void setNamaMenu(String v)    { this.namaMenu = v; }
    public void setKategoriMenu(String v){ this.kategoriMenu = v; }
    public void setBahanResep(String v)  { this.bahanResep = v; }
    public void setEstimasiHarga(int v)  { this.estimasiHarga = v; }
    public void setFotoUriPath(String v) { this.fotoUriPath = v; }
    public void setSkorRating(float v)   { this.skorRating = v; }
}
