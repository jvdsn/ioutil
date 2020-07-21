/*
 * Copyright 2020 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.jvdsn.ioutil.writer;

import com.jvdsn.ioutil.Endianness;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class AbstractWriterTest {
    @Test
    public void testWriteByte() throws IOException {
        byte[] littleEndianBytes = new byte[4];
        byte[] bigEndianBytes = new byte[4];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeByte((byte) 0);
            bigEndian.writeByte((byte) 0);
            littleEndian.writeByte((byte) 127);
            bigEndian.writeByte((byte) 127);
            littleEndian.writeByte((byte) -128);
            bigEndian.writeByte((byte) -128);
            littleEndian.writeByte((byte) -1);
            bigEndian.writeByte((byte) -1);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeByte((byte) 0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeByte((byte) 0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteUnsignedShort() throws IOException {
        byte[] littleEndianBytes = new byte[8];
        byte[] bigEndianBytes = new byte[8];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeUnsignedShort(0);
            bigEndian.writeUnsignedShort(0);
            littleEndian.writeUnsignedShort(32767);
            bigEndian.writeUnsignedShort(32767);
            littleEndian.writeUnsignedShort(32768);
            bigEndian.writeUnsignedShort(32768);
            littleEndian.writeUnsignedShort(65535);
            bigEndian.writeUnsignedShort(65535);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedShort(0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedShort(0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testeWriteShort() throws IOException {
        byte[] littleEndianBytes = new byte[8];
        byte[] bigEndianBytes = new byte[8];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeShort((short) 0);
            bigEndian.writeShort((short) 0);
            littleEndian.writeShort((short) 32767);
            bigEndian.writeShort((short) 32767);
            littleEndian.writeShort((short) -32768);
            bigEndian.writeShort((short) -32768);
            littleEndian.writeShort((short) -1);
            bigEndian.writeShort((short) -1);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeShort((short) 0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeShort((short) 0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteUnsignedInt() throws IOException {
        byte[] littleEndianBytes = new byte[16];
        byte[] bigEndianBytes = new byte[16];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeUnsignedInt(0L);
            bigEndian.writeUnsignedInt(0L);
            littleEndian.writeUnsignedInt(2147483647L);
            bigEndian.writeUnsignedInt(2147483647L);
            littleEndian.writeUnsignedInt(2147483648L);
            bigEndian.writeUnsignedInt(2147483648L);
            littleEndian.writeUnsignedInt(4294967295L);
            bigEndian.writeUnsignedInt(4294967295L);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedInt(0L));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedInt(0L));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteInt() throws IOException {
        byte[] littleEndianBytes = new byte[16];
        byte[] bigEndianBytes = new byte[16];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeInt(0);
            bigEndian.writeInt(0);
            littleEndian.writeInt(2147483647);
            bigEndian.writeInt(2147483647);
            littleEndian.writeInt(-2147483648);
            bigEndian.writeInt(-2147483648);
            littleEndian.writeInt(-1);
            bigEndian.writeInt(-1);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeInt(0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeInt(0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteUnsignedLong() throws IOException {
        byte[] littleEndianBytes = new byte[32];
        byte[] bigEndianBytes = new byte[32];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeUnsignedLong(BigInteger.valueOf(0L));
            bigEndian.writeUnsignedLong(BigInteger.valueOf(0L));
            littleEndian.writeUnsignedLong(BigInteger.valueOf(9223372036854775807L));
            bigEndian.writeUnsignedLong(BigInteger.valueOf(9223372036854775807L));
            littleEndian.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
            bigEndian.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
            littleEndian.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}));
            bigEndian.writeUnsignedLong(new BigInteger(1, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedLong(BigInteger.valueOf(0L)));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedLong(BigInteger.valueOf(0L)));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteLong() throws IOException {
        byte[] littleEndianBytes = new byte[32];
        byte[] bigEndianBytes = new byte[32];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeLong(0L);
            bigEndian.writeLong(0L);
            littleEndian.writeLong(9223372036854775807L);
            bigEndian.writeLong(9223372036854775807L);
            littleEndian.writeLong(-9223372036854775808L);
            bigEndian.writeLong(-9223372036854775808L);
            littleEndian.writeLong(-1L);
            bigEndian.writeLong(-1L);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeLong(0L));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeLong(0L));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteChar() throws IOException {
        byte[] littleEndianBytes = new byte[8];
        byte[] bigEndianBytes = new byte[8];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeChar('\u0000');
            bigEndian.writeChar('\u0000');
            littleEndian.writeChar('\u7fff');
            bigEndian.writeChar('\u7fff');
            littleEndian.writeChar('\u8000');
            bigEndian.writeChar('\u8000');
            littleEndian.writeChar('\uffff');
            bigEndian.writeChar('\uffff');
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeChar('\u0000'));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeChar('\u0000'));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, (byte) 0xFF, 0x7F, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteFloat() throws IOException {
        byte[] littleEndianBytes = new byte[16];
        byte[] bigEndianBytes = new byte[16];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeFloat(Float.intBitsToFloat(0));
            bigEndian.writeFloat(Float.intBitsToFloat(0));
            littleEndian.writeFloat(Float.intBitsToFloat(2147483647));
            bigEndian.writeFloat(Float.intBitsToFloat(2147483647));
            littleEndian.writeFloat(Float.intBitsToFloat(-2147483648));
            bigEndian.writeFloat(Float.intBitsToFloat(-2147483648));
            littleEndian.writeFloat(Float.intBitsToFloat(-1));
            bigEndian.writeFloat(Float.intBitsToFloat(-1));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeFloat(Float.intBitsToFloat(0)));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeFloat(Float.intBitsToFloat(0)));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteDouble() throws IOException {
        byte[] littleEndianBytes = new byte[32];
        byte[] bigEndianBytes = new byte[32];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeDouble(Double.longBitsToDouble(0L));
            bigEndian.writeDouble(Double.longBitsToDouble(0L));
            littleEndian.writeDouble(Double.longBitsToDouble(9223372036854775807L));
            bigEndian.writeDouble(Double.longBitsToDouble(9223372036854775807L));
            littleEndian.writeDouble(Double.longBitsToDouble(-9223372036854775808L));
            bigEndian.writeDouble(Double.longBitsToDouble(-9223372036854775808L));
            littleEndian.writeDouble(Double.longBitsToDouble(-1L));
            bigEndian.writeDouble(Double.longBitsToDouble(-1L));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeDouble(Double.longBitsToDouble(0L)));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeDouble(Double.longBitsToDouble(0L)));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteUnsignedLEB128() throws IOException {
        byte[] littleEndianBytes = new byte[21];
        byte[] bigEndianBytes = new byte[21];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeUnsignedLEB128(0L);
            bigEndian.writeUnsignedLEB128(0L);
            littleEndian.writeUnsignedLEB128(1L);
            bigEndian.writeUnsignedLEB128(1L);
            littleEndian.writeUnsignedLEB128(127L);
            bigEndian.writeUnsignedLEB128(127L);
            littleEndian.writeUnsignedLEB128(2147483647L);
            bigEndian.writeUnsignedLEB128(2147483647L);
            littleEndian.writeUnsignedLEB128(2147483648L);
            bigEndian.writeUnsignedLEB128(2147483648L);
            littleEndian.writeUnsignedLEB128(4294967295L);
            bigEndian.writeUnsignedLEB128(4294967295L);
            littleEndian.writeUnsignedLEB128(624485L);
            bigEndian.writeUnsignedLEB128(624485L);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedLEB128(0L));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedLEB128(0L));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26}, bigEndianBytes);
    }

    @Test
    public void testWriteUnsignedLEB128Plus1() throws IOException {
        byte[] littleEndianBytes = new byte[20];
        byte[] bigEndianBytes = new byte[20];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeUnsignedLEB128Plus1(-1L);
            bigEndian.writeUnsignedLEB128Plus1(-1L);
            littleEndian.writeUnsignedLEB128Plus1(0L);
            bigEndian.writeUnsignedLEB128Plus1(0L);
            littleEndian.writeUnsignedLEB128Plus1(1L);
            bigEndian.writeUnsignedLEB128Plus1(1L);
            littleEndian.writeUnsignedLEB128Plus1(127L);
            bigEndian.writeUnsignedLEB128Plus1(127L);
            littleEndian.writeUnsignedLEB128Plus1(2147483647L);
            bigEndian.writeUnsignedLEB128Plus1(2147483647L);
            littleEndian.writeUnsignedLEB128Plus1(2147483648L);
            bigEndian.writeUnsignedLEB128Plus1(2147483648L);
            littleEndian.writeUnsignedLEB128Plus1(4294967295L);
            bigEndian.writeUnsignedLEB128Plus1(4294967295L);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedLEB128Plus1(0L));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedLEB128Plus1(0L));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10}, bigEndianBytes);
    }

    @Test
    public void testWriteSignedLEB128() throws IOException {
        byte[] littleEndianBytes = new byte[16];
        byte[] bigEndianBytes = new byte[16];
        try (Writer littleEndian = new ByteArrayWriter(Endianness.LITTLE_ENDIAN, littleEndianBytes); Writer bigEndian = new ByteArrayWriter(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            littleEndian.writeSignedLEB128(0);
            bigEndian.writeSignedLEB128(0);
            littleEndian.writeSignedLEB128(1);
            bigEndian.writeSignedLEB128(1);
            littleEndian.writeSignedLEB128(-1);
            bigEndian.writeSignedLEB128(-1);
            littleEndian.writeSignedLEB128(2147483647);
            bigEndian.writeSignedLEB128(2147483647);
            littleEndian.writeSignedLEB128(-2147483648);
            bigEndian.writeSignedLEB128(-2147483648);
            littleEndian.writeSignedLEB128(-624485);
            bigEndian.writeSignedLEB128(-624485);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeSignedLEB128(0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeSignedLEB128(0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59}, bigEndianBytes);
    }
}
