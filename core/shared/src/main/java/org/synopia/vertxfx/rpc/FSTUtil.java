package org.synopia.vertxfx.rpc;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectOutput;
import org.vertx.java.core.buffer.Buffer;

import java.io.IOException;

/**
 * @author synopia
 */
public class FSTUtil {

    private FSTUtil() {
    }

    public static <U> U read(Buffer readBuff) {
        return read(readBuff.getBytes());
    }

    public static <U> U read(byte[] arr) {
        return read(arr, arr.length);
    }

    public static <U> U read(byte[] arr, int len) {
        try {
            FSTConfiguration config = FSTConfiguration.createDefaultConfiguration();
            return (U) config.getObjectInput(arr, len).readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <U> Buffer write(U object) {
        Buffer buffer = new Buffer();
        write(object, buffer);
        return buffer;
    }

    public static <U> void write(U object, Buffer buff) {
        try {
            FSTConfiguration config = FSTConfiguration.createDefaultConfiguration();
            FSTObjectOutput output = config.getObjectOutput();
            output.writeObject(object);
            buff.appendBytes(output.getBuffer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
