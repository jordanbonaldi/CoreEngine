package net.neferett.coreengine.Processors.Plugins.Commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name() default "";

    int argsLength() default -1;

    String desc() default "";

    boolean activated() default true;

}
