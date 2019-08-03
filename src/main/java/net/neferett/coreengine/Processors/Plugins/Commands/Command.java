package net.neferett.coreengine.Processors.Plugins.Commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name() default "";

    int argsLength() default -1;

    int minLength() default 0;

    String desc() default "";

    String help() default "";

    boolean activated() default true;

}
