/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.main;

import jetty.server.AppContext;
import jetty.server.JettyServer;
import jetty.server.ServerRunner;
//import com.hopecee.proshopnew.jetty.server.tx.MyBeanManager;
//import com.hopecee.proshopnew.jetty.server.tx.MyDataSource;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
//import jetty.bootstrap.JettyBootstrap;
import jetty.server.AppContext;
import jetty.server.JettyServer;
import jetty.server.ManyConnectors;
import jetty.server.ServerRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.xml.sax.SAXException;

/**
 *
 * @author hope
 */
public class ServerMain {

    private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);
    private static final String RESOURCE_BASE_IMG = "E:/proShopDir";

    public static void main(String[] args) throws Exception {

        LOGGER.debug("ServerMain msg");

        //String goArg = "next";
        if (args.length > 0 && "next".equals(args[0])) {
            // if (true) {
            System.out.println("PPPPPPPPPPPP : " + args.length);
            System.out.println("PPPPPPPPPPPP 2: " + args[0]);
            try {
                URL warLocation = ServerMain.class.getProtectionDomain().getCodeSource().getLocation();
                if (warLocation == null) {
                    throw new IOException("ServerMain not discoverable");
                }

                //ClassLoader jb = ServerMain.class.getClassLoader();
                File warFile = new File(warLocation.toURI());
                System.out.println("warFile.toPath() : " + warFile.toPath().toRealPath().toString());

                System.setProperty("com.hopecee.tublex.jetty.main.LOCATION", warFile.toPath().toRealPath().toString());

                //ServerMain.main(args);
            } catch (IOException | URISyntaxException | SecurityException | IllegalArgumentException t) {
                t.printStackTrace(System.out);
                System.exit(-1);
            }

        } else {
        }

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
        /**
         * final JettyServer jettyServer = new JettyServer();
         *
         * //AppConnectors connectors = new AppConnectors(); //ManyConnectors
         * manyConnectors = new ManyConnectors(); ContextHandlerCollection
         * contexts = new ContextHandlerCollection();
         *
         * WebAppContext webAppContext = new
         * AppContext().buildWebAppContext(jettyServer); //Handler handler ;
         * //handler. //contexts.setHandlers(new Handler[]{wac });
         *
         * contexts.setHandlers(new Handler[]{webAppContext});
         *
         * //Server server = new Server(); //Context ctx = new Context(server,
         * "/", Context.SESSIONS); //Resource jettyEnvXml =
         * Resource.newResource("src/main/webapp/WEB-INF/jetty-env.xml");
         * //XmlConfiguration confJettyEnv = new
         * XmlConfiguration(jettyEnvXml.getURL());
         * //System.out.println("jettyEnvXml: " + jettyEnvXml.getURL()); //
         * confJettyEnv.configure(contexts); jettyServer.setHandler(contexts);
         * jettyServer.setConnectors();
         * //jettyServer.setConnectors(connectors.getConnectors());
         *
         * //jettyServer.setHandler(contexts); //jettyServer.setConnectors();
         * //jettyServer.setConnectors(connectors.getConnectors());
         * //============================== //Register tx //
         * MyTransactionManager myTransactionManager = new
         * MyTransactionManager();
         * //myTransactionManager.register(webAppContext); //Register
         * beanManager // MyBeanManager myBeanManager = new MyBeanManager(); //
         * myBeanManager.register(webAppContext); //Register dataSource //
         * MyDataSource myDataSource = new MyDataSource(); //
         * myDataSource.register(webAppContext); //Register webAppServerClasses
         * // MyWebAppServerClasses MyWebAppServerClasses = new
         * MyWebAppServerClasses(); //
         * MyWebAppServerClasses.register(webAppContext);
         * //========================== Runnable runner = new Runnable() {
         *
         * @Override public void run() { ServerRunner serverRunner = new
         * ServerRunner(jettyServer); } }; EventQueue.invokeLater(runner);
         *
         * //jettyServer.setStopAtShutdown(true); // jetty.start();
         * //jetty.stop();
         */
        System.out.println("aClass.getName() = " + ServerMain.class.getClassLoader());
        System.out.println("aClass.getName() = " + ServerMain.class.getClassLoader().getParent());
        // Setup Threadpool
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(1000);
        threadPool.setMinThreads(10);
        threadPool.setIdleTimeout(60);
        //threadPool.setDetailedDump(false);

        final JettyServer jettyServer = new JettyServer(threadPool);
        /*JNDI AND OTHER SERVER SPECIFIC BUSINESS*/
        // enableAnnotationScanning(jettyServer.getServer());

        /*
        // Setup JMX
        MBeanServer mbServer = ManagementFactory.getPlatformMBeanServer();
        MBeanContainer mbContainer = new MBeanContainer(mbServer);
        jettyServer.getServer().addEventListener(mbContainer);
        jettyServer.getServer().addBean(mbContainer);
        //Create an RMI connector server   
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1999/server");
        ConnectorServer cs = new ConnectorServer(url, "org.eclipse.jetty.jmx:name=rmiconnectorserver");
        System.out.println("ZZZZZZZZZZZZZ  : "+jettyServer.getServer().getBeans().size());
        Iterator<Object> it = jettyServer.getServer().getBeans().iterator();
                while(it.hasNext()){
                    System.out.println("ZZZZZZZZZZZZZ  : "+it.next().toString());  
        }
        System.out.println("ZZZZZZZZZZZZZ  : "+cs.isStarted());
        cs.start();
       // Add loggers MBean to server (will be picked up by MBeanContainer above)
        jettyServer.getServer().addBean(LogManager.getLogger("TuShop Logger"));
        **/
        ContextHandlerCollection contexts = new ContextHandlerCollection();

//EmbeddedCdiHandler context = new EmbeddedCdiHandler();
        //AppContext appContext = new AppContext();
        WebAppContext webAppContext = new AppContext().buildWebAppContext(jettyServer);

        /**
         * ** Serve static contents(images etc.) // ServletContextHandler
         * servletContextHandler = new ServletContextHandler();
         * servletContextHandler.setContextPath("/static");
         * //servletContextHandler.setBaseResource(new PathResource(new
         * File(STATIC_CONTENT_FOLDER))); //
         * servletContextHandler.addFilter(GuiceFilter.class, "/*", null); //
         * Create default servlet (servlet api required) // The name of
         * DefaultServlet should be set to 'defualt'.
         *
         * //DefaultServlet staticServlet = new DefaultServlet(); ServletHolder
         * staticContent = new ServletHolder("static-contents",
         * DefaultServlet.class); staticContent.setInitParameter("resourceBase",
         * "E:/proShopDir"); staticContent.setInitParameter("dirAllowed",
         * "false"); //staticContent.setInitParameter("pathInfoOnly","true");
         *
         * //webAppContext.addServlet(staticContent, "/*");
         * //webAppContext.getServletHandler()
         *
         * servletContextHandler.addServlet(staticContent, "/*");
         */
// Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
//ServletHolder holderDef  = new ServletHolder("default", DefaultServlet.class);
        // holderDef.setInitParameter("resourceBase", "E:/proShopDir");
        //holderDef.setInitParameter("dirAllowed", "true");
//staticContent.setInitParameter("pathInfoOnly","true");
        //webAppContext.addServlet(staticContent, "/*"); 
//servletContextHandler.addServlet(holderDef, "/");
        //Create servlet context handler for main servlet.
        //ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //servletContextHandler.setContextPath("/");
        //  ServletHolder staticContent = new ServletHolder( staticServlet);
        //staticContent.setInitParameter("resourceBase", STATIC_CONTENT_FOLDER);
        // servletContextHandler.addServlet(staticContent, "/*");
//webAppContext.addServlet(staticContent, "/*"); 
        //**** END static contents ***//
        // We don't have static content so just set up servlets.
        //ServletContextHandler servletContextHandler1 = new ServletContextHandler();
        // servletContextHandler1.setContextPath("/");
        // servletContextHandler1.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        //servletContextHandler1.addServlet(DefaultServlet.class, "/*");
        ///servletContextHandler1.setResourceBase(new PathResource(new File(STATIC_CONTENT_FOLDER)).toString());
        // StaticContentServlet
        // ServletHolder staticContent = new ServletHolder( DefaultServlet.class);
        // staticContent.setInitParameter("resourceBase", STATIC_CONTENT_FOLDER);
//webAppContext.addServlet(staticContent,"/*");
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/static");

        //ServletContextHandler servletContextHandler = new ServletContextHandler();
        // servletContextHandler.setContextPath("/static");
        //servletContextHandler.setResourceBase("E:/proShopDir");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(RESOURCE_BASE_IMG);
        contextHandler.setHandler(resourceHandler);

        // this configures jetty to require HTTPS for all requests
        Constraint constraint = new Constraint();
        constraint.setDataConstraint(Constraint.DC_CONFIDENTIAL);
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);
        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setConstraintMappings(Collections.singletonList(mapping));

        //servletContextHandler.setHandler(resourceHandler);
        //webAppContext.setHandler(securityHandler);
        //webAppContext.setSecurityHandler(securityHandler);
        //HandlerList handlerList = new HandlerList();
        //handlerList.addHandler(securityHandler);
        //handlerList.addHandler( repoHandler );
        // handlerList.addHandler(new DefaultHandler());
        /**
         * RewriteHandler rewriteHandler = new RewriteHandler();
         * rewriteHandler.setRewriteRequestURI(true);
         * rewriteHandler.setRewritePathInfo(false);
         *
         * ForwardedSchemeHeaderRule forwardedHttps = new
         * ForwardedSchemeHeaderRule();
         * forwardedHttps.setHeader("X-Forwarded-Scheme");
         * forwardedHttps.setHeaderValue("https");
         * forwardedHttps.setScheme("https");
         *
         * rewriteHandler.setRules(new Rule[]{forwardedHttps});
         */
        securityHandler.setHandler(webAppContext);
        //rewriteHandler.setHandler(securityHandler);

        //contexts.setHandlers(new Handler[]{securityHandler, contextHandler});
        contexts.setHandlers(new Handler[]{webAppContext, contextHandler});

        jettyServer.setHandler(contexts);

        jettyServer.setConnectors();

        Runnable runner = new Runnable() {
            @Override
            public void run() {
                ServerRunner serverRunner = new ServerRunner(jettyServer);
            }
        };
        EventQueue.invokeLater(runner);

    }

}
