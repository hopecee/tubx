/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import org.eclipse.jetty.alpn.ALPN;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NegotiatingServerConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.ResourceCollection;
//import org.eclipse.jetty.spdy.server.NPNServerConnectionFactory;
//import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnectionFactory;
//import org.eclipse.jetty.spdy.server.http.ReferrerPushStrategy;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 *
 * @author hope
 */
public class ManyConnectors {

    private final String DEV_KEYSTORE_PATH = "src/main/resources/keystore";
    private final String PROD_KEYSTORE_PATH = "/webapp/keystore";
    private final String PASS = "rising";
    private String keyStorePath;

    AppContext appContext = new AppContext();

    public ManyConnectors() {
        ALPN.debug = true;
    }

    /**
     * many connectors
     *
     * @return
     */
    // HTTP Configuration
    public HttpConfiguration getHttpConfig() {
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(8443);
        httpConfig.setOutputBufferSize(32768);
        return httpConfig;
    }

    // HTTP Connector
    public ServerConnector getHttpConnector(Server server) {
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(getHttpConfig()), new HTTP2CServerConnectionFactory(getHttpConfig()));
        http.setPort(8080);
        http.setIdleTimeout(200000);//TODO 30000
        return http;
    }

    //SSL Context Factory for HTTPS and HTTP/2
    public SslContextFactory getSslContextFactory() {
        final String TEMP_DIR_PATH = System.getProperty("com.hopecee.tublex.jetty.main.TEMP_DIR_PATH");

        try {
            if (null != appContext.getOperationalMode()) {
                switch (appContext.getOperationalMode()) {
                    case PROD:
                        keyStorePath = TEMP_DIR_PATH + PROD_KEYSTORE_PATH;
                        break;
                    case PROD_EXE:
                        keyStorePath = TEMP_DIR_PATH + PROD_KEYSTORE_PATH;
                        break;
                    case DEV:
                        keyStorePath = DEV_KEYSTORE_PATH;
                        break;
                    default:
                        throw new FileNotFoundException("Unable to configure WebAppContext base resource undefined");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ManyConnectors.class.getName()).log(Level.SEVERE, null, ex);
        }

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keyStorePath);
        sslContextFactory.setKeyStorePassword(PASS);
        sslContextFactory.setKeyManagerPassword(PASS);
        sslContextFactory.setProtocol("TLSv1.2");
        // The mandatory HTTP/2 cipher.
        //sslContextFactory.setIncludeCipherSuites("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        // sslContextFactory.setIncludeCipherSuites("TLS_ECDHE_RSA.*");
        //sslContextFactory.setIncludeCipherSuites("TLS_DHE_RSA.*");

        //sslContextFactory.addExcludeCipherSuites(".*NULL.*");
        //sslContextFactory.addExcludeCipherSuites(".*RC4.*");
        //sslContextFactory.addExcludeCipherSuites(".*MD5.*");
        //sslContextFactory.addExcludeCipherSuites(".*DES.*");
        //sslContextFactory.addExcludeCipherSuites(".*DSS.*");
        //sslContextFactory.addExcludeProtocols("SSL", "SSLv2", "SSLv2Hello", "SSLv3");
        //sslContextFactory.setRenegotiationAllowed(false);
        //sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
        sslContextFactory.setUseCipherSuitesOrder(true);

        return sslContextFactory;
    }

    // HTTPS Configuration
    public HttpConfiguration getHttpsConfig() {
        HttpConfiguration config = new HttpConfiguration(getHttpConfig());
        config.addCustomizer(new SecureRequestCustomizer());
        return config;
    }

    //HTTP/2 Connection Factory
    public HTTP2ServerConnectionFactory getHTTP2Factory() {
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(getHttpsConfig());
        return h2;
    }

    // ALPN Connection factory with HTTP as default protocol 
    public ALPNServerConnectionFactory getALPNFactory(Server server) {
        NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
        ALPNServerConnectionFactory factory = new ALPNServerConnectionFactory();
        factory.setDefaultProtocol(getHttpConnector(server).getDefaultProtocol());
        return factory;
    }

    // SSL Connection factory with ALPN as next protocol 
    public SslConnectionFactory getSslFactory(Server server) {
        SslConnectionFactory factory = new SslConnectionFactory(getSslContextFactory(), getALPNFactory(server).getProtocol());
        return factory;
    }

    // HTTP/2 Connector
    public ServerConnector getHTTP2Connector(Server server) {
        ServerConnector http2Connector = new ServerConnector(
                server,
                getSslFactory(server),
                getALPNFactory(server),
                getHTTP2Factory(),
                new HttpConnectionFactory(getHttpConfig()));
        http2Connector.setPort(8443);
        http2Connector.setIdleTimeout(2000000);//TODO 30000

        return http2Connector;
    }

    // All connectors
    public Connector[] getConnectors(Server server) {
        Connector[] connectors = new Connector[]{getHttpConnector(server), getHTTP2Connector(server)
        };
        return connectors;
    }
}
