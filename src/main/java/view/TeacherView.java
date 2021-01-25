package view;

import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;
import service.StudentService;
import service.TeacherService;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import tool.BarTool;
import tool.PublicTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherView extends JFrame {
    Teacher teacher;

    //刷新面板
    public void replacementPanel(Container change, Container res) {
        change.removeAll();
        change.add(res);
        change.validate();
        change.repaint();
    }

    public TeacherView(Teacher teacher) {
        super("教务管理系统");
        this.teacher = teacher;
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        JButton jbtHome = new BarTool().getButton("首页", "img/home.png");
        JButton jbtArrangement = new BarTool().getButton("教学安排", "img/course.png");
        JButton jbtGradeinput = new BarTool().getButton("成绩录入", "img/Selectcourse.png");
        JButton jbtUser = new BarTool().getButton("用户管理", "img/user.png");
        JPanel bar = new JPanel();
        bar.add(jbtHome);
        bar.add(jbtArrangement);
        bar.add(jbtGradeinput);
        bar.add(jbtUser);
        JPanel mid = getHome();
        cp.add(bar, BorderLayout.NORTH);
        cp.add(mid, BorderLayout.CENTER);

        this.setSize(800, 650);
        //居中
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jbtHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getHome());
            }
        });

        jbtArrangement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getArrangement());
            }
        });

        jbtGradeinput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getGradeinput());
            }
        });
        jbtUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getUser());
            }
        });
    }

    /*首页面板*/
    public JPanel getHome() {
        JPanel Home = new JPanel();
        JTextArea textArea = new JTextArea();
        String text = teacher.getT_name();
        text += "老师：欢迎使用教务管理系统！";
        textArea.setText(text);
        textArea.setFont(new Font("黑体", 1, 30));
        Home.add(textArea);
        return Home;
    }

    /*教学安排面板*/
    public JPanel getArrangement() {
        JPanel arrangement = new JPanel(new BorderLayout());

        class TableViewRenderer extends JTextArea implements TableCellRenderer {
            public TableViewRenderer() {
                //将表格设为自动换行
                setLineWrap(true);
                setWrapStyleWord(true);
            }

            public Component getTableCellRendererComponent(JTable jtable, Object obj, //obj指的是单元格内容
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                setText(obj == null ? "" : obj.toString()); //利用JTextArea的setText设置文本方法
                return this;
            }
        }

        JPanel top = new JPanel();
        JLabel jlYear = new JLabel("学年:");
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("--请选择--");    //向下拉列表中添加一项
        java.util.List<String> year = new StudentService().getYear();
        for (String str : year) {
            comboBox.addItem(str);
        }
        JButton jbtSelect = new JButton("查询");
        top.add(jlYear);
        top.add(comboBox);
        top.add(jbtSelect);
        arrangement.add(top, BorderLayout.NORTH);

        /*JTable table = new JTable();*/

        //不可编辑表格内容
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane tab = new JScrollPane(table);

        //表格内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();//单元格渲染器
        TableViewRenderer tableViewRenderer = new TableViewRenderer();
        tableViewRenderer.setPreferredSize(new Dimension(50, 75));
        tableViewRenderer.setBackground(new Color(211, 211, 211));
        /*tcr.setHorizontalAlignment(JLabel.CENTER);
        tcr.setPreferredSize(new Dimension(50, 75));
        tcr.setBackground(new Color(211, 211, 211));*/
        table.setDefaultRenderer(Object.class, tableViewRenderer);
        //表格表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        hr.setPreferredSize(new Dimension(50, 78));
        table.getTableHeader().setDefaultRenderer(hr);
        //设置面板大小
        tab.setPreferredSize(new Dimension(750, 472));
        //设置透明
        tab.setOpaque(false);
        tab.getViewport().setOpaque(false);

        arrangement.add(tab, BorderLayout.CENTER);
        jbtSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object cp_year = comboBox.getSelectedItem();
                List<TeachingPlan> teachingplan = new TeacherService().getTeachingplan(teacher.getT_id(), (String) cp_year);
                Object[][] tableDate = new Object[5][6];
                tableDate[0][0] = "\n\n          1-2节";
                tableDate[1][0] = "\n\n          3-4节";
                tableDate[2][0] = "\n\n          5-6节";
                tableDate[3][0] = "\n\n          7-8节";
                tableDate[4][0] = "\n\n          9-10节";
                for (int i = 0; i < 5; i++) {
                    for (int j = 1; j < 6; j++) {
                        tableDate[i][j] = "";
                    }
                }
                String[] name = {" ", "周一", "周二", "周三", "周四", "周五"};

                for (int i = 0; i < teachingplan.size(); i++) {
                    String res;
                    String tc_time = teachingplan.get(i).getTc_time();
                    //获得最终上课时间与地点
                    String[] res_time = tc_time.split(" ");
                    for (int j = 0; j < res_time.length; j++) {
                        int week = res_time[j].charAt(0) - '0';
                        int section = res_time[j].charAt(2) - '0';
                        res = "\n  " + teachingplan.get(i).getTc_name() + "\n  " + teachingplan.get(i).getTc_point();
                        tableDate[section - 1][week] = res;
                    }
                }
                table.removeAll();
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //获得表格模型
                tableModel.setRowCount(0);    //清空表格中的数据
                tableModel.setColumnIdentifiers(name);    //设置表头
                for (int i = 0; i < 5; i++) {
                    tableModel.addRow(tableDate[i]);
                }
                table.setRowHeight(78);
                table.setModel(tableModel);    //应用表格模型

            }
        });
        return arrangement;
    }

    /*成绩录入面板*/
    public JPanel getGradeinput() {
        JPanel gradeInput = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JLabel jlYear = new JLabel("学年:");
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("--请选择--");    //向下拉列表中添加一项
        java.util.List<String> year = new StudentService().getYear();
        for (String str : year) {
            comboBox.addItem(str);
        }

        JLabel jlClass = new JLabel("教学班级:");
        JComboBox<String> cmbClass = new JComboBox<>();
        cmbClass.addItem("--请选择--");

        JButton jbtSelect = new JButton("查询");
        top.add(jlYear);
        top.add(comboBox);
        top.add(jlClass);
        top.add(cmbClass);
        top.add(jbtSelect);

        gradeInput.add(top, BorderLayout.NORTH);

        JTable table = new JTable();

        JScrollPane tab = new JScrollPane(table);

        //表格内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();//单元格渲染器
        tcr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, tcr);
        //表格表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        hr.setPreferredSize(new Dimension(25, 25));
        table.getTableHeader().setDefaultRenderer(hr);
        //设置面板大小
        tab.setPreferredSize(new Dimension(600, 500));
        //设置透明
        tab.setOpaque(false);
        tab.getViewport().setOpaque(false);
        gradeInput.add(tab, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton jbtSubmit = new JButton("提交");
        JLabel jlOrd = new JLabel("平时成绩系数:");
        JLabel jlF = new JLabel("期末成绩系数");
        JTextField jtfOrd = new JTextField(4);
        JTextField jtfF = new JTextField(4);
        JButton jbtExport = new JButton("导出数据");
        bottom.add(jlOrd);
        bottom.add(jtfOrd);
        bottom.add(jlF);
        bottom.add(jtfF);
        bottom.add(jbtSubmit);
        bottom.add(jbtExport);
        gradeInput.add(bottom, BorderLayout.SOUTH);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object cp_year = comboBox.getSelectedItem();
                List<TeachingPlan> teachingplan = new TeacherService().getTeachingplan(teacher.getT_id(), (String) cp_year);
                cmbClass.removeAllItems();
                if (teachingplan.size() == 0)
                    cmbClass.addItem("无对应教学班级");
                else {
                    for (int i = 0; i < teachingplan.size(); i++) {
                        cmbClass.addItem(teachingplan.get(i).getTc_name());
                    }
                }
            }
        });

        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object className = cmbClass.getSelectedItem();
                List<Grade> gradebyClass = new TeacherService().getGradebyClass((String) className);
                Object[][] tableDate = new Object[gradebyClass.size()][5];
                for (int i = 0; i < gradebyClass.size(); i++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == 0)
                            tableDate[i][j] = gradebyClass.get(i).getS_id();
                        else if (j == 1)
                            tableDate[i][j] = gradebyClass.get(i).getS_name();
                        else if (j == 2)
                            tableDate[i][j] = gradebyClass.get(i).getG_ordinary();
                        else if (j == 3)
                            tableDate[i][j] = gradebyClass.get(i).getG_final();
                        else
                            tableDate[i][j] = gradebyClass.get(i).getG_sum();
                    }
                }
                String[] name = {"学号", "姓名", "平时性成绩", "终结性成绩", "总成绩"};
                table.removeAll();

                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //获得表格模型

                tableModel.setRowCount(0);    //清空表格中的数据

                tableModel.setColumnIdentifiers(name);    //设置表头
                for (int i = 0; i < gradebyClass.size(); i++) {
                    tableModel.addRow(tableDate[i]);
                }
                table.setRowHeight(30);
                table.setModel(tableModel);    //应用表格模型
            }
        });
        JFrame jf = this;

        jbtSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Grade> grades = new ArrayList<>();
                Object className = cmbClass.getSelectedItem();
                for (int i = 0; i < table.getRowCount(); i++) {
                    Grade grade = new Grade();
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        if (j == 0)
                            grade.setS_id((String) table.getValueAt(i, j));
                        else if (j == 2)
                            grade.setG_ordinary(Double.valueOf(table.getValueAt(i, j).toString()));
                        else if (j == 3)
                            grade.setG_final(Double.valueOf(table.getValueAt(i, j).toString()));
                        else if (j == 4) {
                            Double res;
                            res = Double.valueOf(jtfOrd.getText()) * Double.valueOf(table.getValueAt(i, 2).toString());
                            res += Double.valueOf(jtfF.getText()) * Double.valueOf(table.getValueAt(i, 3).toString());
                            grade.setG_sum(res);
                        } else
                            continue;
                    }
                    grades.add(grade);
                }
                if (new TeacherService().updateGrade(grades, (String) className)) {
                    JOptionPane.showMessageDialog(jf, "成绩录入成功", "提示", JOptionPane.PLAIN_MESSAGE);
                    //重新进行表格渲染
                    List<Grade> gradebyClass = new TeacherService().getGradebyClass((String) className);
                    Object[][] tableDate = new Object[gradebyClass.size()][5];
                    for (int i = 0; i < gradebyClass.size(); i++) {
                        for (int j = 0; j < 5; j++) {
                            if (j == 0)
                                tableDate[i][j] = gradebyClass.get(i).getS_id();
                            else if (j == 1)
                                tableDate[i][j] = gradebyClass.get(i).getS_name();
                            else if (j == 2)
                                tableDate[i][j] = gradebyClass.get(i).getG_ordinary();
                            else if (j == 3)
                                tableDate[i][j] = gradebyClass.get(i).getG_final();
                            else
                                tableDate[i][j] = gradebyClass.get(i).getG_sum();
                        }
                    }
                    String[] name = {"学号", "姓名", "平时性成绩", "终结性成绩", "总成绩"};
                    table.removeAll();

                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //获得表格模型

                    tableModel.setRowCount(0);    //清空表格中的数据

                    tableModel.setColumnIdentifiers(name);    //设置表头
                    for (int i = 0; i < gradebyClass.size(); i++) {
                        tableModel.addRow(tableDate[i]);
                    }
                    table.setRowHeight(30);
                    table.setModel(tableModel);    //应用表格模型

                }
            }
        });

        jbtExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(jf, "导出成绩", FileDialog.SAVE);
                fd.setLocation(500, 350);
                fd.setVisible(true);
                String stringfile = fd.getDirectory() + fd.getFile() + ".xls";
                try {
                    PublicTool.exportTable(table, new File(stringfile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return gradeInput;
    }

    /*用户管理面板*/
    public JPanel getUser() {
        JPanel jpTeacheruser = new JPanel(new GridLayout(6, 2, 2, 2));
        JPanel jPanel = new JPanel(new BorderLayout());
        JFrame jf = this;
        JLabel jlId = new JLabel("教师号：");
        JLabel jlName = new JLabel("姓名：");
        JLabel jlSex = new JLabel("性别：");
        JLabel jlDate = new JLabel("出生日期：");
        JTextField jtfId = new JTextField(10);
        jtfId.setEditable(false);
        jtfId.setText(teacher.getT_id());
        JTextField jtfName = new JTextField(10);
        jtfName.setEditable(false);
        jtfName.setText(teacher.getT_name());
        JTextField jtfSex = new JTextField(10);
        jtfSex.setEditable(false);
        jtfSex.setText(teacher.getT_sex());
        JTextField jtfDate = new JTextField(10);
        jtfDate.setEditable(false);
        jtfDate.setText(teacher.getT_date());

        JButton jbt = new JButton("修改密码");
        JButton jbtQuit = new JButton("退出");
        JPanel jpBottom = new JPanel();
        jpBottom.add(jbt);
        jpBottom.add(jbtQuit);
        jpTeacheruser.add(jlId);
        jpTeacheruser.add(jtfId);
        jpTeacheruser.add(jlName);
        jpTeacheruser.add(jtfName);
        jpTeacheruser.add(jlSex);
        jpTeacheruser.add(jtfSex);
        jpTeacheruser.add(jlDate);
        jpTeacheruser.add(jtfDate);
        jPanel.add(jpTeacheruser, BorderLayout.NORTH);
        jPanel.add(jpBottom, BorderLayout.CENTER);
        //修改密码事件
        jbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField jpOldPassword = new JPasswordField();
                JPasswordField jpPassword = new JPasswordField();
                JPasswordField jpRepassword = new JPasswordField();
                Object[] message = {
                        "旧密码:", jpOldPassword,
                        "新密码:", jpPassword,
                        "重复新密码:", jpRepassword
                };
                int option = JOptionPane.showConfirmDialog(jf, message, "修改密码", JOptionPane.YES_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String oldPassword = new StudentService().getOldpassword(teacher.getT_id());
                    if (!oldPassword.equals(PublicTool.transitionPassword(jpOldPassword.getPassword()))) {
                        JOptionPane.showMessageDialog(jf, "原密码输入错误", "错误 ", 0);
                    } else if (!PublicTool.transitionPassword(jpPassword.getPassword()).equals(PublicTool.transitionPassword(jpRepassword.getPassword()))) {
                        JOptionPane.showMessageDialog(jf, "两次密码输入不一致", "错误 ", 0);
                    } else {
                        if (new StudentService().updatePassword(PublicTool.transitionPassword(jpPassword.getPassword()), teacher.getT_id())) {
                            JOptionPane.showMessageDialog(jf, "密码修改成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                            jf.dispose();
                            new LoginView().setVisible(true);
                        }
                    }
                }
            }
        });
        //退出事件
        jbtQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new LoginView().setVisible(true);
            }
        });
        return jPanel;
    }
}
