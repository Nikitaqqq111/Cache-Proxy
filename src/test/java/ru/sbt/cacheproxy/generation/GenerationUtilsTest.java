package ru.sbt.cacheproxy.generation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.cacheproxy.service.Service;
import ru.sbt.cacheproxy.service.ServiceImpl;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Никита on 28.08.2016.
 */
public class GenerationUtilsTest {

    Method method;

    @Before
    public void setUp() throws Exception {
        method = new ServiceImpl().getClass().getMethod("run", new Class<?>[]{String.class, int.class, boolean.class});
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getIdentityArguments() throws Exception {
        assertArrayEquals(GenerationUtils.getIdentityArguments(method, new Object[]{new String("5"), 3, false}), new Object[]{new String("5")});;
    }

    @Test
    public void generateFileName() throws Exception {
        assertEquals(GenerationUtils.generateFileName(method,  new Object[]{new String("5"), 3, false}, "D:\\"), "D:\\smth_5.ser");
    }

    @Test
    public void generateKey() throws Exception {
        List<Object> keyList = (List) GenerationUtils.generateKey(method, new Object[]{"smth", 50, false});
        assertEquals(keyList.get(0), method);
        assertEquals(keyList.get(1), "smth");
        assertEquals(keyList.size(), 2);
    }

    @Test
    public void cutList() throws Exception {
        assertEquals(((List<Object>) GenerationUtils.cutList(method, new ServiceImpl().run("Aaaa", 4, false))).size(), 3);
    }

}