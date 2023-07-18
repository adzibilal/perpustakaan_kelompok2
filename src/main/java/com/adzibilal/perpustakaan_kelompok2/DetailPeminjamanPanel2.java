/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

/**
 *
 * @author Lenovo
 */
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailPeminjamanPanel2 extends JPanel {
    private int idPeminjaman;

    public DetailPeminjamanPanel2(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;

        setLayout(new BorderLayout());

        // Panel detail peminjaman
        JPanel detailPanel = new JPanel(new GridLayout(5, 2));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel idLabel = new JLabel("ID Peminjaman:");
        JLabel npmLabel = new JLabel("NPM:");
        JLabel namaLabel = new JLabel("Nama:");
        JLabel tanggalLabel = new JLabel("Tanggal Peminjaman:");
        JLabel statusLabel = new JLabel("Status:");

        JLabel idValueLabel = new JLabel();
        JLabel npmValueLabel = new JLabel();
        JLabel namaValueLabel = new JLabel();
        JLabel tanggalValueLabel = new JLabel();
        JLabel statusValueLabel = new JLabel();

        detailPanel.add(idLabel);
        detailPanel.add(idValueLabel);
        detailPanel.add(npmLabel);
        detailPanel.add(npmValueLabel);
        detailPanel.add(namaLabel);
        detailPanel.add(namaValueLabel);
        detailPanel.add(tanggalLabel);
        detailPanel.add(tanggalValueLabel);
        detailPanel.add(statusLabel);
        detailPanel.add(statusValueLabel);

        // Mengambil data peminjaman dari database berdasarkan ID Peminjaman
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM peminjaman JOIN mahasiswa ON peminjaman.NPM = mahasiswa.NPM WHERE ID_Pinjam = ?");
            stmt.setInt(1, idPeminjaman);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idValueLabel.setText(String.valueOf(idPeminjaman));
                npmValueLabel.setText(rs.getString("NPM"));
                namaValueLabel.setText(rs.getString("Nama"));
                tanggalValueLabel.setText(rs.getString("Tanggal_Pinjam"));
                statusValueLabel.setText(rs.getString("Status"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Menambahkan panel detail ke panel utama
        add(detailPanel, BorderLayout.CENTER);
    }
}
