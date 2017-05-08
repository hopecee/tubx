//
//  ========================================================================
//  Copyright (c) 1995-2015 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//
package jetty.bootstrap;

//import jetty.bootstrap.protocolhandlers.jarjar_1.JarJarURLConnection;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import jetty.main.ServerMain;
//import jetty.bootstrap.protocolhandlers.jarjar.MarURLConnection;

public class JettyBootstrap {

    //public final static String LIB_DIR = "WEB-INF/lib/";
    // public final static String ALPN_VERSION = "8.1.5.v20150921";
    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        System.out.println("PPPPPPPPPPPP : " + args.length);

        //String goArg = "next";
        if (args.length > 0 && "next".equals(args[0])) {
            // if (true) {
            System.out.println("PPPPPPPPPPPP 2: " + args[0]);
            try {
                URL warLocation = JettyBootstrap.class.getProtectionDomain().getCodeSource().getLocation();
                if (warLocation == null) {
                    throw new IOException("JettyBootstrap not discoverable");
                }

                /*
URL libUrl = new URL("jar:" + warLocation.toURI().toURL() + "!/" + "WEB-INF/jetty-server/"
            );
            URL libUrl1 = new URL("jar:" + warLocation.toURI().toURL() + "!/" + "WEB-INF/lib/javax/inject-1.jar!/"
            );

           // new URLClassLoader(new URL[]{new URL("jar:jarjar:file:/C:/mylibs/Outer.jar^/Inner.jar!/")}
           // ).loadClass("tested.robots.Ahead");
                 */
                // ClassLoader ucl = new ServerClassLoader(warLocation);
                ClassLoader jb = JettyBootstrap.class.getClassLoader();
                //System.out.println("aClass.getName() = " + JettyBootstrap.class.getClassLoader());
                // System.out.println("aClass.getName() = " + JettyBootstrap.class.getClassLoader().getParent());
                // System.out.println("aClass.getName() = " + JettyBootstrap.class.getClassLoader().getResource("org/eclipse/jetty/alpn/ALPN.class").toString());

                //ClassLoader ucl = new ServerClassLoader(warLocation, jb);
                //ClassLoader jcl = new JarClassLoader(ucl);
                //System.out.println("aClass.getName() = " + JettyBootstrap.class.getClassLoader());
                // System.out.println("aClass.getName() = " + jb.loadClass("jetty.bootstrap.JettyBootstrap").getName());
                // System.out.println("aClass.getName() = " + jb.loadClass("javax.servlet.Filter").getName());
                //System.out.println("aClass.getName() = " + jb.loadClass("org.eclipse.jetty.util.component.Container$Listener").getName());
                //System.out.println("aClass.getName() = " + jb.loadClass("jetty.main.ServerMain").getName());
                //Thread.currentThread().setContextClassLoader(ucl);
                // Thread.currentThread().setContextClassLoader(jcl);
                File warFile = new File(warLocation.toURI());
                System.out.println("warFile.toPath() : " + warFile.toPath().toRealPath().toString());

                System.setProperty("com.hopecee.tublex.jetty.main.LOCATION", warFile.toPath().toRealPath().toString());
                /**
                  Class<?> mainClass = Class.forName("jetty.main.ServerMain",
                  false, jb); Method mainMethod = mainClass.getMethod("main",
                  args.getClass());
                 
                  mainMethod.invoke(mainClass, new Object[]{args});
                 */

                ServerMain.main(args);

            } catch (IOException | URISyntaxException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException t) {
                t.printStackTrace(System.out);
                System.exit(-1);
            }

        } else {

            String file = JettyBootstrap.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            int lstIndexOf = file.lastIndexOf("/");
            String jar = file.substring(lstIndexOf + 1);
            System.out.println("Starting Server ... " + jar);

            ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "start",
                    "java",
                    "-Xms512m", "-Xmx1024m",
                    "-Xbootclasspath/p:C:/Docume~1/hope/.m2/repository/org/mortbay/jetty/alpn/alpn-boot/8.1.5.v20150921/alpn-boot-8.1.5.v20150921.jar",
                    // "-Xbootclasspath/p:C:/alpn-boot-8.1.5.v20150921.jar",
                    "-jar", jar, "next");
            System.out.println("Starting Server ...");
            Process p = pb.start();
            System.out.println("Starting Server ...1");

        }
    }
}
