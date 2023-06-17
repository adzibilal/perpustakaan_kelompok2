package com.adzibilal.perpustakaan_kelompok2;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setText("Dashboard");

        add(jLabel1);
    }
}
