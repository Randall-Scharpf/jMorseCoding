/*
 * The MIT License
 *
 * Copyright 2021 Randall.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.randallscharpf.java.jmorsecoding.gui;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;

public class JarLoader {
    public JarLoader(File jarFile) throws IOException, ReflectiveOperationException {
        JarInputStream is = new JarInputStream(new FileInputStream(jarFile));
        extName = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("Extension-Name"));
        String onOffClass = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("OnOff-Class"));
        String delayClass = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("Delayer-Class"));
        if (extName == null || onOffClass == null || delayClass == null) {
            throw new IOException("JAR file is not an extension: missing manifest attribute(s)!");
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader());
        onOff = (OnOff) loader.loadClass(onOffClass).newInstance();
        delayer = (Delayer) loader.loadClass(delayClass).newInstance();
    }
    
    public final String extName;
    public final OnOff onOff;
    public final Delayer delayer;
}
