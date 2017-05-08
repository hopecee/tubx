/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/**
 *
 * @author hope
 */
public class AppContext {

   // private static final Logger LOGGER = LogManager.getLogger(AppContext.class);

    private static final String CONTEXT_PATH = "/";

    private static final String RESOURCE_BASE1 = "src/main/webapp";
    private static final String RESOURCE_BASE3 = "target";
    private static final String RESOURCE_BASE_IMG = "E:\\proShopDir";
    private static final String RESOURCE_ALIAS1 = "/WEB-INF/classes/";
    private static final String RESOURCE_ALIAS2 = "/classes/";

    private static final String WAR_LOCATION_PROPERTY = System.getProperty("com.hopecee.tublex.jetty.main.LOCATION");
    private static final String TEMP_DIR_PATH = "com.hopecee.tublex.jetty.main.TEMP_DIR_PATH";
    public Path warPath;
    private File jettyTempDir;
    public String tempDirPath;
    private final DirDeleter dirDeleter = new DirDeleter();

    Configuration[] jettyConfs = new Configuration[]{
        new WebInfConfiguration(),
        new WebXmlConfiguration(),
        new MetaInfConfiguration(),
        new FragmentConfiguration(),
        new EnvConfiguration(),
        new PlusConfiguration(),
         new AnnotationConfiguration(), 
        //new MyAnnotationConfiguration(),
        new JettyWebXmlConfiguration()
    };

    public enum OperationalMode {
        DEV,
        PROD,
        PROD_EXE
    }

    //public WebAppContext buildWebAppContext(JettyServer jettyServer) throws Exception {
   public WebAppContext buildWebAppContext(Server server) throws Exception {
//NextProtoNego.debug = true;
//AsyncConnection AsyncConnection = new AsyncConnection();
        WebAppContext webAppContext = new WebAppContext();
        //webAppContext.setConfigurations(jettyConfs);
        //String webappPath = AppContext.class.getClassLoader().getResource("classpath*:/").toExternalForm();
        //webAppContext.setDescriptor(descriptor);

        webAppContext.setContextPath(CONTEXT_PATH);

        switch (getOperationalMode()) {

            //C:\Users\Hope\AppData\Local\Temp\embedded-jetty-jsp
            /**
             * case PROD_EXE: getScratchDirExe();
             *
             * / *JNDI AND OTHER SERVER SPECIFIC BUSINESS* /
             * enableAnnotationScanning(jettyServer.getServer());
             *
             * //webAppContext.setTempDirectory(jettyTempDir);
             * webAppContext.setWar(warPath.toString()); // res = new
             * String[]{RESOURCE_IMG};//"../tuShopWar-webapp/target/tuShopWar-webapp-1.0-SNAPSHOT","../tuShopWar-webapp/target/webapp"
             * // webAppContext.setBaseResource(new ResourceCollection(res));
             *
             * //webAppContext.setDescriptor(PROD_DESCRIPTOR); break; *
             */
            case PROD:
                getScratchDir();
                /*JNDI AND OTHER SERVER SPECIFIC BUSINESS*/
                enableAnnotationScanning(server.getServer());

                //webAppContext.setAttribute("org.eclipse.jetty.webapp.basetempdir", "E:/tmp/foo");
                webAppContext.setTempDirectory(jettyTempDir);
                webAppContext.setWar(warPath.toString());
                //webAppContext.setTempDirectory(new File("E:"));

                // System.out.println("PPPPPPPPPPPP 11: " + jettyTempDir);
                // System.out.println("PPPPPPPPPPPP 0: " + webAppContext.getTempDirectory());
                // System.out.println("PPPPPPPPPPPP 0: " + webAppContext.getWar());
                break;

            case DEV:
                webAppContext.setConfigurations(jettyConfs);
                String[] res = new String[]{RESOURCE_BASE1, RESOURCE_BASE3 //, RESOURCE_BASE_IMG
            };
                webAppContext.setBaseResource(new ResourceCollection(res));
                webAppContext.setResourceAlias(RESOURCE_ALIAS1, RESOURCE_ALIAS2);
                break;
            default:
                throw new FileNotFoundException("Unable to configure WebAppContext base resource undefined");
        }

        //webAppContext.setParentLoaderPriority(true);
        // webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        //Map<String, String> initParam = new HashMap<>();
        //initParam.put("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        return webAppContext;
    }

    public OperationalMode getOperationalMode() throws IOException {
        String warLocation = WAR_LOCATION_PROPERTY;

        if (warLocation != null && warLocation.toLowerCase().endsWith(".exe")) {
            return OperationalMode.PROD_EXE;
        } else if (warLocation != null) {
            return OperationalMode.PROD;
        } else {
            return OperationalMode.DEV;
        }

    }

    /**
     * Create the directory in which Jetty will store the generated java classes
     * when source is war.
     *
     * @throws IOException if I/O fails
     */
    public void getScratchDir() throws IOException {
        String warLocation = WAR_LOCATION_PROPERTY;
        Path myWarPath = new File(warLocation).toPath().toRealPath();
        if (Files.exists(myWarPath) && !Files.isDirectory(myWarPath)) {
            this.warPath = myWarPath;
        }
        
        final File tempDir, scratchDir;
        tempDir = new File(System.getProperty("java.io.tmpdir"));
        scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");
        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException("Unable to create scratch directory: "//$NON-NLS-1$
                        + scratchDir);
            }
        }
        this.jettyTempDir = scratchDir;

        tempDirPath = Paths.get(scratchDir.toString()).toString();
        System.setProperty(TEMP_DIR_PATH, tempDirPath);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    dirDeleter.deleteRecursive(Paths.get(scratchDir.getAbsolutePath()));
                    //deleteRecursive(Paths.get(scratchDir.getAbsolutePath()));
                   // LOGGER.debug("Temporary WAR directory deleted");
                } catch (IOException e) {
                    //LOGGER.warn("Problems with deleting temporary directory", e);
                }
            }
        });

    }

    private void enableAnnotationScanning(Server server) {
        Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(server);
        // this line sets up the server to pick up JNDI related configurations in override-web.xml. This is a direct copy off the documentation.
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
                "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");
    }
}
