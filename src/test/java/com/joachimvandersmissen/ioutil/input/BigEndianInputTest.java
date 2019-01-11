/*
 * Copyright 2019 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.joachimvandersmissen.ioutil.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class BigEndianInputTest {
    @Test
    public void testConstructors() throws IOException {
        byte[] bytes = {0, 1};
        try (Input input1 = new BigEndianInput(new ByteArrayInputStream(bytes)); Input input2 = new BigEndianInput(bytes)) {
            Assertions.assertEquals(input2.readUnsignedByte(), input1.readUnsignedByte());
            Assertions.assertEquals(input2.readUnsignedByte(), input1.readUnsignedByte());
        }
    }

    @Test
    public void testReadUnsignedShort() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readUnsignedShort());
            Assertions.assertEquals(32767, input.readUnsignedShort());
            Assertions.assertEquals(32768, input.readUnsignedShort());
            Assertions.assertEquals(65535, input.readUnsignedShort());
        }
    }

    @Test
    public void testReadShort() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readShort());
            Assertions.assertEquals(32767, input.readShort());
            Assertions.assertEquals(-32768, input.readShort());
            Assertions.assertEquals(-1, input.readShort());
        }
    }

    @Test
    public void testReadUnsignedInt() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readUnsignedInt());
            Assertions.assertEquals(2147483647, input.readUnsignedInt());
            Assertions.assertEquals(2147483648L, input.readUnsignedInt());
            Assertions.assertEquals(4294967295L, input.readUnsignedInt());
        }
    }

    @Test
    public void testReadInt() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readInt());
            Assertions.assertEquals(2147483647, input.readInt());
            Assertions.assertEquals(-2147483648, input.readInt());
            Assertions.assertEquals(-1, input.readInt());
        }
    }

    @Test
    public void testReadUnsignedLong() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(BigInteger.valueOf(0), input.readUnsignedLong());
            Assertions.assertEquals(BigInteger.valueOf(9223372036854775807L), input.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}), input.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}), input.readUnsignedLong());
        }
    }

    @Test
    public void testReadLong() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(0L, input.readLong());
            Assertions.assertEquals(9223372036854775807L, input.readLong());
            Assertions.assertEquals(-9223372036854775808L, input.readLong());
            Assertions.assertEquals(-1L, input.readLong());
        }
    }

    @Test
    public void testReadChar() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals('\u0000', input.readChar());
            Assertions.assertEquals('\u7FFF', input.readChar());
            Assertions.assertEquals('\u8000', input.readChar());
            Assertions.assertEquals('\uFFFF', input.readChar());
        }
    }

    @Test
    public void testReadFloat() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(Float.intBitsToFloat(0), input.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(2147483647), input.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-2147483648), input.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-1), input.readFloat());
        }
    }

    @Test
    public void testReadDouble() throws IOException {
        try (Input input = new BigEndianInput(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF})) {
            Assertions.assertEquals(Double.longBitsToDouble(0), input.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(9223372036854775807L), input.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-9223372036854775808L), input.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-1), input.readDouble());
        }
    }
}
