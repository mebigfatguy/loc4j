/*
 * loc4j - a source code line counting ant task
 * Copyright 2016-2019 MeBigFatGuy.com
 * Copyright 2016-2019 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.loc4j;

import java.io.BufferedInputStream;
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
import org.objectweb.asm.ClassReader;

public class Loc4jTask extends Task {

    private String linesProperty;
    private String methodsProperty;
    private String classesProperty;
    private Path classpath;
    private Counts counts;

    public void setLinesProperty(String linesProp) {
        linesProperty = linesProp;
    }

    public void setMethodsProperty(String methodsProp) {
        methodsProperty = methodsProp;
    }

    public void setClassesProperty(String classesProp) {
        classesProperty = classesProp;
    }

    public void addConfiguredClasspath(final Path cp) {
        classpath = cp;
    }

    @Override
    public void execute() {

        if (classpath == null) {
            throw new BuildException("'classpath' attribute not set");
        }

        counts = new Counts();

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

            if (linesProperty != null) {
                p.setProperty(linesProperty, String.valueOf(counts.getLineCounts()));
            }
            if (methodsProperty != null) {
                p.setProperty(methodsProperty, String.valueOf(counts.getMethodCounts()));
            }
            if (classesProperty != null) {
                p.setProperty(classesProperty, String.valueOf(counts.getClassCounts()));
            }
        } catch (IOException e) {
            throw new BuildException("Failed counting lines", e);
        }
    }

    private void processDirectory(Resource r) throws IOException {
        try {
            Files.list(Paths.get(r.getName())).filter(c -> c.endsWith(".class")).forEach(p -> {
                try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(p))) {
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

                if (entry.getName().endsWith(".class")) {
                    processClass(jis);
                }
                entry = jis.getNextJarEntry();
            }
        }
    }

    private void processClass(InputStream is) throws IOException {

        ClassReader cr = new ClassReader(is);
        LocClassVisitor cv = new LocClassVisitor(counts);
        cr.accept(cv, 0);
    }

    // for testing only
    public static void main(String[] args) {

        Project p = new Project();
        Loc4jTask l = new Loc4jTask();
        l.setProject(p);

        Path cp = new Path(p, "/home/dave/dev/loc4j/target/loc4j-0.1.0-SNAPSHOT.jar");

        l.addConfiguredClasspath(cp);
        l.setLinesProperty("lines");
        l.setMethodsProperty("methods");
        l.setClassesProperty("classes");

        l.execute();

        System.out.println("Lines = " + p.getProperty("lines"));
        System.out.println("Methods = " + p.getProperty("methods"));
        System.out.println("Classes = " + p.getProperty("classes"));
    }

}
