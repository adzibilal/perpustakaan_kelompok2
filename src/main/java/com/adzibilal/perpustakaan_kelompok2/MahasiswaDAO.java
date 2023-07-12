/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ridwannurjaman
 */
public class MahasiswaDAO {
    private static final String TABLE_NAME = "Mahasiswa";
    private Connection connection;

    public MahasiswaDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }
    
     public String tambahMahasiswa(Mahasiswa mahasiswa) throws SQLException {
        String query = "SELECT * FROM mahasiswa WHERE npm = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, mahasiswa.getNpm());
        ResultSet resultSet = statement.executeQuery();
        String message = "";
        // Menampilkan pesan sesuai hasil pencarian
        if (resultSet.next()) {
            message = "NPM Sudah ada!";
        } else {
            String query1 = "INSERT INTO mahasiswa (npm, nama, jurusan, alamat) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(query1);
            statement1.setString(1, mahasiswa.getNpm());
            statement1.setString(2, mahasiswa.getNama_lengkap());
            statement1.setString(3, mahasiswa.getJurusan());
            statement1.setString(4, mahasiswa.getAlamat());
            statement1.executeUpdate();
            statement1.close();
            message = "Data Berhasil Disimpan";
        }
        return message;
    }
     
     public void hapusMahasiswa(String npm) throws SQLException {
        String query = "DELETE FROM mahasiswa WHERE npm = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, npm);
        statement.executeUpdate();
        statement.close();
    }
     
      public void perbaruiMahasiswa(Mahasiswa mahasiswa) throws SQLException {
        String query = "UPDATE mahasiswa SET nama = ?, jurusan = ?, alamat = ? WHERE npm = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, mahasiswa.getNama_lengkap());
        statement.setString(2, mahasiswa.getJurusan());
        statement.setString(3, mahasiswa.getAlamat());
        statement.setString(4, mahasiswa.getNpm());
        statement.executeUpdate();
        statement.close();
    }
      
     public List<Mahasiswa> semuaMahasiswa() throws SQLException {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String npm = resultSet.getString("npm");
            String nama = resultSet.getString("nama");
            String jurusan = resultSet.getString("jurusan");
            String alamat = resultSet.getString("alamat");
            Mahasiswa mahasiswa = new Mahasiswa(npm, nama, jurusan, alamat);
            mahasiswaList.add(mahasiswa);
        }
        resultSet.close();
        statement.close();
        return mahasiswaList;
    }

    public List<Mahasiswa> cariMahasiswa(String searchQuery) throws SQLException {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa WHERE npm LIKE ? OR nama LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + searchQuery + "%");
        statement.setString(2, "%" + searchQuery + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String npm = resultSet.getString("npm");
            String nama = resultSet.getString("nama");
            String jurusan = resultSet.getString("jurusan");
            String alamat = resultSet.getString("alamat");
            Mahasiswa mahasiswa = new Mahasiswa(npm, nama, jurusan, alamat);
            mahasiswaList.add(mahasiswa);
        }
        resultSet.close();
        statement.close();
        return mahasiswaList;
    }

}
