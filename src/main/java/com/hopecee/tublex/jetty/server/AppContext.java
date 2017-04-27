/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.jetty.server;

//import java.io.File;
import java.net.URI;
//import javax.mail.Service;
//import org.eclipse.jetty.npn.NextProtoNego;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
//import org.eclipse.jetty.webapp.TagLibConfiguration;
//import org.mortbay.jetty.webapp.WebAppContext;
//import org.mortbay.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
//import org.springframework.stereotype.Service;

/**
 *
 * @author hope
 */
public class AppContext {

    private static String contextPath = "/";
    private static String descriptor = "WEB-INF/web.xml";
    private static final String RESOURCE_BASE1 = "src/main/webapp";
    private static String resourceBase2 = "src/main/java";
    private static String resourceBase3 = "target";
    private static final String RESOURCE_BASE_IMG = "E:\\proShopDir";
    private static String warDir = "target/proShopNew-1.0-SNAPSHOT";
    private static String evnXml = "src/main/webapp/WEB-INF/jetty-env.xml";
    private static String resourceAlias1 = "/WEB-INF/classes/";
    private static String resourceAlias2 = "/classes/";
    Configuration[] jettyConfs = new Configuration[]{
        new WebInfConfiguration(),
        new WebXmlConfiguration(),
        new MetaInfConfiguration(),
        new FragmentConfiguration(),
        new EnvConfiguration(),
        new PlusConfiguration(),
        // new AnnotationConfiguration(), 
        new MyAnnotationConfiguration(),
        new JettyWebXmlConfiguration()
            
             
                    
    };

    public WebAppContext buildWebAppContext() throws Exception {

        //NextProtoNego.debug = true;
//AsyncConnection AsyncConnection = new AsyncConnection();
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setConfigurations(jettyConfs);
        //String webappPath = AppContext.class.getClassLoader().getResource("classpath*:/").toExternalForm();
        webAppContext.setDescriptor(descriptor);


        //Place to get Images.
        // ResourceHandler publicDocsImg = new ResourceHandler();
        // publicDocsImg.setResourceBase("C:/images");

        // webAppContext.setWar(warDir);
        String[] res1 = new String[]{warDir};
        String[] res = new String[]{RESOURCE_BASE1, RESOURCE_BASE_IMG
        //, resourceBase2, resourceBase3,"."
        };

        //File dir = File.createTempFile("dir", null);
        //dir.delete();
        //dir.mkdir();
        //dir.deleteOnExit();

        //File webinf = new File(dir, "WEB-INF");
        //webinf.mkdir();

        //File classes = new File(dir, "classes");
        //classes.mkdir();

        //File someclass = new File(classes, "SomeClass.class");
        //someclass.createNewFile();

        //System.out.println("ZZZZZZZZZZZZZ" + webinf.getAbsolutePath());
        //System.out.println("PPPPPPPPPPPP" + System.getProperties());

        //new ClassPathResource("webapp").getURI().toString();

        if (res != null) {
            webAppContext.setBaseResource(new ResourceCollection(res));
         } else {
            // running in a jar
            final URI uri = AppContext.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            webAppContext.setBaseResource(Resource.newResource("jar:" + uri + "!/"));
         }
        webAppContext.setResourceAlias(resourceAlias1, resourceAlias2);
        
        //webAppContext.setClassLoader(this.getClass().getClassLoader());
        webAppContext.setParentLoaderPriority(true);
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        //Map<String, String> initParam = new HashMap<>();
        //initParam.put("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        return webAppContext;
    }
}
