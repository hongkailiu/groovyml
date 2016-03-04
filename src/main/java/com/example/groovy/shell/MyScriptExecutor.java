package com.example.groovy.shell;

import groovy.lang.GroovyShell;
import groovy.lang.MissingPropertyException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hao on 6/25/2014.
 */
public class MyScriptExecutor {

    public static void main(String[] args) {
        try {
            while (true) {
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                aaa(args[0]);
                bbb();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private static void aaa(String filepath) {
        System.out.println("a");
        MyScriptExecutor executor = new MyScriptExecutor();
        //ClassLoader classLoader = MyScriptExecutor.class.getClassLoader();
        //File file = new File(classLoader.getResource("test.groovy").getFile());
        File file = new File(filepath);
        executor.execute(file);
        System.out.println("b");
    }

    private static void bbb() {
        PersonInJ p = new PersonInJ();
        p.write();
    }

    public void execute(File script) {
        println("[INFO]");
        println("[INFO] -----------------------------------------------------");
        println("[INFO] " + "Global Post Script");
        println("[INFO] -----------------------------------------------------");
        String ext = FilenameUtils.getExtension(script.getAbsolutePath());
        if ("groovy".equalsIgnoreCase(ext) || "gvy".equalsIgnoreCase(ext) || "gs"
            .equalsIgnoreCase(ext) || "gsh".equalsIgnoreCase(ext)) {
            executeGroovy(script);
        } else {
            println("[ERROR] Script type not supported: " + ext + " | " + script.getName());
        }
        println("[INFO] -----------------------------------------------------");
    }

    public void executeGroovy(File script) {
        GroovyShell shell = new GroovyShell(getParentClassLoader());
        try {
            String scriptContent = getScriptContent(script);
            shell.evaluate(scriptContent);
        } catch (MissingPropertyException e) {
            println("[ERROR] Failed to execute: " + script.getName() + ", " + e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            println("[ERROR] Failed to execute: " + script.getName() + ", " + e.getMessage());
        } finally {
            shell.resetLoadedClasses();
        }
    }

    private void println(String message) {
        System.out.println(message);
    }

    private String getScriptContent(File script) throws IOException {
        InputStream is = new FileInputStream(script);
        try {
            return IOUtils.toString(is);
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    private ClassLoader getParentClassLoader() {
        return this.getClass().getClassLoader();
    }

}
