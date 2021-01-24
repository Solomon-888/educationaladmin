package tool;


import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PublicTool {
    //转换密码为字符串
    public static String transitionPassword(char[] password) {
        String res = "";
        for (char ch : password) {
            res += ch;
        }
        return res;
    }

    //获取星期字符串
    public static String getWeek(char week) {
        switch (week) {
            case '1':
                return "周一";
            case '2':
                return "周二";
            case '3':
                return "周三";
            case '4':
                return "周四";
            case '5':
                return "周五";
        }
        return null;
    }

    //获取课程节数
    public static String getPitch(char pitch) {
        switch (pitch) {
            case '1':
                return "[1-2]节";
            case '2':
                return "[3-4]节";
            case '3':
                return "[5-6]节";
            case '4':
                return "[7-8]节";
            case '5':
                return "[9-10]节";
        }
        return null;
    }

    //设置列宽
    public static void setColumnSize(JTable table, int i, int preferedWidth, int maxWidth, int minWidth) {
        //表格的列模型
        TableColumnModel cm = table.getColumnModel();
        //得到第i个列对象
        TableColumn column = cm.getColumn(i);
        column.setPreferredWidth(preferedWidth);
        column.setMaxWidth(maxWidth);
        column.setMinWidth(minWidth);
    }

    //导出数据
    public static void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "gbk"));
        for (int i = 0; i < model.getColumnCount(); i++) {
            bWriter.write(model.getColumnName(i));
            bWriter.write("\t");
        }
        bWriter.newLine();
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                if (model.getValueAt(i, j) != null)
                    bWriter.write(model.getValueAt(i, j).toString());
                else
                    bWriter.write("");
                bWriter.write("\t");
            }
            bWriter.newLine();
        }
        bWriter.close();
    }
    public static String getDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
