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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Joachim Vandersmissen
 */
public class InputStreamReaderTest {
    @Test
    public void testReadUnsignedByte() throws IOException {
        InputStream littleEndianStream = new ByteArrayInputStream(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF});
        InputStream bigEndianStream = new ByteArrayInputStream(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF});
        try (Reader littleEndian = InputStreamReader.littleEndian(littleEndianStream); Reader bigEndian = InputStreamReader.bigEndian(bigEndianStream)) {
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
        InputStream littleEndianStream = new ByteArrayInputStream(new byte[]{0, 1, 2, 3});
        InputStream bigEndianStream = new ByteArrayInputStream(new byte[]{0, 1, 2, 3});
        try (Reader littleEndian = InputStreamReader.littleEndian(littleEndianStream); Reader bigEndian = InputStreamReader.bigEndian(bigEndianStream)) {
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
