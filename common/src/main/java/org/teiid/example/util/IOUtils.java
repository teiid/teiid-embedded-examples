/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class supply static method to operate file which exist under current folder
 *
 */
public class IOUtils {
    
    /**
     * Get file path from current directory
     * @param dir is base directory under current directory
     * @param name is the file name
     * @return
     */
    public static String findFilePath(String name){

            File baseDir = new File(System.getProperty("user.dir")); //$NON-NLS-1$
            File fileItem = find(baseDir, name);
            if(fileItem != null) {
                    return fileItem.getAbsolutePath();
            }
            return null;
    }
    
    /**
     * Find file from current directory
     * @param dir is base directory under current directory
     * @param name is the file name
     * @return
     */
    public static File findFile(String name){

            File baseDir = new File(System.getProperty("user.dir")); //$NON-NLS-1$
            return find(baseDir, name);
    }
    
    public static Properties findProperties(String name) throws IOException{
        
        File file = findFile(name);
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if(file != null) {
                input = new FileInputStream(file);
                prop.load(input);
                return prop;
            } else {
                input = IOUtils.class.getClassLoader().getResourceAsStream(name);
                prop.load(input);
                return prop;
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }
        
    }
    
    private static File find(File baseDir, String dir) {

        for(File file : baseDir.listFiles()) {
                if(file.getName().equals(dir)) {
                        return file;
                } else if(file.isDirectory()) {
                        File result = find(file, dir);
                        if(result != null && result.getName().equals(dir)) {
                                return result;
                        }
                }
        }

        return null;
    }
    
}
