/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.jetty.server;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author hope
 */
public class ServerRunner extends JFrame {
    
    private static final long serialVersionUID = 8261022096695034L;
	private static final Logger LOGGER = LogManager.getLogger(ServerRunner.class);

	private JButton btnStartStop;

       public ServerRunner(final JettyServer jettyServer) {
		super("Start/Stop Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnStartStop = new JButton("Start");
		btnStartStop.addActionListener
			(new ServerStartStopActionListner(jettyServer));
		add(btnStartStop,BorderLayout.CENTER);
		setSize(300,300);
                
           /* ShutdownHook is added so that whenever the user clicks on the
            * close button on the top right corner of the window, it will 
            * first check whether the server is running or not. If running, 
            * it will stop the server before exiting.
            */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
                            checkBTMproperlyShotdown();
		//		if(jettyServer.isStarted()) {
					try {
		//				jettyServer.stop();
					} catch (Exception exception) {
					}
				}
		//	}
		},"Stop Jetty Hook")); 
		setVisible(true);
	}
       
         private void checkBTMproperlyShotdown() {
        LOGGER.info("BitronixTransactionManager - Checking if properly shutdown");
        BitronixTransactionManager txm = TransactionManagerServices.getTransactionManager();
        if (txm != null) {
            LOGGER.debug("BitronixTransactionManager - Bitronix Transaction Manager was not properly shutdown");
            try {
                txm.shutdown();
            } catch (Exception exception) {
            }

        }
    }
         
}
