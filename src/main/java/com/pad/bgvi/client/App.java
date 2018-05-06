package com.pad.bgvi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.pad.bgvi.common.IRMIServer;
import com.pad.bgvi.common.RMIServerUtil;

public class App 
{
	private static IRMIServer server;
    public static void main( String[] args )
    {
    	try {
    		System.setProperty("java.security.policy","file:///home/.../<filename>.policy");
    		server = initServerConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    	
    	SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				ApplicationFrame frame = new ApplicationFrame("Server Management Tool");
				frame.setVisible(true);
				frame.setSize(800, 500);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
    }
    
    private static IRMIServer initServerConnection() throws MalformedURLException, RemoteException, NotBoundException {
    	IRMIServer server = (IRMIServer) Naming.lookup(RMIServerUtil.URI);
    	return server;
    }

	public static IRMIServer getServer() {
		return server;
	}
}
