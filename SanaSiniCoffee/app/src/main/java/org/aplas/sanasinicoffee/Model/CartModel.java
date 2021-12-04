package org.aplas.sanasinicoffee.Model;

public class CartModel {
    String coffeeid, nama, gambar, jenis, ukuran;
    int totalHarga, jumlah;

    public CartModel() {
    }

    public CartModel(String coffeeid, String nama, String gambar, String jenis, String ukuran, int totalHarga, int jumlah) {
        this.coffeeid = coffeeid;
        this.nama = nama;
        this.gambar = gambar;
        this.jenis = jenis;
        this.ukuran = ukuran;
        this.totalHarga = totalHarga;
        this.jumlah = jumlah;
    }

    public String getCoffeeid() {
        return coffeeid;
    }

    public void setCoffeeid(String coffeeid) {
        this.coffeeid = coffeeid;
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
}
