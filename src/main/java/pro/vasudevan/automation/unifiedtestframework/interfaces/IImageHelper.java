package pro.vasudevan.automation.unifiedtestframework.interfaces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

/*
Created By: Vasudevan Sampath

 IImageHelper.java has image specific methods
 */
public interface IImageHelper {

    static boolean compareImage(String actualImageLink, String expectedImagePath) throws Exception {
        URL url = new URI(actualImageLink).toURL();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        url.openStream().transferTo(out);
        byte[] actualImageData =  new ByteArrayInputStream(out.toByteArray()).readAllBytes();
        byte[] expectedImageData = Files.readAllBytes(new File(expectedImagePath).toPath());
        return Arrays.equals(actualImageData, expectedImageData);
    }
}