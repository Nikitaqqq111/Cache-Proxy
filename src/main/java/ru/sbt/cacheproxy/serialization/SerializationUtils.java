package ru.sbt.cacheproxy.serialization;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Никита on 24.08.2016.
 */
public class SerializationUtils {

    public static void serialize(Serializable o, String file) throws SerializationException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file))) {
            out.writeObject(o);
        } catch (IOException e) {
            throw new SerializationException("IOException, caused by: out.writeObject(o), from: SerializationUtils", e);
        }
    }

    public static <T> T deserialize(String file) throws SerializationException {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {
            return (T) in.readObject();
        } catch (IOException e) {
            throw new SerializationException("IOException, caused by: in.readObject(), from: SerializationUtils");
        } catch (ClassNotFoundException e) {
            throw new SerializationException("ClassNotFoundException, in.readObject(), from: SerializationUtils");
        }
    }

    public static void zipFile(String file) throws SerializationException {
        String zipFileName = file.substring(0, file.lastIndexOf(".")) + ".zip";
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(new File(file))) {
            String fileName = file.substring(file.lastIndexOf(System.getProperty("file.separator")) + 1);
            ZipEntry e = new ZipEntry(fileName);
            zos.putNextEntry(e);
            int tmp = 0;
            while ((tmp = fis.read()) > -1) {
                zos.write(tmp);
            }
        } catch (FileNotFoundException e) {
            throw new SerializationException("FileNotFoundException, name of file: " + file + ", from: SerializationUtils", e);
        } catch (IOException e) {
            throw new SerializationException("IOException, caused by: in.readObject(), from: SerializationUtils", e);
        }
    }

}
