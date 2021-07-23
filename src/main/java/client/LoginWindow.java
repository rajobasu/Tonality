package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import resources.Profile;
import java.awt.Label;

public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int age;
	private String username;
	private String password;
	private Profile p;
	private JPanel contentPane;
	private JTextField textField_Username_register;
	private JPasswordField passwordField_register;
	private JTextField textField_Age;
	private JTextField txtFullFilePath;
	private JTextField textField_Username_login;
	private JPasswordField passwordField_login;
    private Client client;
    private JLabel label;
    private JLabel label_1;
    private JButton btnLogin;
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JLabel lblNewLabel;
    private JLabel label_2;
    private JPanel panel_1;
    private JLabel lblRegister;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblAge;
    private JLabel lblPicture;
    private JButton btnRegister;
	private static JFrame tempFrame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
           
        } catch (InstantiationException ex) {

        } catch (IllegalAccessException ex) {

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tempFrame=new WaitWindow();
					Client clt=new Client();
					tempFrame.dispose();
					LoginWindow frame = new LoginWindow(clt);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	  
	
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow(Client clt) {
		this.client=clt;
		System.out.println(clt);
		
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 174, 260);
		contentPane.add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("Sign in", null, panel, null);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("Log In :");
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel.setBounds(0, 0, 169, 44);
		panel.add(lblNewLabel);
		
		label = new JLabel("Username");
		label.setBounds(53, 41, 48, 14);
		panel.add(label);
		
		textField_Username_login = new JTextField();
		textField_Username_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			     
			
			}
		});
		textField_Username_login.setColumns(10);
		textField_Username_login.setBounds(10, 55, 149, 20);
		panel.add(textField_Username_login);
		
		label_1 = new JLabel("Password");
		label_1.setBounds(53, 86, 46, 14);
		panel.add(label_1);
		
		passwordField_login = new JPasswordField();
		passwordField_login.setBounds(10, 98, 149, 20);
		panel.add(passwordField_login);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			username =textField_Username_login.getText();
			password=new String(passwordField_login.getPassword());
		    p=client.login(username, password);
			textField_Username_login.setText("");
			passwordField_login.setText("");
			if(p==null){
				label_2.setText("Username or Password Incorrect");
			}else{
				new MainWindow(p,client);
				setVisible(false);
				dispose();
				
			}
			
			
			
			}
		});
		btnLogin.setBounds(40, 187, 89, 23);
		panel.add(btnLogin);
		
		label_2 = new JLabel("");
		label_2.setBounds(20, 129, 139, 14);
		panel.add(label_2);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Sign up", null, panel_1, null);
		panel_1.setLayout(null);
		
		lblRegister = new JLabel("Register :-");
		lblRegister.setFont(new Font("Arial Rounded MT Bold", Font.BOLD | Font.ITALIC, 17));
		lblRegister.setBounds(0, 0, 169, 44);
		panel_1.add(lblRegister);
		
		textField_Username_register = new JTextField();
		textField_Username_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    username=textField_Username_register.getText();
			}
		});
		textField_Username_register.setBounds(10, 55, 149, 20);
		panel_1.add(textField_Username_register);
		textField_Username_register.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(60, 39, 48, 14);
		panel_1.add(lblNewLabel_1);
		
		passwordField_register = new JPasswordField();
		passwordField_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    password=new String(passwordField_register.getPassword());    
			
			}
		});
		passwordField_register.setBounds(10, 102, 149, 20);
		panel_1.add(passwordField_register);
		
		lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(61, 86, 46, 14);
		panel_1.add(lblNewLabel_2);
		
		textField_Age = new JTextField();
		textField_Age.setBounds(73, 133, 86, 20);
		panel_1.add(textField_Age);
		textField_Age.setColumns(10);
		
		lblAge = new JLabel("Age");
		lblAge.setBounds(20, 136, 46, 14);
		panel_1.add(lblAge);
		
		lblPicture = new JLabel("Picture ");
		lblPicture.setBounds(20, 161, 46, 14);
		panel_1.add(lblPicture);
		
		txtFullFilePath = new JTextField();
		txtFullFilePath.setText("Full File Path");
		txtFullFilePath.setBounds(73, 164, 86, 20);
		panel_1.add(txtFullFilePath);
		txtFullFilePath.setColumns(10);
		
		
		Label label_for_Error = new Label("");
		label_for_Error.setBounds(0, 210, 169, 22);
		panel_1.add(label_for_Error);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			    password=new String(passwordField_register.getPassword());
			    username=textField_Username_register.getText();
			    try{
			    	age=Integer.parseInt(textField_Age.getText());
			        textField_Age.setBackground(Color.WHITE);
			    }catch(NumberFormatException e){
			    	textField_Age.setBackground(Color.red);
			    	return;
			    }
			    textField_Username_register.setText("");
				passwordField_register.setText("");
				System.out.println(client);
				boolean successful=client.register(username, password);
			    if(successful){
			    	label_for_Error.setText("Successful!! Please Login");
			    }else{
			    	label_for_Error.setText("Duplicate Username !!!");
			    }
				
			    
			    
			}
		});
		btnRegister.setBounds(41, 187, 89, 23);
		panel_1.add(btnRegister);
		
		
		
		
	}
}
