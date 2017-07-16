package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Lee on 2015-06-06.
 */
public class OthersFrame extends JFrame{
    private JPanel rootPanel;
    private JButton backButton;
    private JTable userTable;
    private JTextField searchText;
    private JComboBox selectBox;
    private JButton searchButton;

    private Connection con;
    public OthersFrame(Connection con){
        super("Search Frame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        this.con = con;

        componentSetting();
        screenSetting();
    }


    DefaultTableModel tableModel;
    private void componentSetting(){
        selectBox.setModel(new DefaultComboBoxModel(new String[] {"ID", "Name", "Age"}));
        tableModel = new DefaultTableModel(new String[] {"ID", " Name", "Age", "Favorite"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.setModel(tableModel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void screenSetting(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();

        int xpos = (int)(screen.getWidth()/2 - size.getWidth()/2);
        int ypos = (int)(screen.getHeight()/2 - size.getHeight()/2);
        setLocation(xpos, ypos);

        setResizable(false);
        setVisible(true);
    }

    private void search(){
        if(selectBox.getSelectedItem().equals("ID")){
            tableModel.setRowCount(0);

            try {
                //연결에 사용할 Statement
                Statement stat = con.createStatement();
                ResultSet rs = null;
                //쿼리날리기
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM user");
                }
                else {
                    rs = stat.executeQuery("SELECT * FROM user WHERE id = '" + searchText.getText() + "';");
                }
                //결과
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Integer age = rs.getInt("age");
                    String filename = rs.getString("favorite");
                    String musicName;
                    if(filename == null){
                        musicName = "null";
                    }
                    else {
                        ResultSet rs2 = stat.executeQuery("SELECT name FROM Music WHERE filename = '" + filename + "';");
                        rs2.next();
                        musicName = rs2.getString("name");
                    }
                    tableModel.addRow(new String[] {id, name, age.toString(), musicName});
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                System.out.println("SQL Exception");
            }

        }
        else if(selectBox.getSelectedItem().equals("Name")){
            tableModel.setRowCount(0);

            try {
                //연결에 사용할 Statement
                Statement stat = con.createStatement();
                ResultSet rs = null;
                //쿼리날리기
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM user");
                }
                else {
                    rs = stat.executeQuery("SELECT * FROM user WHERE name = '" + searchText.getText() + "';");
                }
                //결과
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Integer age = rs.getInt("age");
                    tableModel.addRow(new String[] {id, name, age.toString()});
                }
            }
            catch(SQLException e){
                System.out.println("SQL Exception");
            }

        }
        else if(selectBox.getSelectedItem().equals("Age")){
            tableModel.setRowCount(0);

            try {
                //연결에 사용할 Statement
                Statement stat = con.createStatement();
                ResultSet rs = null;
                //쿼리날리기
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM user");
                }
                else {
                    rs = stat.executeQuery("SELECT * FROM user WHERE age = " + searchText.getText() + ";");
                }
                //결과
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Integer age = rs.getInt("age");
                    tableModel.addRow(new String[] {id, name, age.toString()});
                }
            }
            catch(SQLException e){
                System.out.println("SQL Exception");
            }

        }
    }
}
