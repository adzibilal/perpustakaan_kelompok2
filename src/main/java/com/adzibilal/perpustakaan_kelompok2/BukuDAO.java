/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

/**
 *
 * @author Lenovo
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    private static final String TABLE_NAME = "Buku";
    private Connection connection;

    public BukuDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public void tambahBuku(Buku buku) throws SQLException {
        String query = "INSERT INTO buku (id_buku, judul, penerbit, tahun_terbit) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, buku.getIdBuku());
        statement.setString(2, buku.getJudul());
        statement.setString(3, buku.getPenerbit());
        statement.setInt(4, buku.getTahunTerbit());
        statement.executeUpdate();
        statement.close();
    }

    public void hapusBuku(String idBuku) throws SQLException {
        String query = "DELETE FROM buku WHERE id_buku = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, idBuku);
        statement.executeUpdate();
        statement.close();
    }

    public void perbaruiBuku(Buku buku) throws SQLException {
        String query = "UPDATE buku SET judul = ?, penerbit = ?, tahun_terbit = ? WHERE id_buku = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, buku.getJudul());
        statement.setString(2, buku.getPenerbit());
        statement.setInt(3, buku.getTahunTerbit());
        statement.setString(4, buku.getIdBuku());
        statement.executeUpdate();
        statement.close();
    }

    public List<Buku> semuaBuku() throws SQLException {
        List<Buku> bukuList = new ArrayList<>();
        String query = "SELECT * FROM buku";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String idBuku = resultSet.getString("id_buku");
            String judul = resultSet.getString("judul");
            String penerbit = resultSet.getString("penerbit");
            int tahunTerbit = resultSet.getInt("tahun_terbit");

            Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
            bukuList.add(buku);
        }
        resultSet.close();
        statement.close();
        return bukuList;
    }

    public List<Buku> cariBuku(String searchQuery) throws SQLException {
        List<Buku> bukuList = new ArrayList<>();
        String query = "SELECT * FROM buku WHERE judul LIKE ? OR penerbit LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + searchQuery + "%");
        statement.setString(2, "%" + searchQuery + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String idBuku = resultSet.getString("id_buku");
            String judul = resultSet.getString("judul");
            String penerbit = resultSet.getString("penerbit");
            int tahunTerbit = resultSet.getInt("tahun_terbit");

            Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
            bukuList.add(buku);
        }
        resultSet.close();
        statement.close();
        return bukuList;
    }

}
