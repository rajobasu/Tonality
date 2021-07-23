package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class WaitWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WaitWindow frame = new WaitWindow();
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
	public WaitWindow() {
		setTitle("Connecting ...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblTryingToConnect = new JLabel("Trying to Connect to Server ...   Please Wait !!!");
		lblTryingToConnect.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 18));
		lblTryingToConnect.setForeground(Color.RED);
		lblTryingToConnect.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTryingToConnect, BorderLayout.CENTER);
	}

}
