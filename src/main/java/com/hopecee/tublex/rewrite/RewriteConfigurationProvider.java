
package com.hopecee.tublex.rewrite;

import javax.servlet.ServletContext;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 *
 * @author hope
 */
@RewriteConfiguration
public class RewriteConfigurationProvider extends HttpConfigurationProvider {

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public Configuration getConfiguration(final ServletContext t) {
        return ConfigurationBuilder.begin()
                .addRule(Join.path("/").to("/public/index.xhtml"))
                //.when(Direction.isInbound().and(Path.matches("/some/{page}/")))
                //.perform(Forward.to("/new-{page}/"))
                ;
    }

}
