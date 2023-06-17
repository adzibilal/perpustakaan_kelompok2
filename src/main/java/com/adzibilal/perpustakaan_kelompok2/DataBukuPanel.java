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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Lenovo
 */
public class DataBukuPanel extends JPanel {

    private BukuDAO bukuDAO;

    private JTextField txtIdBuku;
    private JTextField txtJudul;
    private JTextField txtPenerbit;
    private JTextField txtTahunTerbit;
    private JTextField txtSearchQuery;

    private JButton btnTambah;
    private JButton btnHapus;
    private JButton btnPerbarui;
    private JButton btnReset;
    private JButton btnCari;

    private JTable tabelBuku;
    private DefaultTableModel modelTabelBuku;

    public DataBukuPanel() throws SQLException {
        bukuDAO = new BukuDAO();

        setLayout(new BorderLayout());
        // Panel utama dengan BorderLayout
        JPanel panelUtama = new JPanel(new BorderLayout());

        // Panel form
        JPanel panelForm = new JPanel(new GridLayout(5, 2));
        JLabel lblIdBuku = new JLabel("ID Buku:");
        txtIdBuku = new JTextField();
        JLabel lblJudul = new JLabel("Judul:");
        txtJudul = new JTextField();
        JLabel lblPenerbit = new JLabel("Penerbit:");
        txtPenerbit = new JTextField();
        JLabel lblTahunTerbit = new JLabel("Tahun Terbit:");
        txtTahunTerbit = new JTextField();
        JLabel lblSearchQuery = new JLabel("Cari Buku:");
        txtSearchQuery = new JTextField();
        panelForm.add(lblIdBuku);
        panelForm.add(txtIdBuku);
        panelForm.add(lblJudul);
        panelForm.add(txtJudul);
        panelForm.add(lblPenerbit);
        panelForm.add(txtPenerbit);
        panelForm.add(lblTahunTerbit);
        panelForm.add(txtTahunTerbit);
        panelForm.add(lblSearchQuery);
        panelForm.add(txtSearchQuery);
        // ...
        panelUtama.add(panelForm, BorderLayout.NORTH);

        // Panel button
        JPanel panelButton = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus");
        btnPerbarui = new JButton("Perbarui");
        btnReset = new JButton("Reset");
        btnCari = new JButton("Cari");
        panelButton.add(btnTambah);
        panelButton.add(btnHapus);
        panelButton.add(btnPerbarui);
        panelButton.add(btnReset);
        panelButton.add(btnCari);
        // ...
        panelUtama.add(panelButton, BorderLayout.CENTER);

        // Tabel buku
        String[] header = {"ID Buku", "Judul", "Penerbit", "Tahun Terbit"};
        modelTabelBuku = new DefaultTableModel(header, 0);
        tabelBuku = new JTable(modelTabelBuku);
        // Menentukan tinggi tabel
        int tableHeight = 370; // Ubah sesuai dengan kebutuhan Anda

        // Mengatur tinggi tabel
        Dimension tableDimension = new Dimension(tabelBuku.getPreferredSize().width, tableHeight);
        tabelBuku.setPreferredScrollableViewportSize(tableDimension);

        JScrollPane scrollPane = new JScrollPane(tabelBuku);
        tabelBuku.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tabelBuku.getSelectedRow();
                if (selectedRow != -1) {
                    String idBuku = (String) tabelBuku.getValueAt(selectedRow, 0);
                    String judul = (String) tabelBuku.getValueAt(selectedRow, 1);
                    String penerbit = (String) tabelBuku.getValueAt(selectedRow, 2);
                    int tahunTerbit = (int) tabelBuku.getValueAt(selectedRow, 3);

                    txtIdBuku.setText(idBuku);
                    txtJudul.setText(judul);
                    txtPenerbit.setText(penerbit);
                    txtTahunTerbit.setText(String.valueOf(tahunTerbit));
                }
            }
        });

        // ...
        panelUtama.add(scrollPane, BorderLayout.SOUTH);

// Menambahkan panelUtama ke dalam DataBukuPanel
        add(panelUtama);

        // Mengatur aksi tombol
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahBuku();
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBuku();
            }
        });

        btnPerbarui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perbaruiBuku();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetForm();
                } catch (SQLException ex) {
                    Logger.getLogger(DataBukuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariBuku();
            }
        });

        // Memuat data buku ke dalam tabel
        muatDataBuku();
    }

    private void tambahBuku() {
        try {
            String idBuku = txtIdBuku.getText();
            String judul = txtJudul.getText();
            String penerbit = txtPenerbit.getText();
            int tahunTerbit = Integer.parseInt(txtTahunTerbit.getText());

            Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
            bukuDAO.tambahBuku(buku);

            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            resetForm();
            muatDataBuku();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun Terbit harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusBuku() {
        int selectedRow = tabelBuku.getSelectedRow();
        if (selectedRow != -1) {
            String idBuku = (String) tabelBuku.getValueAt(selectedRow, 0);
            try {
                bukuDAO.hapusBuku(idBuku);

                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                resetForm();
                muatDataBuku();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void perbaruiBuku() {
        int selectedRow = tabelBuku.getSelectedRow();
        if (selectedRow != -1) {
            String idBuku = txtIdBuku.getText();
            String judul = txtJudul.getText();
            String penerbit = txtPenerbit.getText();
            int tahunTerbit = Integer.parseInt(txtTahunTerbit.getText());

            Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
            try {
                bukuDAO.perbaruiBuku(buku);

                JOptionPane.showMessageDialog(this, "Buku berhasil diperbarui", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                resetForm();
                muatDataBuku();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tahun Terbit harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan diperbarui", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void resetForm() throws SQLException {
        txtIdBuku.setText("");
        txtJudul.setText("");
        txtPenerbit.setText("");
        txtTahunTerbit.setText("");
        txtSearchQuery.setText(""); // Menambahkan pernyataan ini untuk mereset pencarian
        tabelBuku.clearSelection();
        muatDataBuku();
    }

    private void muatDataBuku() throws SQLException {
        modelTabelBuku.setRowCount(0);

        List<Buku> bukuList = bukuDAO.semuaBuku();
        for (Buku buku : bukuList) {
            Object[] row = {buku.getIdBuku(), buku.getJudul(), buku.getPenerbit(), buku.getTahunTerbit()};
            modelTabelBuku.addRow(row);
        }

        tabelBuku.getSelectionModel().clearSelection();
    }

    private void cariBuku() {
        String searchQuery = txtSearchQuery.getText();
        try {
            List<Buku> bukuList = bukuDAO.cariBuku(searchQuery);
            modelTabelBuku.setRowCount(0);
            for (Buku buku : bukuList) {
                Object[] row = {buku.getIdBuku(), buku.getJudul(), buku.getPenerbit(), buku.getTahunTerbit()};
                modelTabelBuku.addRow(row);
            }
            tabelBuku.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
