/*
 * Copyright 2019 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.joachimvandersmissen.ioutil.writer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author Joachim Vandersmissen
 */
public class ByteArrayWriterTest {
    @Test
    public void testPosition() throws IOException {
        byte[] littleEndianBytes = new byte[3];
        byte[] bigEndianBytes = new byte[3];
        try (ByteArrayWriter littleEndian = ByteArrayWriter.littleEndian(littleEndianBytes, 1, 1); ByteArrayWriter bigEndian = ByteArrayWriter.bigEndian(bigEndianBytes, 1, 1)) {
            Assertions.assertEquals(1, littleEndian.position());
            Assertions.assertEquals(1, bigEndian.position());
            littleEndian.writeUnsignedByte(0);
            bigEndian.writeUnsignedByte(0);
            Assertions.assertEquals(2, littleEndian.position());
            Assertions.assertEquals(2, bigEndian.position());
        }
    }

    @Test
    public void testRemaining() throws IOException {
        byte[] littleEndianBytes = new byte[3];
        byte[] bigEndianBytes = new byte[3];
        try (ByteArrayWriter littleEndian = ByteArrayWriter.littleEndian(littleEndianBytes, 1, 1); ByteArrayWriter bigEndian = ByteArrayWriter.bigEndian(bigEndianBytes, 1, 1)) {
            Assertions.assertEquals(1, littleEndian.remaining());
            Assertions.assertEquals(1, bigEndian.remaining());
            littleEndian.writeUnsignedByte(0);
            bigEndian.writeUnsignedByte(0);
            Assertions.assertEquals(0, littleEndian.remaining());
            Assertions.assertEquals(0, bigEndian.remaining());
        }
    }

    @Test
    public void testWriteUnsignedByte() throws IOException {
        byte[] littleEndianBytes = new byte[4];
        byte[] bigEndianBytes = new byte[4];
        try (Writer littleEndian = ByteArrayWriter.littleEndian(littleEndianBytes); Writer bigEndian = ByteArrayWriter.bigEndian(bigEndianBytes)) {
            littleEndian.writeUnsignedByte(0);
            bigEndian.writeUnsignedByte(0);
            littleEndian.writeUnsignedByte(127);
            bigEndian.writeUnsignedByte(127);
            littleEndian.writeUnsignedByte(128);
            bigEndian.writeUnsignedByte(128);
            littleEndian.writeUnsignedByte(255);
            bigEndian.writeUnsignedByte(255);
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeUnsignedByte(0));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeUnsignedByte(0));
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, bigEndianBytes);
    }

    @Test
    public void testWriteBytes() throws IOException {
        byte[] littleEndianBytes = new byte[4];
        byte[] bigEndianBytes = new byte[4];
        try (Writer littleEndian = ByteArrayWriter.littleEndian(littleEndianBytes); Writer bigEndian = ByteArrayWriter.bigEndian(bigEndianBytes)) {
            littleEndian.writeBytes(new byte[]{0, 0, 1, 0}, 1, 2);
            bigEndian.writeBytes(new byte[]{0, 0, 1, 0}, 1, 2);
            littleEndian.writeBytes(new byte[]{2, 3});
            bigEndian.writeBytes(new byte[]{2, 3});
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeBytes(new byte[1], 0, 1));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeBytes(new byte[1], 0, 1));
            Assertions.assertThrows(EOFException.class, () -> littleEndian.writeBytes(new byte[1]));
            Assertions.assertThrows(EOFException.class, () -> bigEndian.writeBytes(new byte[1]));
        }

        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3}, littleEndianBytes);
        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3}, bigEndianBytes);
    }
}
