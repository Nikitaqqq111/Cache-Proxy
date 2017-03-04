package ru.sbt.cacheproxy.serialization;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Никита on 26.08.2016.
 */
public class SerializationUtilsTest {
    @Before
    public void setUp() throws Exception {
//        SerializationUtils.serialize(new Integer(4), "src\\test\\resources\\Integer.ser");
    }

    @Test
    public void serialize() throws Exception {
//        SerializationUtils.serialize(new String("StringSerializable"), "src\\test\\resources\\String.ser");
//        SerializationUtils.serialize(new Long(1234324L), "src\\test\\resources\\Long.ser");
//        SerializationUtils.serialize(new Boolean(false), "src\\test\\resources\\Boolean.ser");
    }

    @Test
    public void deserialize() throws Exception {
//        assertEquals(SerializationUtils.deserialize("src\\test\\resources\\Integer.ser"), new Integer(4));
    }

//    @Test(expected = SerializationException.class)
    public void deserialize2() throws Exception {
//        SerializationUtils.deserialize("src\\test\\resources\\FileNotFound.ser");
    }

    @Test
    public void zipFile() throws Exception {
//        SerializationUtils.zipFile("src\\test\\resources\\Integer.ser");
    }

}