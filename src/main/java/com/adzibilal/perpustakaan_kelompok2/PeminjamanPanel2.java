/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PeminjamanPanel2 extends JPanel {

    private JLabel idPeminjamanLabel;
    private JTextField idPeminjamanField;
    private JLabel mahasiswaLabel;
    private JComboBox<String> mahasiswaComboBox;
    private JLabel tanggalPeminjamanLabel;
    private JTextField tanggalPeminjamanField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton detailButton;
    private JButton clearButton;
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;

    private Connection connection;
    private Statement statement;

    public PeminjamanPanel2() {
        initializeDatabase();
        initializeComponents();
        loadMahasiswaComboBox();
        loadTableData();
    }

    private void initializeDatabase() {
        String url = "jdbc:mysql://localhost/db_perpustakaan";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        // Set layout manager for the panel
        setLayout(new BorderLayout());

        // Create panel components
        JPanel formPanel = createFormPanel();
        JScrollPane tableScrollPane = createTableScrollPane();

        // Add components to the panel
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Create form fields
        idPeminjamanLabel = new JLabel("ID Peminjaman:");
        idPeminjamanField = new JTextField(10);
        mahasiswaLabel = new JLabel("Mahasiswa:");
        mahasiswaComboBox = new JComboBox<>();
        tanggalPeminjamanLabel = new JLabel("Tanggal Peminjaman:");
        tanggalPeminjamanField = new JTextField(10);
        addButton = new JButton("Tambah");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        detailButton = new JButton("Detail");
        clearButton = new JButton("Clear");

        // Add form labels and fields to the form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idPeminjamanLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(idPeminjamanField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(mahasiswaLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(mahasiswaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(tanggalPeminjamanLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(tanggalPeminjamanField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(addButton, gbc);

        gbc.gridx = 1;
        formPanel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        formPanel.add(detailButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(clearButton, gbc);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeminjaman();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePeminjaman();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePeminjaman();
            }
        });

        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDetailPopup();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        return formPanel;
    }

    private JScrollPane createTableScrollPane() {
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel();

        // Create table
        table = new JTable(tableModel);

        // Create scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);

        return tableScrollPane;
    }

    private void loadMahasiswaComboBox() {
        try {
            // Retrieve data from the database
            String query = "SELECT NPM, Nama FROM mahasiswa";
            ResultSet resultSet = statement.executeQuery(query);

            // Clear existing items
            mahasiswaComboBox.removeAllItems();

            // Add items to the combo box
            while (resultSet.next()) {
                String npm = resultSet.getString("NPM");
                String nama = resultSet.getString("Nama");
                mahasiswaComboBox.addItem(npm + " - " + nama);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            // Retrieve data from the database
            String query = "SELECT p.*, m.Nama, m.Jurusan, m.Alamat FROM peminjaman p JOIN mahasiswa m ON p.NPM = m.NPM;";
            ResultSet resultSet = statement.executeQuery(query);

            // Create table model
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID Peminjaman");
            tableModel.addColumn("Nama");
            tableModel.addColumn("NPM");
            tableModel.addColumn("Jurusan");
            tableModel.addColumn("Alamat");
            tableModel.addColumn("Tanggal Peminjaman");
            tableModel.addColumn("Status");
            tableModel.addColumn("Tanggal Kembali");
            tableModel.addColumn("Denda");

            // Populate the table model with the retrieved data
            while (resultSet.next()) {
                int idPeminjaman = resultSet.getInt("ID_Pinjam");
                String nama = resultSet.getString("Nama");
                String npm = resultSet.getString("NPM");
                String jurusan = resultSet.getString("Jurusan");
                String alamat = resultSet.getString("Alamat");
                Date tanggalPeminjaman = resultSet.getDate("Tanggal_Pinjam");
                String status = resultSet.getString("Status");
                Date tanggalKembali = resultSet.getDate("Tanggal_Kembali");
                double denda = resultSet.getDouble("Denda");

                Object[] rowData = {idPeminjaman, nama, npm, jurusan, alamat, tanggalPeminjaman, status, tanggalKembali, denda};
                tableModel.addRow(rowData);
            }

            // Set the table model to the table
            table.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPeminjaman() {
        String idPeminjaman = idPeminjamanField.getText();
        String npm = ((String) mahasiswaComboBox.getSelectedItem()).split(" - ")[0];
        String tanggalPeminjaman = tanggalPeminjamanField.getText();
        String status = "Dipinjam";

        try {
            // Insert data into the database
            String query = "INSERT INTO peminjaman (ID_Pinjam, NPM, Tanggal_Pinjam, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(idPeminjaman));
            preparedStatement.setString(2, npm);
            preparedStatement.setString(3, tanggalPeminjaman);
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();

            // Clear the form and reload table data
            clearForm();
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePeminjaman() {
        String idPeminjaman = idPeminjamanField.getText();
        String npm = ((String) mahasiswaComboBox.getSelectedItem()).split(" - ")[0];
        String tanggalPeminjaman = tanggalPeminjamanField.getText();
        String status = "Dipinjam";

        try {
            // Update data in the database
            String query = "UPDATE peminjaman SET NPM = ?, Tanggal_Pinjam = ?, Status = ? WHERE ID_Pinjam = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, npm);
            preparedStatement.setString(2, tanggalPeminjaman);
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4, Integer.parseInt(idPeminjaman));
            preparedStatement.executeUpdate();

            // Clear the form and reload table data
            clearForm();
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePeminjaman() {
        String idPeminjaman = idPeminjamanField.getText();

        try {
            // Delete data from the database
            String query = "DELETE FROM peminjaman WHERE ID_Pinjam = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(idPeminjaman));
            preparedStatement.executeUpdate();

            // Clear the form and reload table data
            clearForm();
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showDetailPopup() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            int idPeminjaman = (int) table.getValueAt(selectedRow, 0);
            String npm = (String) table.getValueAt(selectedRow, 2);

            // Create and show the detail popup window
            JFrame detailFrame = new JFrame("Detail Peminjaman");
            detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            detailFrame.setSize(400, 300);

            JPanel detailPanel = new JPanel(new BorderLayout());
            JTextArea detailTextArea = new JTextArea();
            JScrollPane detailScrollPane = new JScrollPane(detailTextArea);

            try {
                // Retrieve detail data from the database
                String query = "SELECT b.Judul, b.Penerbit, b.Tahun_Terbit, dp.Jumlah "
                        + "FROM detail_peminjaman dp "
                        + "JOIN buku b ON dp.ID_Buku = b.ID_Buku "
                        + "WHERE dp.ID_Peminjaman = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idPeminjaman);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Populate the detail text area
                StringBuilder detailText = new StringBuilder();
                while (resultSet.next()) {
                    String judul = resultSet.getString("Judul");
                    String penerbit = resultSet.getString("Penerbit");
                    int tahunTerbit = resultSet.getInt("Tahun_Terbit");
                    int jumlah = resultSet.getInt("Jumlah");

                    detailText.append("Judul: ").append(judul).append("\n");
                    detailText.append("Penerbit: ").append(penerbit).append("\n");
                    detailText.append("Tahun Terbit: ").append(tahunTerbit).append("\n");
                    detailText.append("Jumlah: ").append(jumlah).append("\n\n");
                }

                detailTextArea.setText(detailText.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            detailPanel.add(detailScrollPane, BorderLayout.CENTER);
            detailFrame.add(detailPanel);
            detailFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idPeminjamanField.setText("");
        mahasiswaComboBox.setSelectedIndex(0);
        tanggalPeminjamanField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Peminjaman Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                PeminjamanPanel2 panel = new PeminjamanPanel2();
                frame.add(panel);
                frame.setVisible(true);
            }
        });
    }
}
