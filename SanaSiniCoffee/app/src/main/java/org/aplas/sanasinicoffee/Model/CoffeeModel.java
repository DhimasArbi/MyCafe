package org.aplas.sanasinicoffee.Model;

import com.google.firebase.firestore.DocumentId;

public class CoffeeModel {

    @DocumentId
    String coffeid;
    String nama, deskripsi, gambar;
    int jumlah, harga;

    public CoffeeModel() {
    }

    public CoffeeModel(String coffeid, String nama, String deskripsi, String gambar, int jumlah, int harga) {
        this.coffeid = coffeid;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getCoffeid() {
        return coffeid;
    }

    public void setCoffeid(String coffeid) {
        this.coffeid = coffeid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "CoffeeModel{" +
                "coffeid='" + coffeid + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", gambar='" + gambar + '\'' +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                '}';
    }
}
