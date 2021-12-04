package org.aplas.sanasinicoffee.Model;

public class CartModel {
    String nama, gambar, coffeeid;
    int totalHarga, jumlah;

    public CartModel() {
    }

    public CartModel(String nama, String gambar, String coffeeid, int totalHarga, int jumlah) {
        this.nama = nama;
        this.gambar = gambar;
        this.coffeeid = coffeeid;
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
}
