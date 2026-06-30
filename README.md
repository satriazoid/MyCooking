# MyCooking

**MyCooking** adalah aplikasi manajemen resep masakan berbasis Android yang dikembangkan menggunakan Java dan SQLite. Aplikasi ini memungkinkan pengguna untuk mencatat, melihat, mengedit, dan menghapus resep masakan favorit mereka.

## Fitur Utama

- **Autentikasi**: Fitur Login sederhana untuk mengamankan akses ke aplikasi.
- **Manajemen Resep (CRUD)**:
  - **Tambah Resep**: Menambahkan resep baru lengkap dengan nama, kategori, bahan, estimasi harga, foto, dan rating.
  - **Daftar Resep**: Menampilkan semua koleksi resep dalam list yang rapi (menggunakan RecyclerView).
  - **Detail Resep**: Melihat informasi lengkap dari satu resep tertentu.
  - **Ubah Resep**: Memperbarui informasi resep yang sudah ada.
  - **Hapus Resep**: Menghapus resep dari koleksi.
- **Penyimpanan Lokal**: Menggunakan database SQLite untuk menyimpan data secara permanen di perangkat.

## Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java
- **Database**: SQLite
- **UI Framework**: Android XML
- **Minimum SDK**: Android 24 (atau sesuai konfigurasi project)

## Struktur Database

Aplikasi ini menggunakan database SQLite dengan detail sebagai berikut:
- **Nama Database**: _# Rahasia_
- **Nama Tabel**: _# Rahasia_

**Kolom Tabel:**
| Nama Kolom | Tipe Data | Deskripsi |
| :--- | :--- | :--- |
| `id_resep` | INTEGER | Primary Key (Auto Increment) |
| `nim_verifikasi` | TEXT | NIM Pembuat (Default: 231011400324) |
| `nama_menu` | TEXT | Nama masakan |
| `kategori_menu` | TEXT | Kategori (Misal: Tradisional, Modern, dll) |
| `bahan_resep` | TEXT | Daftar bahan-bahan masakan |
| `estimasi_harga` | INTEGER | Perkiraan biaya bahan |
| `foto_uri_path` | TEXT | Path/URI foto masakan |
| `skor_rating` | REAL | Rating masakan (0.0 - 5.0) |

## Cara Menjalankan

1. Clone repository ini.
2. Buka project di **Android Studio**.
3. Tunggu hingga proses Gradle Sync selesai.
4. Hubungkan perangkat Android atau jalankan Emulator.
5. Klik tombol **Run 'app'**.

## Author

- **Nama**: Satria Alfarizki
- **NIM**: *****
