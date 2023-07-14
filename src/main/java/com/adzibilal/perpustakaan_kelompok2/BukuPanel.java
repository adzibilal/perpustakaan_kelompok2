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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class BukuPanel extends JPanel {

    private JTextField idBukuField;
    private JTextField judulField;
    private JTextField penerbitField;
    private JTextField tahunTerbitField;
    private JTextField searchField;
    private JTable bukuTable;
    private DefaultTableModel tableModel;

    public BukuPanel() {
        setLayout(new BorderLayout());

        // Panel input data buku
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idBukuLabel = new JLabel("ID Buku:");
        idBukuField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(idBukuLabel, gbc);
        gbc.gridx++;
        inputPanel.add(idBukuField, gbc);

        JLabel judulLabel = new JLabel("Judul:");
        judulField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(judulLabel, gbc);
        gbc.gridx++;
        inputPanel.add(judulField, gbc);

        JLabel penerbitLabel = new JLabel("Penerbit:");
        penerbitField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(penerbitLabel, gbc);
        gbc.gridx++;
        inputPanel.add(penerbitField, gbc);

        JLabel tahunTerbitLabel = new JLabel("Tahun Terbit:");
        tahunTerbitField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(tahunTerbitLabel, gbc);
        gbc.gridx++;
        inputPanel.add(tahunTerbitField, gbc);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("Cari:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cari");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Tabel data buku
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Buku");
        tableModel.addColumn("Judul");
        tableModel.addColumn("Penerbit");
        tableModel.addColumn("Tahun Terbit");

        bukuTable = new JTable(tableModel);
        bukuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(bukuTable);

        // Menambahkan komponen ke panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Menampilkan data saat panel dibuat
        loadData();

        // Listener untuk tombol tambah
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBuku();
            }
        });

        // Listener untuk tombol update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBuku();
            }
        });

        // Listener untuk tombol delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBuku();
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
                searchBuku();
            }
        });

        // Listener untuk klik pada baris tabel
        bukuTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = bukuTable.getSelectedRow();
            if (selectedRow != -1) {
                String idBuku = tableModel.getValueAt(selectedRow, 0).toString();
                String judul = tableModel.getValueAt(selectedRow, 1).toString();
                String penerbit = tableModel.getValueAt(selectedRow, 2).toString();
                String tahunTerbit = tableModel.getValueAt(selectedRow, 3).toString();

                idBukuField.setText(idBuku);
                judulField.setText(judul);
                penerbitField.setText(penerbit);
                tahunTerbitField.setText(tahunTerbit);
            }
        });
    }

    // Method untuk memuat data buku dari database ke tabel
    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM buku");

            // Menghapus data lama dari tabel
            tableModel.setRowCount(0);

            // Memuat data baru dari hasil query
            while (rs.next()) {
                int idBuku = rs.getInt("ID_Buku");
                String judul = rs.getString("Judul");
                String penerbit = rs.getString("Penerbit");
                int tahunTerbit = rs.getInt("Tahun_Terbit");

                Vector<Object> row = new Vector<>();
                row.add(idBuku);
                row.add(judul);
                row.add(penerbit);
                row.add(tahunTerbit);

                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mencari data buku berdasarkan judul
    private void searchBuku() {
        String searchQuery = searchField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM buku WHERE Judul LIKE ?");
            stmt.setString(1, "%" + searchQuery + "%");
            ResultSet rs = stmt.executeQuery();

            // Menghapus data lama dari tabel
            tableModel.setRowCount(0);

            // Memuat data baru dari hasil query
            while (rs.next()) {
                int idBuku = rs.getInt("ID_Buku");
                String judul = rs.getString("Judul");
                String penerbit = rs.getString("Penerbit");
                int tahunTerbit = rs.getInt("Tahun_Terbit");

                Vector<Object> row = new Vector<>();
                row.add(idBuku);
                row.add(judul);
                row.add(penerbit);
                row.add(tahunTerbit);

                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menambahkan data buku ke database
    private void addBuku() {
        String idBuku = idBukuField.getText();
        String judul = judulField.getText();
        String penerbit = penerbitField.getText();
        String tahunTerbit = tahunTerbitField.getText();
        // Pengecekan jika ada field yang kosong
        if (idBuku.isEmpty() || judul.isEmpty() || penerbit.isEmpty() || tahunTerbit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua field.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO buku (ID_Buku, Judul, Penerbit, Tahun_Terbit) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, Integer.parseInt(idBuku));
            stmt.setString(2, judul);
            stmt.setString(3, penerbit);
            stmt.setInt(4, Integer.parseInt(tahunTerbit));
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

    // Method untuk mengupdate data buku di database
    private void updateBuku() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris tabel untuk mengedit data.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idBuku = idBukuField.getText();
        String judul = judulField.getText();
        String penerbit = penerbitField.getText();
        String tahunTerbit = tahunTerbitField.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
            PreparedStatement stmt = conn.prepareStatement("UPDATE buku SET Judul = ?, Penerbit = ?, Tahun_Terbit = ? WHERE ID_Buku = ?");
            stmt.setString(1, judul);
            stmt.setString(2, penerbit);
            stmt.setInt(3, Integer.parseInt(tahunTerbit));
            stmt.setInt(4, Integer.parseInt(idBuku));
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            // Menampilkan kembali data setelah update
            loadData();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghapus data buku dari database
    private void deleteBuku() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris tabel untuk menghapus data.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String idBuku = idBukuField.getText();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", "");
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM buku WHERE ID_Buku = ?");
                stmt.setInt(1, Integer.parseInt(idBuku));
                stmt.executeUpdate();

                stmt.close();
                conn.close();

                // Menampilkan kembali data setelah penghapusan
                loadData();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk membersihkan input field
    private void clearFields() {
        idBukuField.setText("");
        judulField.setText("");
        penerbitField.setText("");
        tahunTerbitField.setText("");
        bukuTable.clearSelection();
    }
}
