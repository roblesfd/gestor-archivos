package org.fernandodev.path_visitors;

import java.nio.file.Path;

public class OperationsTreeBase {
    public static void log(Object... objects){
        for(Object object: objects){
            System.out.println(object);
        }
    }
}
