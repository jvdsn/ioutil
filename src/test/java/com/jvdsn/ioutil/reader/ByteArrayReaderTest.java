/*
 * Copyright 2020 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.jvdsn.ioutil.reader;

import com.jvdsn.ioutil.Endianness;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author Joachim Vandersmissen
 */
public class ByteArrayReaderTest {
    @Test
    public void testPosition() throws IOException {
        byte[] littleEndianBytes = new byte[3];
        byte[] bigEndianBytes = new byte[3];
        try (ByteArrayReader littleEndian = new ByteArrayReader(Endianness.LITTLE_ENDIAN, littleEndianBytes, 1, 1); ByteArrayReader bigEndian = new ByteArrayReader(Endianness.BIG_ENDIAN, bigEndianBytes, 1, 1)) {
            Assertions.assertEquals(1, littleEndian.position());
            Assertions.assertEquals(1, bigEndian.position());
            littleEndian.readUnsignedByte();
            bigEndian.readUnsignedByte();
            Assertions.assertEquals(2, littleEndian.position());
            Assertions.assertEquals(2, bigEndian.position());
        }
    }

    @Test
    public void testRemaining() throws IOException {
        byte[] littleEndianBytes = new byte[3];
        byte[] bigEndianBytes = new byte[3];
        try (ByteArrayReader littleEndian = new ByteArrayReader(Endianness.LITTLE_ENDIAN, littleEndianBytes, 1, 1); ByteArrayReader bigEndian = new ByteArrayReader(Endianness.BIG_ENDIAN, bigEndianBytes, 1, 1)) {
            Assertions.assertEquals(1, littleEndian.remaining());
            Assertions.assertEquals(1, bigEndian.remaining());
            littleEndian.readUnsignedByte();
            bigEndian.readUnsignedByte();
            Assertions.assertEquals(0, littleEndian.remaining());
            Assertions.assertEquals(0, bigEndian.remaining());
        }
    }

    @Test
    public void testReadUnsignedByte() throws IOException {
        byte[] littleEndianBytes = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        byte[] bigEndianBytes = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        try (Reader littleEndian = new ByteArrayReader(Endianness.LITTLE_ENDIAN, littleEndianBytes); Reader bigEndian = new ByteArrayReader(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            Assertions.assertEquals(0, littleEndian.readUnsignedByte());
            Assertions.assertEquals(0, bigEndian.readUnsignedByte());
            Assertions.assertEquals(127, littleEndian.readUnsignedByte());
            Assertions.assertEquals(127, bigEndian.readUnsignedByte());
            Assertions.assertEquals(128, littleEndian.readUnsignedByte());
            Assertions.assertEquals(128, bigEndian.readUnsignedByte());
            Assertions.assertEquals(255, littleEndian.readUnsignedByte());
            Assertions.assertEquals(255, bigEndian.readUnsignedByte());
            Assertions.assertThrows(EOFException.class, littleEndian::readUnsignedByte);
            Assertions.assertThrows(EOFException.class, bigEndian::readUnsignedByte);
        }
    }

    @Test
    public void testReadBytes() throws IOException {
        byte[] littleEndianBytes = {0, 1, 2, 3};
        byte[] bigEndianBytes = {0, 1, 2, 3};
        try (Reader littleEndian = new ByteArrayReader(Endianness.LITTLE_ENDIAN, littleEndianBytes); Reader bigEndian = new ByteArrayReader(Endianness.BIG_ENDIAN, bigEndianBytes)) {
            Assertions.assertArrayEquals(new byte[]{0, 0, 1, 0}, littleEndian.readBytes(new byte[4], 1, 2));
            Assertions.assertArrayEquals(new byte[]{0, 0, 1, 0}, bigEndian.readBytes(new byte[4], 1, 2));
            Assertions.assertArrayEquals(new byte[]{2, 3}, littleEndian.readBytes(new byte[2]));
            Assertions.assertArrayEquals(new byte[]{2, 3}, bigEndian.readBytes(new byte[2]));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.readBytes(new byte[1], 0, 1));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.readBytes(new byte[1], 0, 1));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.readBytes(new byte[1]));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.readBytes(new byte[1]));
        }
    }
}
