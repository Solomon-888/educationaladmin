package view;

;
import entity.User;
import service.StudentService;
import service.TeacherService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginView extends JFrame {
    JTextField jtfId = new JTextField(15);
    JPasswordField jpfPassword = new JPasswordField(15);
    JLabel jlId = new JLabel("账号：");
    JLabel jlPassword = new JLabel("密码：");
    JButton jbtLogin = new JButton("登录");

    public LoginView() {
        this.setTitle("教务管理系统");
        this.setLayout(new FlowLayout());
        Container cp = this.getContentPane();
        JPanel jpId = new JPanel();
        jpId.add(jlId);
        jpId.add(jtfId);
        JPanel jpPassword = new JPanel();
        jpPassword.add(jlPassword);
        jpPassword.add(jpfPassword);
        JPanel jpLogin = new JPanel();
        jpLogin.add(jbtLogin);
        this.add(jpId);
        this.add(jpPassword);
        this.add(jpLogin);
        /*this.pack(); //设置初始大小*/
        this.setSize(300, 155);
        //居中
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame jf = this;
        jpfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == '\n')
                    jbtLogin.doClick();
            }
        });
        jbtLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uId = jtfId.getText();
                String uPassword = "";
                char[] password = jpfPassword.getPassword();
                for (int i = 0; i < password.length; i++) {
                    uPassword += password[i];
                }
                User user = new User(uId, uPassword, null);
                UserService userService = new UserService();
                if (userService.judgementIDandPassword(user)) {
                    userService.getStatus(user);
                    if (user.getuStatus().equals("教师")) {
                        jf.dispose();
                        TeacherView teacherView = new TeacherView(new TeacherService().getTeacher(uId));
                        teacherView.setVisible(true);
                    } else {
                        jf.dispose();
                        StudentService studentService = new StudentService();
                        StudentView studentView = new StudentView(studentService.getStudent(uId));
                        studentView.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "用户名密码输入有误，请重新输入", "错误", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

}
