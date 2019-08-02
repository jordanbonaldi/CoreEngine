package net.neferett.tests;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourcesGetter {

    public static String getConfigPath(String path) {
        URL url = CorePluginsTests.class.getClassLoader().getResource(path);

        try {
            assert url != null;
            File file = new File(url.toURI());

            return file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
