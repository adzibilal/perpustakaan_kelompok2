/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Lenovo
 */
public class DetailPeminjamanPanel extends javax.swing.JPanel {

    /**
     * Creates new form DetailPeminjamanPanel
     */
    private int idPeminjaman; // Declare a variable to store the idPeminjaman

    // Add a constructor that accepts the idPeminjaman parameter
    public DetailPeminjamanPanel(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
        initComponents();
        initializeTable();

        getDataDetailPeminjaman();
        getDataBuku();
        getDataDetailPeminjamanTabel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        namaTextField = new javax.swing.JTextField();
        npmTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tanggalPinjamTextField = new javax.swing.JTextField();
        tanggalKembaliTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        bukuComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        detailTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(785, 474));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Detail Peminjaman");

        jLabel2.setText("Nama Peminjam");

        jLabel3.setText("NPM");

        namaTextField.setEditable(false);

        npmTextField.setEditable(false);

        jLabel4.setText("Tanggal Pinjam");

        jLabel5.setText("Tanggal Kembali");

        tanggalPinjamTextField.setEditable(false);

        tanggalKembaliTextField.setEditable(false);

        jLabel6.setText("Data Buku");

        bukuComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Jumlah");

        jButton1.setText("Tambah Buku");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        detailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama Buku", "Jumlah"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(detailTable);

