package ru.sbt.cacheproxy.generation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.cacheproxy.service.Service;
import ru.sbt.cacheproxy.service.ServiceImpl;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Никита on 28.08.2016.
 */
public class GenerationUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getIdentityArguments() throws Exception {
        Method method = new ServiceImpl().getClass().getMethod("run", new Class<?>[]{String.class, int.class, boolean.class});
        assertArrayEquals(new Object[]{5}, GenerationUtils.getIdentityArguments(method, new Object[]{5}));
    }

    @Test
    public void generateFileName() throws Exception {

    }

    @Test
    public void generateKey() throws Exception {

    }

    @Test
    public void cutList() throws Exception {

    }

}