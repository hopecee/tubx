/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.main.server;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author hope
 */
public class ServerStartStopActionListner implements ActionListener {

    private final JettyServer jettyServer;

    public ServerStartStopActionListner(JettyServer jettyServer) {
        this.jettyServer = jettyServer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnStartStop = (JButton) e.getSource();
        if (jettyServer.isStarted()) {
            btnStartStop.setText("Stopping...");
            btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                jettyServer.stop();
            } catch (Exception exception) {
            }
            btnStartStop.setText("Start");
            btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else if (jettyServer.isStopped()) {
            btnStartStop.setText("Starting...");
            btnStartStop.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                jettyServer.start();
            } catch (Exception exception) {
            }
            btnStartStop.setText("Stop");
            btnStartStop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }




    }
}
