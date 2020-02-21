package me.aberdeener.commandexecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] commandNames();
    boolean playerOnly();
    String permission();
    String usage();

}
