/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DataBukuPanel extends javax.swing.JPanel {

    private BukuDAO bukuDAO;
    private DefaultTableModel tableModel;
    private JTable bukuTable;
    private JTextField searchField;
    private JTextField idField;
    private JTextField judulField;
    private JTextField penerbitField;
    private JTextField tahunTerbitField;
    private int selectedRow = -1;

    public DataBukuPanel() {
        try {
            bukuDAO = new BukuDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        initComponents();
        loadDataBuku();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel Form Input
        JPanel formPanel = new JPanel(new BorderLayout());

        JPanel labelPanel = new JPanel(new GridLayout(4, 1));

        JPanel inputFieldPanel = new JPanel(new GridLayout(4, 2));
        idField = new JTextField(10);
        judulField = new JTextField(10);
        penerbitField = new JTextField(10);
        tahunTerbitField = new JTextField(10);

        inputFieldPanel.add(new JLabel("ID Buku:"));
        inputFieldPanel.add(idField);
        inputFieldPanel.add(new JLabel("Judul:"));
        inputFieldPanel.add(judulField);
        inputFieldPanel.add(new JLabel("Penerbit:"));
        inputFieldPanel.add(penerbitField);
        inputFieldPanel.add(new JLabel("Tahun Terbit:"));
        inputFieldPanel.add(tahunTerbitField);

        formPanel.add(labelPanel, BorderLayout.WEST);
        formPanel.add(inputFieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahBuku();
            }
        });
        JButton updateButton = new JButton("Perbarui");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perbaruiBuku();
            }
        });
        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBuku();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(formPanel);

        // Panel Pencarian
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cari");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                filterBukuTable(searchQuery);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel);

        // Tabel Buku
        tableModel = new DefaultTableModel(new String[]{"ID Buku", "Judul", "Penerbit", "Tahun Terbit"}, 0);
        bukuTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bukuTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 200));
        mainPanel.add(tableScrollPane);

        // Mengatur seleksi baris pada tabel
        bukuTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = bukuTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = bukuTable.convertRowIndexToModel(selectedRow);
                    isiFormDariDataBuku();
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadDataBuku() {
        try {
            List<Buku> bukuList = bukuDAO.semuaBuku();
            tableModel.setRowCount(0);
            for (Buku buku : bukuList) {
                Object[] data = {buku.getIdBuku(), buku.getJudul(), buku.getPenerbit(), buku.getTahunTerbit()};
                tableModel.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterBukuTable(String searchQuery) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        bukuTable.setRowSorter(sorter);
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + searchQuery);
        sorter.setRowFilter(filter);
    }

    private void tambahBuku() {
        String idBuku = idField.getText();
        String judul = judulField.getText();
        String penerbit = penerbitField.getText();
        int tahunTerbit = Integer.parseInt(tahunTerbitField.getText());

        try {
            Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
            bukuDAO.tambahBuku(buku);
            loadDataBuku();
            clearInputFields();
            JOptionPane.showMessageDialog(null, "Buku berhasil ditambahkan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void perbaruiBuku() {
        if (selectedRow >= 0) {
            String idBuku = idField.getText();
            String judul = judulField.getText();
            String penerbit = penerbitField.getText();
            int tahunTerbit = Integer.parseInt(tahunTerbitField.getText());

            try {
                Buku buku = new Buku(idBuku, judul, penerbit, tahunTerbit);
                bukuDAO.perbaruiBuku(buku);
                loadDataBuku();
                clearInputFields();
                JOptionPane.showMessageDialog(null, "Buku berhasil diperbarui", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih buku yang ingin diperbarui", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hapusBuku() {
        if (selectedRow >= 0) {
            String idBuku = idField.getText();

            try {
                bukuDAO.hapusBuku(idBuku);
                loadDataBuku();
                clearInputFields();
                JOptionPane.showMessageDialog(null, "Buku berhasil dihapus", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih buku yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void isiFormDariDataBuku() {
        if (selectedRow >= 0) {
            String idBuku = tableModel.getValueAt(selectedRow, 0).toString();
            String judul = tableModel.getValueAt(selectedRow, 1).toString();
            String penerbit = tableModel.getValueAt(selectedRow, 2).toString();
            String tahunTerbit = tableModel.getValueAt(selectedRow, 3).toString();

            idField.setText(idBuku);
            judulField.setText(judul);
            penerbitField.setText(penerbit);
            tahunTerbitField.setText(tahunTerbit);
        }
    }

    private void clearInputFields() {
        idField.setText("");
        judulField.setText("");
        penerbitField.setText("");
        tahunTerbitField.setText("");
    }
}