        jButton2.setText("Kembalikan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(bukuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(bukuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(npmTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(29, 29, 29)
                                .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(33, 33, 33)
                                .addComponent(tanggalPinjamTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tanggalKembaliTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(79, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(tanggalPinjamTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(npmTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tanggalKembaliTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addDetailPeminjamanToDatabase();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        kembalikanBuku();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void initializeTable() {
        detailTable.setRowSelectionAllowed(true);
        detailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showPopupMenu(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                showPopupMenu(evt);
            }
        });
    }

    private void showPopupMenu(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger() && detailTable.getSelectedRowCount() == 1) {
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem editItem = new JMenuItem("Edit");
            editItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int rowIndex = detailTable.getSelectedRow();
                    editRow(rowIndex);
                }
            });
            popupMenu.add(editItem);

            JMenuItem deleteItem = new JMenuItem("Delete");
            deleteItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int rowIndex = detailTable.getSelectedRow();
                    deleteRow(rowIndex);
                }
            });
            popupMenu.add(deleteItem);

            popupMenu.show(detailTable, evt.getX(), evt.getY());
        }
    }

    // Fungsi untuk mendapatkan data detail peminjaman dan mengisi TextField yang bersangkutan
    private void getDataDetailPeminjaman() {
        String query = "SELECT m.Nama, m.NPM, p.Tanggal_Pinjam, p.Tanggal_Kembali "
                + "FROM peminjaman p "
                + "JOIN mahasiswa m ON p.NPM = m.NPM "
                + "WHERE p.ID_Pinjam = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idPeminjaman);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String namaPeminjam = resultSet.getString("Nama");
                    String npm = resultSet.getString("NPM");
                    String tanggalPinjam = resultSet.getString("Tanggal_Pinjam");
                    String tanggalKembali = resultSet.getString("Tanggal_Kembali");

                    namaTextField.setText(namaPeminjam);
                    npmTextField.setText(npm);
                    tanggalPinjamTextField.setText(tanggalPinjam);
                    tanggalKembaliTextField.setText(tanggalKembali);
                    namaTextField.disable();
                    npmTextField.disable();
                    tanggalPinjamTextField.disable();
                    tanggalKembaliTextField.disable();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fungsi untuk mendapatkan data buku dari tabel buku dan mengisi ComboBox
    private void getDataBuku() {
        String query = "SELECT ID_Buku, Judul FROM buku";
        // Clear existing items
        bukuComboBox.removeAllItems();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idBuku = resultSet.getInt("ID_Buku");
                    String judulBuku = resultSet.getString("Judul");

                    bukuComboBox.addItem(idBuku + ": " + judulBuku);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fungsi untuk mengisi tabel detailTable dengan data detail peminjaman
    private void getDataDetailPeminjamanTabel() {
        String query = "SELECT b.Judul, dp.Jumlah "
                + "FROM detail_peminjaman dp "
                + "JOIN buku b ON dp.ID_Buku = b.ID_Buku "
                + "WHERE dp.ID_Peminjaman = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idPeminjaman);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) detailTable.getModel();
                model.setRowCount(0); // Menghapus data lama dari tabel

                while (resultSet.next()) {
                    String judulBuku = resultSet.getString("Judul");
                    int jumlah = resultSet.getInt("Jumlah");

                    // Add the row data, including the buttons, to the table
                    Object[] row = {judulBuku, jumlah};
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fungsi untuk menambah data ke tabel detail_peminjaman di database
    private void addDetailPeminjamanToDatabase() {
        String selectedBook = (String) bukuComboBox.getSelectedItem();
        int idBuku = Integer.parseInt(selectedBook.split(": ")[0]);
        String jumlahBukuStr = jTextField5.getText();

        if (jumlahBukuStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah buku harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int jumlahBuku;

        try {
            jumlahBuku = Integer.parseInt(jumlahBukuStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah buku harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the last used ID_Detail or use 1 if the table is empty
        int nextIDDetail = getLastUsedIDDetail() + 1;

        String query = "INSERT INTO detail_peminjaman (ID_Detail, ID_Peminjaman, ID_Buku, Jumlah) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, nextIDDetail);
            preparedStatement.setInt(2, idPeminjaman);
            preparedStatement.setInt(3, idBuku);
            preparedStatement.setInt(4, jumlahBuku);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan ", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                getDataDetailPeminjamanTabel(); // Refresh tabel detailTable dengan data terbaru
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal ditambahkan ", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getLastUsedIDDetail() {
        String query = "SELECT MAX(ID_Detail) FROM detail_peminjaman";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    private void editRow(int rowIndex) {
        int modelRow = detailTable.convertRowIndexToModel(rowIndex);
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) detailTable.getModel();

        String judulBuku = (String) model.getValueAt(modelRow, 0);
        String jumlahBukuStr = JOptionPane.showInputDialog(this, "Edit Jumlah Buku:", model.getValueAt(modelRow, 1));
        if (jumlahBukuStr == null) // User clicked Cancel
        {
            return;
        }

        int jumlahBuku;
        try {
            jumlahBuku = Integer.parseInt(jumlahBukuStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah buku harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idBuku = getIdBukuByJudul(judulBuku);
        if (idBuku == -1) {
            JOptionPane.showMessageDialog(this, "Data buku tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updateDetailPeminjamanToDatabase(idBuku, jumlahBuku);
    }

    private void deleteRow(int rowIndex) {
        int modelRow = detailTable.convertRowIndexToModel(rowIndex);
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) detailTable.getModel();

        String judulBuku = (String) model.getValueAt(modelRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data buku \"" + judulBuku + "\"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int idBuku = getIdBukuByJudul(judulBuku);
        if (idBuku == -1) {
            JOptionPane.showMessageDialog(this, "Data buku tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        deleteDetailPeminjamanFromDatabase(idBuku);
    }

    private int getIdBukuByJudul(String judul) {
        String query = "SELECT ID_Buku FROM buku WHERE Judul = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, judul);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ID_Buku");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private void updateDetailPeminjamanToDatabase(int idBuku, int jumlahBuku) {
        String query = "UPDATE detail_peminjaman SET Jumlah = ? WHERE ID_Peminjaman = ? AND ID_Buku = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, jumlahBuku);
            preparedStatement.setInt(2, idPeminjaman);
            preparedStatement.setInt(3, idBuku);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                getDataDetailPeminjamanTabel(); // Refresh tabel detailTable dengan data terbaru
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteDetailPeminjamanFromDatabase(int idBuku) {
        String query = "DELETE FROM detail_peminjaman WHERE ID_Peminjaman = ? AND ID_Buku = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idPeminjaman);
            preparedStatement.setInt(2, idBuku);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                getDataDetailPeminjamanTabel(); // Refresh tabel detailTable dengan data terbaru
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fungsi untuk mengubah status peminjaman menjadi 'dikembalikan' setelah konfirmasi
    private void kembalikanBuku() {
        int confirmation = JOptionPane.showConfirmDialog(this, "Anda yakin ingin mengembalikan semua buku?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            String updateQuery = "UPDATE peminjaman SET Status = 'dikembalikan', Tanggal_Kembali = ? WHERE ID_Pinjam = ?";
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/db_perpustakaan", "root", ""); PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(currentDate);

                preparedStatement.setString(1, formattedDate); // Set the current date to the parameter
                preparedStatement.setInt(2, idPeminjaman);
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data peminjaman berhasil diubah menjadi 'dikembalikan'.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    // Perform any other actions after the status is updated successfully, if needed.
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengubah status peminjaman.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                getDataDetailPeminjaman();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> bukuComboBox;
    private javax.swing.JTable detailTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField namaTextField;
    private javax.swing.JTextField npmTextField;
    private javax.swing.JTextField tanggalKembaliTextField;
    private javax.swing.JTextField tanggalPinjamTextField;
    // End of variables declaration//GEN-END:variables

}
