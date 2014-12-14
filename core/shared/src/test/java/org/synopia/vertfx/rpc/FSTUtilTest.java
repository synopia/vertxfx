package org.synopia.vertfx.rpc;

import org.junit.Test;
import org.synopia.vertxfx.rpc.FSTUtil;
import org.vertx.java.core.buffer.Buffer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author synopia
 */
public class FSTUtilTest {
    public static class Pojo implements Serializable {
        private int a = 100;
        private String xyz = "foobar";
        private Date time;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pojo)) return false;

            Pojo pojo = (Pojo) o;

            return a == pojo.a && time.equals(pojo.time) && xyz.equals(pojo.xyz);

        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31 * result + xyz.hashCode();
            result = 31 * result + time.hashCode();
            return result;
        }
    }

    @Test
    public void testPrimitives() {
        assertSerialize((byte) 1);
        assertSerialize((byte) 254);
        assertSerialize(10000000L);
        assertSerialize(1010.1f);
        assertSerialize(1010.1d);
        assertSerialize("FOO_BARü");
    }

    @Test
    public void testPojo() {
        Date now = new Date();
        Pojo pojo = new Pojo();
        pojo.time = now;

        assertSerialize(pojo);
    }

    @Test
    public void testList() {
        List<String> list = Arrays.asList("foo", "bar");
        assertSerialize(list);
    }

    private void assertSerialize(Object test) {
        Buffer buffer = FSTUtil.write(test);

        Object read = FSTUtil.read(buffer);

        assertEquals(test, read);
    }
}
