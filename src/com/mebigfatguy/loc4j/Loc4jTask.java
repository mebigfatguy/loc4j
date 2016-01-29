package com.mebigfatguy.loc4j;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

public class Loc4jTask extends Task {

    private String linesProperty;
    private String methodsProperty;
    private String classesProperty;
    private String packagesProperty;
    private Path classpath;

    public void setLinesProperty(String linesProp) {
        linesProperty = linesProp;
    }

    public void setMethodsProperty(String methodsProp) {
        methodsProperty = methodsProp;
    }

    public void setClassesProperty(String classesProp) {
        classesProperty = classesProp;
    }

    public void setPackagesProperty(String packagesProp) {
        packagesProperty = packagesProp;
    }

    public void addConfiguredClasspath(final Path cp) {
        classpath = cp;
    }

    @Override
    public void execute() {

    }

}
