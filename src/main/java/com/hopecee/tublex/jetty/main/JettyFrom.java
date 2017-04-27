/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.jetty.main;

import com.hopecee.tublex.jetty.server.AppContext;
import com.hopecee.tublex.jetty.server.JettyServer;
import com.hopecee.tublex.jetty.server.ServerRunner;
//import com.hopecee.proshopnew.jetty.server.tx.MyBeanManager;
//import com.hopecee.proshopnew.jetty.server.tx.MyDataSource;
import java.awt.EventQueue;
import java.io.IOException;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.xml.sax.SAXException;

/**
 *
 * @author hope
 */
public class JettyFrom {

    public static void main(String[] args) throws SAXException, IOException, Exception {

        /* 
         Server server = new Server(8080);
         // ContextHandler context = new ContextHandler();
         WebAppContext webAppContext = new WebAppContext();
       
        
        
         String context_config=
         "<Configure id=\"webAppCtx\" class=\"org.eclipse.jetty.webapp.WebAppContext\">\n"+
         "<New id=\"BeanManager\" class=\"org.eclipse.jetty.plus.jndi.Resource\">\n"+
         "<Arg>\n"+ 
         "<Ref refid=\"webAppCtx\"/>\n"+ 
         "</Arg>\n"+
         "<Arg>BeanManager</Arg>\n"+
         "<Arg>\n"+
         "<New class=\"javax.naming.Reference\">\n"+
         "<Arg>javax.enterprise.inject.spi.BeanManager</Arg>\n"+
         "<Arg>org.jboss.weld.resources.ManagerObjectFactory</Arg>\n"+
         "<Arg/>\n"+
         "</New>\n"+
         "</Arg>\n"+
         "</New>\n"+
         "</Configure>\n";
            
         XmlConfiguration confJettyEnv = new XmlConfiguration(context_config);
         //confJettyEnv.configure(webAppContext);

         webAppContext.setConfigurationClasses(jettyConfigurationClasses);
 
         Resource jettyEnvXml = Resource.newResource("src/main/webapp/WEB-INF/appJetty-env.xml");
         URL url = jettyEnvXml.getURL();
         EnvConfiguration envConf = new EnvConfiguration();
         envConf.setJettyEnvXml(url);
         //envConf.configure(webAppContext);

         webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
         webAppContext.setContextPath("/");
        
         webAppContext.setResourceBase("src/main/webapp");
         // context.setContextPath("/");
         //context.set "src/main/webapp/WEB-INF/web.xml";
         //context.setResourceBase("src/main/webapp");
         webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
         webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
         server.setHandler(webAppContext);


         // WebAppContext wac = WebAppContext.getCurrentWebAppContext();


         server.start();
         server.join();
         }
    
         */


        final JettyServer jettyServer = new JettyServer();

        //AppConnectors connectors = new AppConnectors();
        //ManyConnectors manyConnectors = new ManyConnectors();


        ContextHandlerCollection contexts = new ContextHandlerCollection();
        WebAppContext webAppContext = new AppContext().buildWebAppContext();
        //Handler handler ;
        //handler.
        //contexts.setHandlers(new Handler[]{wac });

        
       

        contexts.setHandlers(new Handler[]{webAppContext});

       
        //Server server = new Server();
        //Context ctx = new Context(server, "/", Context.SESSIONS);
//Resource jettyEnvXml = Resource.newResource("src/main/webapp/WEB-INF/jetty-env.xml");
//XmlConfiguration confJettyEnv = new XmlConfiguration(jettyEnvXml.getURL());

//System.out.println("jettyEnvXml: " + jettyEnvXml.getURL());

        // confJettyEnv.configure(contexts);


        jettyServer.setHandler(contexts);
        jettyServer.setConnectors();
        //jettyServer.setConnectors(connectors.getConnectors());


        //jettyServer.setHandler(contexts);
       //jettyServer.setConnectors();
        //jettyServer.setConnectors(connectors.getConnectors());

        
        
        //==============================
        //Register tx
        // MyTransactionManager myTransactionManager = new MyTransactionManager();
        //myTransactionManager.register(webAppContext);


        //Register beanManager
        // MyBeanManager myBeanManager = new MyBeanManager();
        // myBeanManager.register(webAppContext);

        //Register dataSource
        // MyDataSource myDataSource = new MyDataSource();
        //  myDataSource.register(webAppContext);

        //Register webAppServerClasses
        // MyWebAppServerClasses MyWebAppServerClasses = new MyWebAppServerClasses();
        // MyWebAppServerClasses.register(webAppContext);






        //==========================

        Runnable runner = new Runnable() {
            @Override
            public void run() {
                ServerRunner serverRunner = new ServerRunner(jettyServer);
            }
        };
        EventQueue.invokeLater(runner);


        //jettyServer.setStopAtShutdown(true);
        // jetty.start();
        //jetty.stop();
    }   
}
