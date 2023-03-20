/*
 * The MIT License
 *
 * Copyright (c) 2023 Randall Scharpf
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

import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;

/**
 * Loads an instance of <code>OnOff</code> and an instance of <code>Delayer</code>
 * from a file. If the manifest of the JAR file indicates that the same class should
 * be used for both the instances, a single instance will be created and used for
 * both purposes. Instances of this class are immutable, being created from JAR files
 * and holding the data processed from the extension.
 * @version 1.0
 * @since 1.0
 */
public class LoadedExtension {
    /**
     * Loads an extension from a JAR file. First, the manifest is searched for a valid
     * <code>Extension-Name</code>, <code>OnOff-Class</code>, and <code>Delayer-Class</code>.
     * Then the JAR file is loaded into a class loader, and the targeted classes are
     * loaded. Finally, instances of the classes are created and stored.
     * <br>
     * Security note: this constructor loads code contained in a file, so the source
     * of any file passed to this constructor must be trusted, as an untrusted party
     * who produces a file that is passed to this method is able to run arbitrary
     * code in the JVM of the caller.
     * @version 1.0
     * @since 1.0
     * @param jarFile the file to load
     * @throws IOException if the JAR is missing manifest attributes or unreadable
     * @throws ReflectiveOperationException if it is impossible to instantiate instances
     * of extension implementations due to type issues, class name/version mismatches,
     * or incompatible class definitions
     */
    public LoadedExtension(File jarFile) throws IOException, ReflectiveOperationException {
        JarInputStream is = new JarInputStream(new FileInputStream(jarFile));
        extName = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("Extension-Name"));
        String onOffClass = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("OnOff-Class"));
        String delayClass = (String) is.getManifest().getMainAttributes().get(new Attributes.Name("Delayer-Class"));
        if (extName == null || onOffClass == null || delayClass == null) {
            throw new IOException("JAR file is not an extension: missing manifest attribute(s)!");
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader());
        try {
            onOff = (OnOff) loader.loadClass(onOffClass).newInstance();
            if (onOffClass.equals(delayClass)) {
                delayer = (Delayer) onOff;
            } else {
                delayer = (Delayer) loader.loadClass(delayClass).newInstance();
            }
        } catch (UnsupportedClassVersionError | ClassCastException ex) {
            throw new ReflectiveOperationException(ex);
        }
    }
    /**
     * The human-readable name of the extension.
     * @version 1.0
     * @since 1.0
     */
    public final String extName;
    /**
     * An instance of the state-setting facility provided by the extension.
     * @version 1.0
     * @since 1.0
     */
    public final OnOff onOff;
    /**
     * An instance of the time-delaying facility provided by the extension.
     * @version 1.0
     * @since 1.0
     */
    public final Delayer delayer;
}
