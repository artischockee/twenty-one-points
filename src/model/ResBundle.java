package model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ResBundle {
    public static ResourceBundle getBundle(String baseName, Locale locale) {
        int indexOfSlash = baseName.indexOf('/');

        String pathname = baseName.substring(0, indexOfSlash);
        String bundleName = baseName.substring(indexOfSlash + 1, baseName.length());

        URL[] urls = new URL[0];
        try {
            urls = new URL[] { new File(pathname).toURI().toURL() };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader classLoader = new URLClassLoader(urls);

        return ResourceBundle.getBundle(bundleName, locale, classLoader);
    }
}
