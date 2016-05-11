/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi.apo;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author JC
 */
public class ImagePiece extends DefaultTableCellRenderer {
    private JLabel lbl = new JLabel();
    
    public ImagePiece()
    {
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column)
    {
        JLabel label = (JLabel)super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column
        );
        if (value instanceof Icon)
        {
            label.setText(null);
            label.setIcon((Icon)value);
            return label;
        }
        else {
            label.setText(null);
            label.setIcon(null);
            return label;         
        }
    }
}