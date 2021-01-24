package view;

import entity.Grade;
import entity.SelectCourse;
import entity.Student;
import entity.TeachingClass;
import service.StudentService;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import tool.BarTool;
import tool.PublicTool;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentView extends JFrame {

    Student student;

    //刷新面板
    public void replacementPanel(Container change, Container res) {
        change.removeAll();
        change.add(res);
        change.validate();
        change.repaint();
    }

    /*学生端视图*/
    public StudentView(Student student) {
        super("教务管理系统");
        this.student = student;
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        JButton jbtHome = new BarTool().getButton("首页", "src/img/home.png");
        JButton jbtCourse = new BarTool().getButton("课程查询", "src/img/course.png");
        JButton jbtSelectcourse = new BarTool().getButton("选课系统", "src/img/Selectcourse.png");
        JButton jbtGrade = new BarTool().getButton("成绩查询", "src/img/grade.png");
        JButton jbtUser = new BarTool().getButton("用户管理", "src/img/user.png");

        JPanel bar = new JPanel();
        bar.add(jbtHome);
        bar.add(jbtCourse);
        bar.add(jbtSelectcourse);
        bar.add(jbtGrade);
        bar.add(jbtUser);
        JPanel mid = getStudenthome();
        cp.add(bar, BorderLayout.NORTH);
        cp.add(mid, BorderLayout.CENTER);

        jbtHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getStudenthome());
            }
        });

        jbtSelectcourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getStudentselectCourse());
            }
        });

        jbtCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getStudentcourse());
            }
        });

        jbtGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getStudentgrade());
            }
        });

        jbtUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replacementPanel(mid, getStudentuser());
            }
        });

        this.setSize(800, 650);
        //居中
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*首页面板*/
    public JPanel getStudenthome() {
        JPanel jpHome = new JPanel();
        JTextArea textArea = new JTextArea();
        String text = student.getS_name();
        text += "同学：欢迎使用教务管理系统！";
        textArea.setText(text);
        textArea.setFont(new Font("黑体", 1, 30));
        jpHome.add(textArea);
        return jpHome;
    }

    /*获取课程表面板*/
    public JPanel getStudentcourse() {

        class TableViewRenderer extends JTextArea implements TableCellRenderer {
            public TableViewRenderer() {
                //将表格设为自动换行
                setLineWrap(true);
                //单词过长的时候是否把长单词移动到下一行
                setWrapStyleWord(true);
            }

            public Component getTableCellRendererComponent(JTable jtable, Object obj, //obj指的是单元格内容
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                setText(obj == null ? "" : obj.toString()); //利用JTextArea的setText设置文本方法
                return this;
            }
        }


        JPanel jpCourse = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JLabel jlYear = new JLabel("学年:");
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("--请选择--");    //向下拉列表中添加一项
        List<String> year = new StudentService().getYear();
        for (String str : year) {
            comboBox.addItem(str);
        }
        JButton jbtSelect = new JButton("查询");
        top.add(jlYear);
        top.add(comboBox);
        top.add(jbtSelect);
        jpCourse.add(top, BorderLayout.NORTH);

        //不可编辑表格内容
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane tab = new JScrollPane(table);

        //表格内容居中且换行
        TableViewRenderer tableViewRenderer = new TableViewRenderer();          //自定义表格格式渲染器
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

        jpCourse.add(tab, BorderLayout.CENTER);
        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object cp_year = comboBox.getSelectedItem();
                List<TeachingClass> teachingclass = new StudentService().getTeachingclass(student.getS_id(), (String) cp_year);

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

                for (int i = 0; i < teachingclass.size(); i++) {
                    String res;
                    String tc_time = teachingclass.get(i).getTc_time();
                    //获得最终上课时间与地点
                    String[] res_time = tc_time.split(" ");
                    for (int j = 0; j < res_time.length; j++) {
                        int week = res_time[j].charAt(0) - '0';
                        int section = res_time[j].charAt(2) - '0';
                        res = "\n  " + teachingclass.get(i).getC_name() + "\n  " + teachingclass.get(i).getTc_point();
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
        return jpCourse;
    }

    /*选课面板*/
    public JPanel getStudentselectCourse() {
        JPanel jpSC = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JLabel jlYear = new JLabel("学年:");
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("--请选择--");    //向下拉列表中添加一项
        List<String> year = new StudentService().getYear();
        for (String str : year) {
            comboBox.addItem(str);
        }
        JButton jbtSelect = new JButton("查询");
        JButton jbtAdd = new JButton("选择");

        top.add(jbtAdd);
        top.add(jlYear);
        top.add(comboBox);
        top.add(jbtSelect);
        jpSC.add(top, BorderLayout.NORTH);

        //JTable table = new JTable();
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
        tab.setPreferredSize(new Dimension(750, 480));
        //设置透明
        tab.setOpaque(false);
        tab.getViewport().setOpaque(false);

        jpSC.add(tab, BorderLayout.CENTER);
        JFrame jf = this;

        //查询选课点击事件
        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object cp_year = comboBox.getSelectedItem();
                List<SelectCourse> courses = new StudentService().getSelectcourse(student.getS_id(), (String) cp_year);
                Object[][] tableDate = new Object[courses.size()][5];
                for (int i = 0; i < courses.size(); i++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == 0)
                            tableDate[i][j] = courses.get(i).getCourseName();
                        else if (j == 1)
                            tableDate[i][j] = courses.get(i).getCredit();
                        else if (j == 2)
                            tableDate[i][j] = courses.get(i).getTeacherClass();
                        else if (j == 3)
                            tableDate[i][j] = courses.get(i).getTeacherName();
                        else {
                            String str = "";
                            str += courses.get(i).getCoursePoint();
                            str += '/';
                            String[] s = courses.get(i).getCourseTime().split(" ");
                            for (int k = 0; k < s.length; k++) {
                                str += PublicTool.getWeek(s[k].charAt(0));
                                str += PublicTool.getPitch(s[k].charAt(2));
                                if (k != s.length - 1)
                                    str += ',';
                            }
                            tableDate[i][j] = str;
                        }
                    }
                }
                String[] name = {"课程名", "课程学分", "课程班级", "教学教师", "上课地点/上课时间"};
                table.removeAll();

                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //获得表格模型

                tableModel.setRowCount(0);    //清空表格中的数据

                tableModel.setColumnIdentifiers(name);    //设置表头
                for (int i = 0; i < courses.size(); i++) {
                    tableModel.addRow(tableDate[i]);
                }
                PublicTool.setColumnSize(table, 0, 140, 140, 140);
                PublicTool.setColumnSize(table, 1, 80, 80, 80);
                PublicTool.setColumnSize(table, 2, 140, 140, 140);
                PublicTool.setColumnSize(table, 3, 80, 80, 80);

                table.setRowHeight(30);
                table.setModel(tableModel);    //应用表格模型
            }
        });

        //增加课程
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = table.getSelectedRow();//获取你选中的行号（记录）
                    String className = table.getValueAt(count, 2).toString();//读取你获取行号的某一列的值（也就是字段）
                    Object cp_year = comboBox.getSelectedItem();
                    List<SelectCourse> courses = new StudentService().getSelectcourse(student.getS_id(), (String) cp_year);
                    //选中的课程安排
                    String courseTime = courses.get(count).getCourseTime();
                    //判断是否与当前课程冲突
                    boolean conflict = new StudentService().isConflict(student.getS_id(), (String) cp_year, courseTime);
                    if (conflict) {
                        JOptionPane.showMessageDialog(jf, "请检查选择的课是否与已选的选课冲突！", "错误 ", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean courseAdd = new StudentService().courseAdd(className, student.getS_id());
                        if (courseAdd)
                            JOptionPane.showMessageDialog(jf, "选课成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(jf, "请检查是否已选择该课", "错误 ", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(jf, "未选择要选修的课程！", "错误 ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return jpSC;
    }

    /*获取学生成绩面板*/
    public JPanel getStudentgrade() {
        JPanel jpGrade = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JLabel jlYear = new JLabel("学年:");
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("--请选择--");    //向下拉列表中添加一项
        List<String> year = new StudentService().getYear();
        for (String str : year) {
            comboBox.addItem(str);
        }
        JButton jbtSelect = new JButton("查询");
        top.add(jlYear);
        top.add(comboBox);
        top.add(jbtSelect);
        jpGrade.add(top, BorderLayout.NORTH);

        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
        tab.setPreferredSize(new Dimension(650, 500));
        //设置透明
        tab.setOpaque(false);
        tab.getViewport().setOpaque(false);

        jpGrade.add(tab, BorderLayout.CENTER);
        jbtSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object cp_year = comboBox.getSelectedItem();
                List<Grade> grade = new StudentService().getGrade(student.getS_id(), (String) cp_year);
                Object[][] tableDate = new Object[grade.size()][5];
                for (int i = 0; i < grade.size(); i++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == 0)
                            tableDate[i][j] = grade.get(i).getS_id();
                        else if (j == 1)
                            tableDate[i][j] = grade.get(i).getCourse_name();
                        else if (j == 2)
                            tableDate[i][j] = grade.get(i).getG_ordinary();
                        else if (j == 3)
                            tableDate[i][j] = grade.get(i).getG_final();
                        else
                            tableDate[i][j] = grade.get(i).getG_sum();
                    }
                }
                String[] name = {"学号", "课程名", "平时性成绩", "终结性成绩","总成绩"};
                table.removeAll();

                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    //获得表格模型

                tableModel.setRowCount(0);    //清空表格中的数据

                tableModel.setColumnIdentifiers(name);    //设置表头
                PublicTool.setColumnSize(table,1,10,160,160);
                for (int i = 0; i < grade.size(); i++) {
                    tableModel.addRow(tableDate[i]);
                }
                table.setRowHeight(30);
                table.setModel(tableModel);    //应用表格模型
            }
        });
        return jpGrade;
    }

    /*学生个人信息以及修改密码面板*/
    public JPanel getStudentuser() {
        JPanel jpStudentuser = new JPanel(new GridLayout(6, 2, 2, 2));
        JPanel jPanel = new JPanel(new BorderLayout());
        JFrame jf = this;
        JLabel jlId = new JLabel("学号：");
        JLabel jlName = new JLabel("姓名：");
        JLabel jlSex = new JLabel("性别：");
        JLabel jlDate = new JLabel("出生日期：");
        JLabel jlClass = new JLabel("行政班级：");
        JTextField jtfId = new JTextField(10);
        jtfId.setEditable(false);
        jtfId.setText(student.getS_id());
        JTextField jtfName = new JTextField(10);
        jtfName.setEditable(false);
        jtfName.setText(student.getS_name());
        JTextField jtfSex = new JTextField(10);
        jtfSex.setEditable(false);
        jtfSex.setText(student.getS_sex());
        JTextField jtfDate = new JTextField(10);
        jtfDate.setEditable(false);
        jtfDate.setText(student.getS_date().toString());
        JTextField jtfClass = new JTextField(10);
        jtfClass.setEditable(false);
        jtfClass.setText(student.getC_id());
        JButton jbt = new JButton("修改密码");
        JButton jbtQuit = new JButton("退出");
        JPanel jpBottom = new JPanel();
        jpBottom.add(jbt);
        jpBottom.add(jbtQuit);
        jpStudentuser.add(jlId);
        jpStudentuser.add(jtfId);
        jpStudentuser.add(jlName);
        jpStudentuser.add(jtfName);
        jpStudentuser.add(jlSex);
        jpStudentuser.add(jtfSex);
        jpStudentuser.add(jlDate);
        jpStudentuser.add(jtfDate);
        jpStudentuser.add(jlClass);
        jpStudentuser.add(jtfClass);
        jPanel.add(jpStudentuser, BorderLayout.NORTH);
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
                    String oldPassword = new StudentService().getOldpassword(student.getS_id());
                    System.out.println();
                    if (!oldPassword.equals(PublicTool.transitionPassword(jpOldPassword.getPassword()))) {
                        JOptionPane.showMessageDialog(jf, "原密码输入错误", "错误 ", 0);
                    } else if (!PublicTool.transitionPassword(jpPassword.getPassword()).equals(PublicTool.transitionPassword(jpRepassword.getPassword()))) {
                        JOptionPane.showMessageDialog(jf, "两次密码输入不一致", "错误 ", 0);
                    } else {
                        if (new StudentService().updatePassword(PublicTool.transitionPassword(jpPassword.getPassword()), student.getS_id())) {
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
