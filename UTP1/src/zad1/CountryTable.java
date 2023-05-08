package zad1;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountryTable extends JTable {

    String path;

    public CountryTable(String path){
        this.path = path;
    }

    public JTable create(){
        int columnCount = 0;
        int rowCount = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            for (int i = 0; i < line.length(); i++){
                if (line.charAt(i) == '\t')
                    columnCount++;
            }
            columnCount++;
            while (br.ready()){
                br.readLine();
                rowCount++;
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        String[] columnName = new String[++columnCount];
        columnName[columnName.length - 1] = "Last modified";
        Object[][] data = new Object[rowCount][++columnCount];

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            StringBuilder word = new StringBuilder();
            int index = 0;
            for (int i = 0; i < line.length(); i++){
                if (line.charAt(i) == '\t'){
                    columnName[index++] = String.valueOf(word);
                    word = new StringBuilder();
                } else if (i == line.length() - 1){
                    word.append(line.charAt(i));
                    columnName[index] = String.valueOf(word);
                } else
                    word.append(line.charAt(i));
            }
            int rowIndex = -1;
            int columnIndex;
            word = new StringBuilder();
            while (br.ready()){
                rowIndex++;
                columnIndex = 0;
                line = br.readLine();
                for (int i = 0; i < line.length(); i++){
                    if (line.charAt(i) != '\t' && i != line.length() - 1){
                        word.append(line.charAt(i));
                    } else if (i != line.length() - 1){
                        if (columnIndex != 2){
                            data[rowIndex][columnIndex++] = word;
                            word = new StringBuilder();
                        } else {
                            int num = Integer.parseInt(String.valueOf(word)) * 1000;
                            word = new StringBuilder();
                            data[rowIndex][columnIndex++] = num;
                        }
                    } else {
                        word.append(line.charAt(i));
                        String flag = "data/CountryFlags/" + word + ".png";
                        BufferedImage bi = ImageIO.read(new File(flag));
                        Image tmp = bi.getScaledInstance(300, 150,Image.SCALE_SMOOTH);
                        BufferedImage fin = new BufferedImage(300, 150, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = fin.createGraphics();
                        g2d.drawImage(tmp, 0, 0, null);
                        g2d.dispose();
                        Icon c = new ImageIcon(fin);
                        word = new StringBuilder();
                        data[rowIndex][columnIndex++] = c;
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                data[rowIndex][columnIndex] = sdf.format(date);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        DefaultTableModel dtm = new DefaultTableModel(data, columnName) {
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public void fireTableCellUpdated(int row, int column) {
                if (column != 2)
                    return;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                setValueAt(sdf.format(date), row, 4);
            }
        };

        setModel(dtm);
        setRowHeight(150);
        getColumnModel().getColumn(3).setPreferredWidth(200);
        setDefaultRenderer(Integer.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(((Integer) value) > 20000000 ? Color.RED : Color.BLACK);
                return c;
            }
        });
        return this;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 2;
    }

}
