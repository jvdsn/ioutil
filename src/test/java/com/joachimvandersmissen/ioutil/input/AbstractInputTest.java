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

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class AbstractInputTest {
    @Test
    public void testPosition() throws IOException {
        try (Input input = new TestAbstractInput((byte) 0)) {
            Assertions.assertEquals(0, input.position());
            input.readUnsignedByte();
            Assertions.assertEquals(1, input.position());
        }
    }

    @Test
    public void testReadUnsignedByte() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readUnsignedByte());
            Assertions.assertEquals(127, input.readUnsignedByte());
            Assertions.assertEquals(128, input.readUnsignedByte());
            Assertions.assertEquals(255, input.readUnsignedByte());
        }

        Assertions.assertThrows(EOFException.class, () -> new TestAbstractInput().readUnsignedByte());
    }

    @Test
    public void testReadByte() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF})) {
            Assertions.assertEquals(0, input.readByte());
            Assertions.assertEquals(127, input.readByte());
            Assertions.assertEquals(-128, input.readByte());
            Assertions.assertEquals(-1, input.readByte());
        }
    }

    @Test
    public void testReadBytes() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0, 1, 2, 3})) {
            Assertions.assertArrayEquals(new byte[]{0, 0, 1, 0}, input.readBytes(new byte[4], 1, 2));
            Assertions.assertArrayEquals(new byte[]{2, 3}, input.readBytes(new byte[2]));
        }
    }

    @Test
    public void testReadUnsignedLEB128() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26})) {
            Assertions.assertEquals(0, input.readUnsignedLEB128());
            Assertions.assertEquals(1, input.readUnsignedLEB128());
            Assertions.assertEquals(127, input.readUnsignedLEB128());
            Assertions.assertEquals(2147483647, input.readUnsignedLEB128());
            Assertions.assertEquals(2147483648L, input.readUnsignedLEB128());
            Assertions.assertEquals(4294967295L, input.readUnsignedLEB128());
            Assertions.assertEquals(624485, input.readUnsignedLEB128());
        }
    }

    @Test
    public void testReadUnsignedLEB128Plus1() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10})) {
            Assertions.assertEquals(-1, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(0, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(1, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(127, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483647, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(2147483648L, input.readUnsignedLEB128Plus1());
            Assertions.assertEquals(4294967295L, input.readUnsignedLEB128Plus1());
        }
    }

    @Test
    public void testReadSignedLEB128() throws IOException {
        try (Input input = new TestAbstractInput(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59})) {
            Assertions.assertEquals(0, input.readSignedLEB128());
            Assertions.assertEquals(1, input.readSignedLEB128());
            Assertions.assertEquals(-1, input.readSignedLEB128());
            Assertions.assertEquals(2147483647, input.readSignedLEB128());
            Assertions.assertEquals(-2147483648, input.readSignedLEB128());
            Assertions.assertEquals(-624485, input.readSignedLEB128());
        }
    }
    
    private static final class TestAbstractInput extends AbstractInput {
        private TestAbstractInput(byte... bytes) {
            super(bytes);
        }

        @Override
        public int readUnsignedShort() {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public long readUnsignedInt() {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public BigInteger readUnsignedLong() {
            throw new UnsupportedOperationException("not implemented");
        }
    }
}
