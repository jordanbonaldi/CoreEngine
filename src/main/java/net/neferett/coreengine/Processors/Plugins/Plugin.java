package net.neferett.coreengine.Processors.Plugins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Policy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

    String name() default "";

    String configPath() default "";

    boolean activated() default true;

}
