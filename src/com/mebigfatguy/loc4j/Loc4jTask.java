package com.mebigfatguy.loc4j;

import java.io.BufferedInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Resource;

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

        if (classpath == null) {
            throw new BuildException("'classpath' attribute not set");
        }

        try {
            Project p = getProject();
            Iterator<Resource> it = classpath.iterator();
            while (it.hasNext()) {
                Resource r = it.next();

                if (r.isDirectory()) {
                    processDirectory(r);
                } else {
                    processJar(r);
                }
            }
        } catch (IOException e) {
            throw new BuildException("Failed counting lines", e);
        }
    }

    private void processDirectory(Resource r) throws IOException {
        try {
            Files.list(Paths.get(r.getName())).filter(c -> c.endsWith(".class")).forEach(p -> {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(p.toFile()))) {
                    processClass(bis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw (IOException) cause;

            }
            throw new IOException("Failed to process file: " + r.getName(), e);
        }
    }

    private void processJar(Resource r) throws IOException {

        try (JarInputStream jis = new JarInputStream(r.getInputStream())) {
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {

                processClass(jis);
                entry = jis.getNextJarEntry();
            }
        }
    }

    private void processClass(InputStream is) {

    }

    // for testing only
    public static void main(String[] args) {

        Project p = new Project();
        Loc4jTask l = new Loc4jTask();
        l.setProject(p);

        Path cp = new Path(p, "/home/dave/dev/loc4j/target/loc4j-0.1.0.jar");

        l.addConfiguredClasspath(cp);
        l.setLinesProperty("lines");

        l.execute();
    }

}
