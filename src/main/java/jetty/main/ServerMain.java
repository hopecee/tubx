/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.main;

import java.io.File;
import jetty.server.AppContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author hope
 */
public class ServerMain {

    
     public static void main(final String[] args) throws Exception {
        //LOGGER.debug("ServerMain msg");

        Server server = new Server(8080);
         // WebAppContext webapp = new WebAppContext();
         AppContext appContext = new AppContext();
        WebAppContext webapp = appContext.buildWebAppContext(server.getServer());
       // webapp.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        //webapp.setContextPath("/");
        //webapp.setTempDirectory(new File("C:\\Users\\Hope\\AppData\\Local\\Temp\\embedded-jetty-jsp"));
        //webapp.setWar("E:\\Users\\Hope\\Documents\\NetBeansProjects\\tuShopWar-parent\\tuShopWar-assembly\\target\\tuShopWar-assembly-1.0-SNAPSHOT.war");
        server.setHandler(webapp);
        server.start();
        server.join();
    }





}
