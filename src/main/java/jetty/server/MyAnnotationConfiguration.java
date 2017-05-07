/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetty.server;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jetty.annotations.AbstractDiscoverableAnnotationHandler;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationDecorator;
import org.eclipse.jetty.annotations.AnnotationParser;
//import org.eclipse.jetty.annotations.ClassNameResolver;
import org.eclipse.jetty.annotations.WebFilterAnnotationHandler;
import org.eclipse.jetty.annotations.WebListenerAnnotationHandler;
import org.eclipse.jetty.annotations.WebServletAnnotationHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
//import org.jboss.logging.Logger;

/**
 *
 * @author hope
 */
public class MyAnnotationConfiguration extends AnnotationConfiguration {

    /**
    //private static final Logger LOG = Log.getLogger(AnnotationConfiguration.class);
    // private static final Logger log = Logger.getLogger(MyAnnotationConfiguration.class);
    @Override
    public void configure(WebAppContext context) throws Exception {
        boolean metadataComplete = context.getMetaData().isMetaDataComplete();
        context.getObjectFactory().addDecorator(new AnnotationDecorator(context));

        //Even if metadata is complete, we still need to scan for ServletContainerInitializers - if there are any
        AnnotationParser parser = null;
        if (!metadataComplete) {
            //If metadata isn't complete, if this is a servlet 3 webapp or isConfigDiscovered is true, we need to search for annotations
            if (context.getServletContext().getEffectiveMajorVersion() >= 3 || context.isConfigurationDiscovered()) {

                _discoverableAnnotationHandlers.add(new WebServletAnnotationHandler(context));
                _discoverableAnnotationHandlers.add(new WebFilterAnnotationHandler(context));
                _discoverableAnnotationHandlers.add(new WebListenerAnnotationHandler(context));
            }
        }

        parser = new AnnotationParser() {
            @Override
            public void parse(Set<? extends AnnotationParser.Handler> handlers, Resource r, ClassNameResolver resolver ) throws Exception {
                if (r.isDirectory()) {
                    super.parse(handlers, r, resolver );
                }
                //else {
                //   super.parse(handlers, r.getURI(), resolver);
                // }
            }
        };
        getParse(context, parser);

        // for (AbstractDiscoverableAnnotationHandler h:_discoverableAnnotationHandlers){
        //                           context.getMetaData().addDiscoveredAnnotations(((AbstractDiscoverableAnnotationHandler)h).getAnnotationList());
        //                            }  
    }

    private void getParse(final WebAppContext context, AnnotationParser parser) throws Exception {
        List<Resource> _resources = getResources(getClass().getClassLoader());
        for (Resource _resource : _resources) {
            if (_resource == null) {
                return;
            } else {
                System.out.println("Resource :" + _resource.getName());

            }
            //parser.clearHandlers();
// parser = null;
            //for (AbstractDiscoverableAnnotationHandler h : _discoverableAnnotationHandlers) {
            //    if (h instanceof AbstractDiscoverableAnnotationHandler) {
            //       ((AbstractDiscoverableAnnotationHandler) h).setResource(null); //
            //   }
            //}
            //parser.registerHandlers(_discoverableAnnotationHandlers);
            Set<AbstractDiscoverableAnnotationHandler> set_discoverableAnnotationHandlers = new HashSet<AbstractDiscoverableAnnotationHandler>(_discoverableAnnotationHandlers);

            // List<String> _classNames = new ArrayList<String>();
            // _classNames.add("javax.servlet.annotation.WebServlet");
            // _classNames.add("javax.servlet.annotation.WebFilter");
            // _classNames.add("javax.servlet.annotation.WebListener");
            //   parser.parse(set_discoverableAnnotationHandlers, _classNames, _webAppClassNameResolver);
            // parser.registerHandler(_classInheritanceHandler);
            // parser.registerHandlers(_containerInitializerAnnotationHandlers);
            parser.parse(set_discoverableAnnotationHandlers, _resource , new ClassNameResolver() {
                @Override
                public boolean isExcluded(String name) {
                    if (context.isSystemClass(name)) {
                        return true;
                    }
                    if (context.isServerClass(name)) {
                        return false;
                    }
                    return false;
                }

                @Override
                public boolean shouldOverride(String name) {
                    //looking at webapp classpath, found already-parsed class of same name - did it come from system or duplicate in webapp?
                    if (context.isParentLoaderPriority()) {
                        return false;
                    }
                    return true;
                }
            });

        }
    }

    private List<Resource> getResources(ClassLoader aLoader) throws IOException {
        if (aLoader instanceof URLClassLoader) {
            List<Resource> _result = new ArrayList<>();
            URL[] _urls = ((URLClassLoader) aLoader).getURLs();
            for (URL _url : _urls) {
                _result.add(Resource.newResource(_url));
            }

            return _result;
        }
        return Collections.emptyList();
    }
*/
}
