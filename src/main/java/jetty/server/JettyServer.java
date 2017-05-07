/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.server;

import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.xml.sax.SAXException;

/**
 *
 * @author hope
 */
public class JettyServer {

    Server server = new Server();
    
    ManyConnectors manyConnectors = new ManyConnectors();
    
    public JettyServer() throws SAXException, IOException, Exception {
        //this(8585);
    }
    
    public JettyServer(Integer runningPort) {
        server = new Server(runningPort);
        
    }
    
        public JettyServer(ThreadPool threadPool) {
        server = new Server(threadPool);
    }

    public JettyServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
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
