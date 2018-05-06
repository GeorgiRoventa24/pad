package com.pad.bgvi.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = -2076041387921527190L;
	private Container container;
	private JButton startServerButton;
	private JButton restartServerButton;
	private JButton stopServerButton;
	private JTextArea textArea;
	
	public ApplicationFrame(String title) {
		super(title);

		createContent();
	}

	private void createContent() {
		setLayout(new BorderLayout());
		container = getContentPane();

		startServerButton = new JButton("Start");
		startServerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					App.getServer().startServer();
					textArea.setText("Server started successfully!");
				} catch (RemoteException e1) {
					e1.printStackTrace();
					textArea.setText("Failed to start the server:\n" + e1.getMessage());
				}
			}
		});
		startServerButton.setSize(100, 30);
		
		restartServerButton = new JButton("Restart");
		restartServerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					App.getServer().restartServer();
					textArea.setText("Server restarted successfully!");
				} catch (RemoteException e1) {
					e1.printStackTrace();
					textArea.setText("Failed to restart the server:\n" + e1.getMessage());
				}
			}
		});
		restartServerButton.setSize(100, 30);
		restartServerButton.setLocation(200, 0);
		
		stopServerButton = new JButton("Stop");
		stopServerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					App.getServer().stopServer();
					textArea.setText("Server stopped Successfully!");
				} catch (RemoteException e1) {
					e1.printStackTrace();
					textArea.setText("Failed to stop the server:\n" + e1.getMessage());
				}
			}
		});
		stopServerButton.setSize(100, 30);
		stopServerButton.setLocation(300, 0);
		
		textArea = new JTextArea();
		textArea.setLocation(0, 200);
		
		container.add(startServerButton, BorderLayout.NORTH);
		container.add(restartServerButton, BorderLayout.AFTER_LAST_LINE);
		container.add(stopServerButton, BorderLayout.AFTER_LAST_LINE);
		container.add(textArea, BorderLayout.AFTER_LAST_LINE);
	}
}
