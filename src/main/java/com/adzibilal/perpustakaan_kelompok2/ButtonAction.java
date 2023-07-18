/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adzibilal.perpustakaan_kelompok2;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class ButtonAction extends AbstractAction {
    private final String actionName;
    private final JTable table;

    public ButtonAction(String actionName, JTable table) {
        this.actionName = actionName;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int rowIndex = table.convertRowIndexToModel(table.getEditingRow());

        if ("Edit".equals(actionName)) {
            editRow(rowIndex);
        } else if ("Delete".equals(actionName)) {
            deleteRow(rowIndex);
        }
    }
}

