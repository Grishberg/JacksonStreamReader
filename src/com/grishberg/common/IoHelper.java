package com.grishberg.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.lang.*;

public class IoHelper {
    public static String pkgToPath(String pkg) {
        pkg = pkg.replace('.', '/');
        return pkg;
    }

    public static String getCwd(String pkg) {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString() + "/out/" + pkgToPath(pkg);
    }

    public static String getCwd() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString() + "/out/";
    }
    public static boolean saveFile(String dir, String fn, String body) {
        checkFolder(dir);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(dir + "/" +fn));
            writer.write(body);
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static String openFile(String wd, String fn) {
        StringBuilder result = new StringBuilder();
        String fileName = wd +"/"+fn;
        File file = new File(fileName);
        if(!file.exists()) return null;
        try {
            FileReader fr1 = new FileReader(file);
            BufferedReader in1 = new BufferedReader(fr1);
            String line;
            while ((line = in1.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return result.toString();
    }

    public static boolean checkFolder(String path) {
        File dirs = new File(path);
        if(!dirs.exists()) {
            try {
                dirs.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
