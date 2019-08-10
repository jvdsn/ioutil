/*
 * Copyright 2019 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.joachimvandersmissen.ioutil.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class AbstractReaderTest {
    @Test
    public void testReadByte() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readByte());
            Assertions.assertEquals(0, bigEndian.readByte());
            Assertions.assertEquals(127, littleEndian.readByte());
            Assertions.assertEquals(127, bigEndian.readByte());
            Assertions.assertEquals(-128, littleEndian.readByte());
            Assertions.assertEquals(-128, bigEndian.readByte());
            Assertions.assertEquals(-1, littleEndian.readByte());
            Assertions.assertEquals(-1, bigEndian.readByte());
            Assertions.assertThrows(EOFException.class, littleEndian::readByte);
            Assertions.assertThrows(EOFException.class, bigEndian::readByte);
        }
    }

    @Test
    public void testReadUnsignedShort() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readUnsignedShort());
            Assertions.assertEquals(0, bigEndian.readUnsignedShort());
            Assertions.assertEquals(32767, littleEndian.readUnsignedShort());
            Assertions.assertEquals(32767, bigEndian.readUnsignedShort());
            Assertions.assertEquals(32768, littleEndian.readUnsignedShort());
            Assertions.assertEquals(32768, bigEndian.readUnsignedShort());
            Assertions.assertEquals(65535, littleEndian.readUnsignedShort());
            Assertions.assertEquals(65535, bigEndian.readUnsignedShort());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedShort);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedShort);
        }
    }

    @Test
    public void testReadShort() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readShort());
            Assertions.assertEquals(0, bigEndian.readShort());
            Assertions.assertEquals(32767, littleEndian.readShort());
            Assertions.assertEquals(32767, bigEndian.readShort());
            Assertions.assertEquals(-32768, littleEndian.readShort());
            Assertions.assertEquals(-32768, bigEndian.readShort());
            Assertions.assertEquals(-1, littleEndian.readShort());
            Assertions.assertEquals(-1, bigEndian.readShort());
            Assertions.assertThrows(EOFException.class, littleEndian::readShort);
            Assertions.assertThrows(EOFException.class, bigEndian::readShort);
        }
    }

    @Test
    public void testReadUnsignedInt() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0L, littleEndian.readUnsignedInt());
            Assertions.assertEquals(0L, bigEndian.readUnsignedInt());
            Assertions.assertEquals(2147483647L, littleEndian.readUnsignedInt());
            Assertions.assertEquals(2147483647L, bigEndian.readUnsignedInt());
            Assertions.assertEquals(2147483648L, littleEndian.readUnsignedInt());
            Assertions.assertEquals(2147483648L, bigEndian.readUnsignedInt());
            Assertions.assertEquals(4294967295L, littleEndian.readUnsignedInt());
            Assertions.assertEquals(4294967295L, bigEndian.readUnsignedInt());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedInt);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedInt);
        }
    }

    @Test
    public void testReadInt() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readInt());
            Assertions.assertEquals(0, bigEndian.readInt());
            Assertions.assertEquals(2147483647, littleEndian.readInt());
            Assertions.assertEquals(2147483647, bigEndian.readInt());
            Assertions.assertEquals(-2147483648, littleEndian.readInt());
            Assertions.assertEquals(-2147483648, bigEndian.readInt());
            Assertions.assertEquals(-1, littleEndian.readInt());
            Assertions.assertEquals(-1, bigEndian.readInt());
            Assertions.assertThrows(EOFException.class, littleEndian::readInt);
            Assertions.assertThrows(EOFException.class, bigEndian::readInt);
        }
    }

    @Test
    public void testReadUnsignedLong() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(BigInteger.valueOf(0L), littleEndian.readUnsignedLong());
            Assertions.assertEquals(BigInteger.valueOf(0L), bigEndian.readUnsignedLong());
            Assertions.assertEquals(BigInteger.valueOf(9223372036854775807L), littleEndian.readUnsignedLong());
            Assertions.assertEquals(BigInteger.valueOf(9223372036854775807L), bigEndian.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}), littleEndian.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}), bigEndian.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}), littleEndian.readUnsignedLong());
            Assertions.assertEquals(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}), bigEndian.readUnsignedLong());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedLong);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedLong);
        }
    }

    @Test
    public void testReadLong() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0L, littleEndian.readLong());
            Assertions.assertEquals(0L, bigEndian.readLong());
            Assertions.assertEquals(9223372036854775807L, littleEndian.readLong());
            Assertions.assertEquals(9223372036854775807L, bigEndian.readLong());
            Assertions.assertEquals(-9223372036854775808L, littleEndian.readLong());
            Assertions.assertEquals(-9223372036854775808L, bigEndian.readLong());
            Assertions.assertEquals(-1L, littleEndian.readLong());
            Assertions.assertEquals(-1L, bigEndian.readLong());
            Assertions.assertThrows(EOFException.class, littleEndian::readLong);
            Assertions.assertThrows(EOFException.class, bigEndian::readLong);
        }
    }

    @Test
    public void testReadChar() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals('\u0000', littleEndian.readChar());
            Assertions.assertEquals('\u0000', bigEndian.readChar());
            Assertions.assertEquals('\u7FFF', littleEndian.readChar());
            Assertions.assertEquals('\u7FFF', bigEndian.readChar());
            Assertions.assertEquals('\u8000', littleEndian.readChar());
            Assertions.assertEquals('\u8000', bigEndian.readChar());
            Assertions.assertEquals('\uFFFF', littleEndian.readChar());
            Assertions.assertEquals('\uFFFF', bigEndian.readChar());
            Assertions.assertThrows(EOFException.class, littleEndian::readChar);
            Assertions.assertThrows(EOFException.class, bigEndian::readChar);
        }
    }

    @Test
    public void testReadFloat() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(Float.intBitsToFloat(0), littleEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(0), bigEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(2147483647), littleEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(2147483647), bigEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-2147483648), littleEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-2147483648), bigEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-1), littleEndian.readFloat());
            Assertions.assertEquals(Float.intBitsToFloat(-1), bigEndian.readFloat());
            Assertions.assertThrows(EOFException.class, littleEndian::readFloat);
            Assertions.assertThrows(EOFException.class, bigEndian::readFloat);
        }
    }

    @Test
    public void testReadDouble() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(Double.longBitsToDouble(0L), littleEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(0L), bigEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(9223372036854775807L), littleEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(9223372036854775807L), bigEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-9223372036854775808L), littleEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-9223372036854775808L), bigEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-1L), littleEndian.readDouble());
            Assertions.assertEquals(Double.longBitsToDouble(-1L), bigEndian.readDouble());
            Assertions.assertThrows(EOFException.class, littleEndian::readDouble);
            Assertions.assertThrows(EOFException.class, bigEndian::readDouble);
        }
    }

    @Test
    public void testReadUnsignedLEB128() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26};
        byte[] bigEndianBytes = {0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(0L, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(1L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(1L, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(127L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(127L, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(2147483647, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(2147483647, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(2147483648L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(2147483648L, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(4294967295L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(4294967295L, bigEndian.readUnsignedLEB128());
            Assertions.assertEquals(624485L, littleEndian.readUnsignedLEB128());
            Assertions.assertEquals(624485L, bigEndian.readUnsignedLEB128());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedLEB128);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedLEB128);
        }
    }

    @Test
    public void testReadUnsignedLEB128Plus1() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10};
        byte[] bigEndianBytes = {0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(-1L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(-1L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(0L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(0L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(1L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(1L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(127L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(127L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483647L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483647L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483648L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483648L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(4294967295L, littleEndian.readUnsignedLEB128Plus1());
            Assertions.assertEquals(4294967295L, bigEndian.readUnsignedLEB128Plus1());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedLEB128Plus1);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedLEB128Plus1);
        }
    }

    @Test
    public void testReadSignedLEB128() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59};
        byte[] bigEndianBytes = {0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59};
        try (Reader littleEndian = ByteArrayReader.littleEndian(littleEndianBytes); Reader bigEndian = ByteArrayReader.bigEndian(bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readSignedLEB128());
            Assertions.assertEquals(0, bigEndian.readSignedLEB128());
            Assertions.assertEquals(1, littleEndian.readSignedLEB128());
            Assertions.assertEquals(1, bigEndian.readSignedLEB128());
            Assertions.assertEquals(-1, littleEndian.readSignedLEB128());
            Assertions.assertEquals(-1, bigEndian.readSignedLEB128());
            Assertions.assertEquals(2147483647, littleEndian.readSignedLEB128());
            Assertions.assertEquals(2147483647, bigEndian.readSignedLEB128());
            Assertions.assertEquals(-2147483648, littleEndian.readSignedLEB128());
            Assertions.assertEquals(-2147483648, bigEndian.readSignedLEB128());
            Assertions.assertEquals(-624485, littleEndian.readSignedLEB128());
            Assertions.assertEquals(-624485, bigEndian.readSignedLEB128());
            Assertions.assertThrows(EOFException.class, littleEndian::readSignedLEB128);
            Assertions.assertThrows(EOFException.class, bigEndian::readSignedLEB128);
        }
    }
}
