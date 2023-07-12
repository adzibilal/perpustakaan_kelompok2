/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

/**
 *
 * @author ridwannurjaman
 */
public class Mahasiswa {
    private String npm;
    private String nama_lengkap;
    private String jurusan;
    private String alamat;

    public Mahasiswa(String npm, String nama_lengkap, String jurusan, String alamat) {
        this.npm = npm;
        this.nama_lengkap = nama_lengkap;
        this.jurusan = jurusan;
        this.alamat = alamat;
    }
    
    
    
    
    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    
}
