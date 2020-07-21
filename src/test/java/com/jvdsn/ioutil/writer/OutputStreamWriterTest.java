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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Joachim Vandersmissen
 */
public class OutputStreamWriterTest {
    @Test
    public void testWriteUnsignedByte() throws IOException {
        ByteArrayOutputStream littleEndianStream = new ByteArrayOutputStream();
        ByteArrayOutputStream bigEndianStream = new ByteArrayOutputStream();
        try (Writer littleEndian = new OutputStreamWriter(Endianness.LITTLE_ENDIAN, littleEndianStream); Writer bigEndian = new OutputStreamWriter(Endianness.BIG_ENDIAN, bigEndianStream)) {
            littleEndian.writeUnsignedByte(0);
            bigEndian.writeUnsignedByte(0);
            littleEndian.writeUnsignedByte(127);
            bigEndian.writeUnsignedByte(127);
            littleEndian.writeUnsignedByte(128);
            bigEndian.writeUnsignedByte(128);
            littleEndian.writeUnsignedByte(255);
            bigEndian.writeUnsignedByte(255);
        }

        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, littleEndianStream.toByteArray());
        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, bigEndianStream.toByteArray());
    }

    @Test
    public void testWriteBytes() throws IOException {
        ByteArrayOutputStream littleEndianStream = new ByteArrayOutputStream();
        ByteArrayOutputStream bigEndianStream = new ByteArrayOutputStream();
        try (Writer littleEndian = new OutputStreamWriter(Endianness.LITTLE_ENDIAN, littleEndianStream); Writer bigEndian = new OutputStreamWriter(Endianness.BIG_ENDIAN, bigEndianStream)) {
            littleEndian.writeBytes(new byte[]{0, 0, 1, 0}, 1, 2);
            bigEndian.writeBytes(new byte[]{0, 0, 1, 0}, 1, 2);
            littleEndian.writeBytes(new byte[]{2, 3});
            bigEndian.writeBytes(new byte[]{2, 3});
        }

        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3}, littleEndianStream.toByteArray());
        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3}, bigEndianStream.toByteArray());
    }
}
