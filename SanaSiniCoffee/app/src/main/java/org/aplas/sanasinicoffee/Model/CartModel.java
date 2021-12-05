package org.aplas.sanasinicoffee.Model;

public class CartModel {
    String id, nama, gambar, jenis, ukuran, category;
    int totalHarga, jumlah;

    public CartModel() {
    }

    public CartModel(String id, String nama, String gambar, String jenis, String ukuran, String category, int totalHarga, int jumlah) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.jenis = jenis;
        this.ukuran = ukuran;
        this.category = category;
        this.totalHarga = totalHarga;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
