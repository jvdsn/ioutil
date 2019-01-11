/*
 * Copyright 2019 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.joachimvandersmissen.ioutil.output;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class LittleEndianOutputTest {
    @Test
    public void testWriteUnsignedShort() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeUnsignedShort(0);
            output.writeUnsignedShort(32767);
            output.writeUnsignedShort(32768);
            output.writeUnsignedShort(65535);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteShort() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeShort((short) 0);
            output.writeShort((short) 32767);
            output.writeShort((short) -32768);
            output.writeShort((short) -1);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteUnsignedInt() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeUnsignedInt(0);
            output.writeUnsignedInt(2147483647);
            output.writeUnsignedInt(2147483648L);
            output.writeUnsignedInt(4294967295L);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteInt() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeInt(0);
            output.writeInt(2147483647);
            output.writeInt(-2147483648);
            output.writeInt(-1);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteUnsignedLong() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeUnsignedLong(BigInteger.valueOf(0));
            output.writeUnsignedLong(BigInteger.valueOf(9223372036854775807L));
            output.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
            output.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}));
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteLong() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeLong(0);
            output.writeLong(9223372036854775807L);
            output.writeLong(-9223372036854775808L);
            output.writeLong(-1);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteChar() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeChar('\u0000');
            output.writeChar('\u7FFF');
            output.writeChar('\u8000');
            output.writeChar('\uFFFF');
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteFloat() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeFloat(Float.intBitsToFloat(0));
            output.writeFloat(Float.intBitsToFloat(2147483647));
            output.writeFloat(Float.intBitsToFloat(-2147483648));
            output.writeFloat(Float.intBitsToFloat(-1));
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteDouble() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new LittleEndianOutput(byteArrayOutputStream)) {
            output.writeDouble(Double.longBitsToDouble(0));
            output.writeDouble(Double.longBitsToDouble(9223372036854775807L));
            output.writeDouble(Double.longBitsToDouble(-9223372036854775808L));
            output.writeDouble(Double.longBitsToDouble(-1));
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }
}
