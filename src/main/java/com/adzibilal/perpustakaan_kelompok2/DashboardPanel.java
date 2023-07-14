package com.adzibilal.perpustakaan_kelompok2;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.awt.GridLayout;

public class DashboardPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel totalBukuLabel;
    private JLabel totalMahasiswaLabel;
    private JLabel totalBukuDipinjamLabel;
    private JLabel totalTransaksiPeminjamanLabel;
    private JTable peminjamanTable;

    public DashboardPanel() {
        initComponents();
        loadDataFromDatabase();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("DASHBOARD");
        titleLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel statisticPanel = new JPanel();
        statisticPanel.setLayout(new GridLayout(4, 2));

        JLabel totalBukuTextLabel = new JLabel("Total Buku: ");
        totalBukuTextLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalBukuTextLabel);

        totalBukuLabel = new JLabel();
        totalBukuLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalBukuLabel);

        JLabel totalMahasiswaTextLabel = new JLabel("Total Mahasiswa: ");
        totalMahasiswaTextLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalMahasiswaTextLabel);

        totalMahasiswaLabel = new JLabel();
        totalMahasiswaLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalMahasiswaLabel);

        JLabel totalBukuDipinjamTextLabel = new JLabel("Total Buku Dipinjam: ");
        totalBukuDipinjamTextLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalBukuDipinjamTextLabel);

        totalBukuDipinjamLabel = new JLabel();
        totalBukuDipinjamLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalBukuDipinjamLabel);

        JLabel totalTransaksiPeminjamanTextLabel = new JLabel("Total Transaksi Peminjaman: ");
        totalTransaksiPeminjamanTextLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalTransaksiPeminjamanTextLabel);

        totalTransaksiPeminjamanLabel = new JLabel();
        totalTransaksiPeminjamanLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        statisticPanel.add(totalTransaksiPeminjamanLabel);

        add(statisticPanel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Pinjam");
        tableModel.addColumn("Nama");
        tableModel.addColumn("NPM");
        tableModel.addColumn("Judul");
        tableModel.addColumn("Tanggal Pinjam");
        tableModel.addColumn("Status");

        peminjamanTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(peminjamanTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.SOUTH);
    }

    private void loadDataFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            Statement statement = connection.createStatement();

            // Menghitung total buku
            ResultSet bukuResult = statement.executeQuery("SELECT COUNT(*) AS total FROM buku");
            if (bukuResult.next()) {
                int totalBuku = bukuResult.getInt("total");
                totalBukuLabel.setText(String.valueOf(totalBuku));
            }

            // Menghitung total mahasiswa
            ResultSet mahasiswaResult = statement.executeQuery("SELECT COUNT(*) AS total FROM mahasiswa");
            if (mahasiswaResult.next()) {
                int totalMahasiswa = mahasiswaResult.getInt("total");
                totalMahasiswaLabel.setText(String.valueOf(totalMahasiswa));
            }

            // Menghitung total buku dipinjam
            ResultSet bukuDipinjamResult = statement.executeQuery("SELECT COUNT(*) AS total FROM detail_peminjaman");
            if (bukuDipinjamResult.next()) {
                int totalBukuDipinjam = bukuDipinjamResult.getInt("total");
                totalBukuDipinjamLabel.setText(String.valueOf(totalBukuDipinjam));
            }

            // Menghitung total transaksi peminjaman
            ResultSet transaksiPeminjamanResult = statement.executeQuery("SELECT COUNT(*) AS total FROM peminjaman");
            if (transaksiPeminjamanResult.next()) {
                int totalTransaksiPeminjaman = transaksiPeminjamanResult.getInt("total");
                totalTransaksiPeminjamanLabel.setText(String.valueOf(totalTransaksiPeminjaman));
            }

            // Mendapatkan data peminjaman terbaru
            ResultSet peminjamanResult = statement.executeQuery("SELECT * FROM view_peminjaman ORDER BY Tanggal_Pinjam DESC");
            DefaultTableModel tableModel = (DefaultTableModel) peminjamanTable.getModel();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

            while (peminjamanResult.next()) {
                int idPinjam = peminjamanResult.getInt("ID_Pinjam");
                String nama = peminjamanResult.getString("Nama");
                String npm = peminjamanResult.getString("NPM");
                String judul = peminjamanResult.getString("Judul");
                Date tanggalPinjam = peminjamanResult.getDate("Tanggal_Pinjam");
                String status = peminjamanResult.getString("Status");

                Object[] rowData = { idPinjam, nama, npm, judul, dateFormat.format(tanggalPinjam), status };
                tableModel.addRow(rowData);
            }

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
