/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.jetty.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    //private final String PROD_KEYSTORE_PATH = "/webapp/keystore";
    private String keyStorePath;

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
        //http.setHost(keyStorePath);

        http.setPort(8080);
        http.setIdleTimeout(200000);//TODO 30000
        return http;
    }

    //SSL Context Factory for HTTPS and HTTP/2
    public SslContextFactory getSslContextFactory() {
        /**
         * final String tempDirPath =
         * System.getProperty("com.hopecee.jetty.main.TEMP_DIR_PATH"); try { if
         * (null != appContext.getOperationalMode()) { switch
         * (appContext.getOperationalMode()) { case PROD: { keyStorePath =
         * tempDirPath + PROD_KEYSTORE_PATH; break; } case PROD_EXE: {
         * keyStorePath = tempDirPath + PROD_KEYSTORE_PATH; break; } default:
         * keyStorePath = DEV_KEYSTORE_PATH; break; } } } catch (IOException ex)
         * { Logger.getLogger(ManyConnectors.class.getName()).log(Level.SEVERE,
         * null, ex); }
         */
        keyStorePath = DEV_KEYSTORE_PATH;

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keyStorePath);
        sslContextFactory.setKeyStorePassword("rising");
        //sslContextFactory.setKeyManagerPassword("rising111");
        sslContextFactory.setProtocol("TLSv1");
        // sslContextFactory.addExcludeCipherSuites(".*RC4.*");
        // sslContextFactory.addExcludeCipherSuites("TLS_DHE_RSA.*");
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
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
        http2Connector.setIdleTimeout(200000);//TODO 30000
        return http2Connector;
    }

    // All connectors
    public Connector[] getConnectors(Server server) {
        Connector[] connectors = new Connector[]{getHttpConnector(server), getHTTP2Connector(server)
        };
        return connectors;
    }
}
