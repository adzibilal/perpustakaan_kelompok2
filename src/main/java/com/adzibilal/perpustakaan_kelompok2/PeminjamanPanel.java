/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PeminjamanPanel extends JPanel {
    private JTextField idPeminjamanField;
    private JComboBox<ComboBoxItem> mahasiswaComboBox;
    private JTextField tanggalPeminjamanField;
    private JTextField statusField;
    private JTextField searchField;
    private JTable peminjamanTable;
    private DefaultTableModel tableModel;

    public PeminjamanPanel() {
        setLayout(new BorderLayout());

        // Panel input data peminjaman
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idPeminjamanLabel = new JLabel("ID Peminjaman:");
        idPeminjamanField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Menggunakan 2 kolom lebar grid
        gbc.fill = GridBagConstraints.HORIZONTAL; // Mengisi seluruh lebar grid
        inputPanel.add(idPeminjamanLabel, gbc);
        gbc.gridy++;
        inputPanel.add(idPeminjamanField, gbc);

        JLabel mahasiswaLabel = new JLabel("Mahasiswa:");
        mahasiswaComboBox = new JComboBox<>();
        gbc.gridy++;
        inputPanel.add(mahasiswaLabel, gbc);
        gbc.gridy++;
        inputPanel.add(mahasiswaComboBox, gbc);

        JLabel tanggalPeminjamanLabel = new JLabel("Tanggal Peminjaman:");
        tanggalPeminjamanField = new JTextField(10);
        gbc.gridy++;
        inputPanel.add(tanggalPeminjamanLabel, gbc);
        gbc.gridy++;
        inputPanel.add(tanggalPeminjamanField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField("dipinjam", 10);
        statusField.setEditable(false); // Default value dipinjam
        gbc.gridy++;
        inputPanel.add(statusLabel, gbc);
        gbc.gridy++;
        inputPanel.add(statusField, gbc);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton detailButton = new JButton("Detail");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(clearButton);

        // Panel pencarian
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Cari:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cari");

        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchFieldPanel.add(searchField);
        searchFieldPanel.add(searchButton);

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchFieldPanel, BorderLayout.CENTER);

        // Tabel data peminjaman
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Peminjaman");
        tableModel.addColumn("Mahasiswa");
        tableModel.addColumn("Tanggal Peminjaman");
        tableModel.addColumn("Status");

        peminjamanTable = new JTable(tableModel);
        peminjamanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(peminjamanTable);

        // Menambahkan komponen ke panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Menampilkan data saat panel dibuat
        loadData();
        loadMahasiswa();

        // Listener untuk tombol tambah
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeminjaman();
            }
        });

        // Listener untuk tombol update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePeminjaman();
            }
        });

        // Listener untuk tombol delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePeminjaman();
            }
        });

        // Listener untuk tombol detail
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = peminjamanTable.getSelectedRow();
                if (selectedRow != -1) {
                    String idPeminjaman = tableModel.getValueAt(selectedRow, 0).toString();
                    String mahasiswa = tableModel.getValueAt(selectedRow, 1).toString();
                    String tanggalPeminjaman = tableModel.getValueAt(selectedRow, 2).toString();
                    String status = tableModel.getValueAt(selectedRow, 3).toString();

                    // Membuka panel detail (popup new window)
                    JFrame detailFrame = new JFrame("Detail Peminjaman");
                    detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    detailFrame.setSize(400, 200);

                    JPanel detailPanel = new JPanel(new GridLayout(4, 2));
                    detailPanel.add(new JLabel("ID Peminjaman:"));
                    detailPanel.add(new JLabel(idPeminjaman));
                    detailPanel.add(new JLabel("Mahasiswa:"));
                    detailPanel.add(new JLabel(mahasiswa));
                    detailPanel.add(new JLabel("Tanggal Peminjaman:"));
                    detailPanel.add(new JLabel(tanggalPeminjaman));
                    detailPanel.add(new JLabel("Status:"));
                    detailPanel.add(new JLabel(status));

                    detailFrame.add(detailPanel);
                    detailFrame.setVisible(true);
                }
            }
        });

        // Listener untuk tombol clear
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Listener untuk tombol cari
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPeminjaman();
            }
        });
    }

    // Mengambil data mahasiswa dari database dan memuatnya ke dalam combo box
    private void loadMahasiswa() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NPM, Nama FROM mahasiswa");

            mahasiswaComboBox.removeAllItems();
            while (rs.next()) {
                String npm = rs.getString("NPM");
                String nama = rs.getString("Nama");
                mahasiswaComboBox.addItem(new ComboBoxItem(npm, nama));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil data peminjaman dari database dan memuatnya ke dalam tabel
    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM peminjaman");

            // Menghapus data tabel sebelumnya
            tableModel.setRowCount(0);

            // Memuat data baru ke dalam tabel
            while (rs.next()) {
                String idPeminjaman = rs.getString("ID_Pinjam");
                String npm = rs.getString("NPM");
                String tanggalPeminjaman = rs.getString("Tanggal_Pinjam");
                String status = rs.getString("Status");

                // Mengambil nama mahasiswa berdasarkan NPM
                String nama = getMahasiswaNameByNPM(npm);

                tableModel.addRow(new Object[]{idPeminjaman, nama, tanggalPeminjaman, status});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menambahkan data peminjaman ke database
    private void addPeminjaman() {
        String idPeminjaman = idPeminjamanField.getText();
        String npm = ((ComboBoxItem) mahasiswaComboBox.getSelectedItem()).getValue();
        String tanggalPeminjaman = tanggalPeminjamanField.getText();
        String status = statusField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO peminjaman (ID_Pinjam, NPM, Tanggal_Pinjam, Status) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, Integer.parseInt(idPeminjaman));
            stmt.setString(2, npm);
            stmt.setString(3, tanggalPeminjaman);
            stmt.setString(4, status);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            // Menampilkan kembali data setelah penambahan
            loadData();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengubah data peminjaman di database
    private void updatePeminjaman() {
        int selectedRow = peminjamanTable.getSelectedRow();
        if (selectedRow != -1) {
            String idPeminjaman = tableModel.getValueAt(selectedRow, 0).toString();
            String npm = ((ComboBoxItem) mahasiswaComboBox.getSelectedItem()).getValue();
            String tanggalPeminjaman = tanggalPeminjamanField.getText();
            String status = statusField.getText();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
                PreparedStatement stmt = conn.prepareStatement("UPDATE peminjaman SET NPM = ?, Tanggal_Pinjam = ?, Status = ? WHERE ID_Pinjam = ?");
                stmt.setString(1, npm);
                stmt.setString(2, tanggalPeminjaman);
                stmt.setString(3, status);
                stmt.setInt(4, Integer.parseInt(idPeminjaman));
                stmt.executeUpdate();

                stmt.close();
                conn.close();

                // Menampilkan kembali data setelah perubahan
                loadData();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman yang ingin diupdate.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus data peminjaman dari database
    private void deletePeminjaman() {
        int selectedRow = peminjamanTable.getSelectedRow();
        if (selectedRow != -1) {
            String idPeminjaman = tableModel.getValueAt(selectedRow, 0).toString();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM peminjaman WHERE ID_Pinjam = ?");
                stmt.setInt(1, Integer.parseInt(idPeminjaman));
                stmt.executeUpdate();

                stmt.close();
                conn.close();

                // Menampilkan kembali data setelah penghapusan
                loadData();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mengosongkan field input
    private void clearFields() {
        idPeminjamanField.setText("");
        mahasiswaComboBox.setSelectedIndex(0);
        tanggalPeminjamanField.setText("");
        statusField.setText("dipinjam");
    }

    // Mencari data peminjaman berdasarkan ID Peminjaman atau Nama Mahasiswa
    private void searchPeminjaman() {
        String keyword = searchField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM peminjaman WHERE ID_Pinjam LIKE ? OR NPM IN (SELECT NPM FROM mahasiswa WHERE Nama LIKE ?)");
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            // Menghapus data tabel sebelumnya
            tableModel.setRowCount(0);

            // Memuat data baru ke dalam tabel
            while (rs.next()) {
                String idPeminjaman = rs.getString("ID_Pinjam");
                String npm = rs.getString("NPM");
                String tanggalPeminjaman = rs.getString("Tanggal_Pinjam");
                String status = rs.getString("Status");

                // Mengambil nama mahasiswa berdasarkan NPM
                String nama = getMahasiswaNameByNPM(npm);

                tableModel.addRow(new Object[]{idPeminjaman, nama, tanggalPeminjaman, status});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil nama mahasiswa berdasarkan NPM
    private String getMahasiswaNameByNPM(String npm) {
        String nama = "";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT Nama FROM mahasiswa WHERE NPM = ?");
            stmt.setString(1, npm);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nama = rs.getString("Nama");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nama;
    }

    // Kelas untuk menyimpan data combo box item (Mahasiswa)
    private class ComboBoxItem {
        private String value;
        private String label;

        public ComboBoxItem(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}