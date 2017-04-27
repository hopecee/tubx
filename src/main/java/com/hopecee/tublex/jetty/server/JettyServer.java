/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.xml.sax.SAXException;

/**
 *
 * @author hope
 */
public class JettyServer {

    // The Server
    Server server = new Server();
    //private AppContext appContext = new AppContext();
    ManyConnectors manyConnectors = new ManyConnectors();
    
    public JettyServer() throws SAXException, IOException, Exception {
        //this(8585);
    }
    
    public JettyServer(Integer runningPort) {
        server = new Server(runningPort);
        
    }
    
    public void setHandler(ContextHandlerCollection contexts) {
       
        server.setHandler(contexts);
    }
    
    public void setConnectors() {
        server.setConnectors(manyConnectors.getConnectors(server));
    }
    
    public void start() throws Exception {
        server.start();
        
    }
    
    public void stop() throws Exception {
        server.stop();
        server.join();
    }
    
    public boolean isStarted() {
        return server.isStarted();
    }
    
    public boolean isStopped() {
        return server.isStopped();
    }
}
