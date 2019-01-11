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
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * @author Joachim Vandersmissen
 */
public class AbstractOutputTest {
    @Test
    public void testPosition() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            Assertions.assertEquals(0, output.position());
            output.writeUnsignedByte(0);
            Assertions.assertEquals(1, output.position());
        }
    }

    @Test
    public void testWriteUnsignedByte() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeUnsignedByte(0);
            output.writeUnsignedByte(127);
            output.writeUnsignedByte(128);
            output.writeUnsignedByte(255);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteByte() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeByte((byte) 0);
            output.writeByte((byte) 127);
            output.writeByte((byte) -128);
            output.writeByte((byte) -1);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x7F, (byte) 0x80, (byte) 0xFF}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeBytes(new byte[]{-1, 0, 1, -1}, 1, 2);
            output.writeBytes((byte) 2, (byte) 3);
        }
        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteUnsignedLEB128() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeUnsignedLEB128(0);
            output.writeUnsignedLEB128(1);
            output.writeUnsignedLEB128(127);
            output.writeUnsignedLEB128(2147483647);
            output.writeUnsignedLEB128(2147483648L);
            output.writeUnsignedLEB128(4294967295L);
            output.writeUnsignedLEB128(624485);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F, (byte) 0xE5, (byte) 0x8E, 0x26}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteUnsignedLEB128Plus1() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeUnsignedLEB128Plus1(-1);
            output.writeUnsignedLEB128Plus1(0);
            output.writeUnsignedLEB128Plus1(1);
            output.writeUnsignedLEB128Plus1(127);
            output.writeUnsignedLEB128Plus1(2147483647);
            output.writeUnsignedLEB128Plus1(2147483648L);
            output.writeUnsignedLEB128Plus1(4294967295L);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x02, (byte) 0x80, 0x01, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x81, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x08, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, 0x10}, byteArrayOutputStream.toByteArray());
    }

    @Test
    public void testWriteSignedLEB128() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new TestAbstractOutput(byteArrayOutputStream)) {
            output.writeSignedLEB128(0);
            output.writeSignedLEB128(1);
            output.writeSignedLEB128(-1);
            output.writeSignedLEB128(2147483647);
            output.writeSignedLEB128(-2147483648);
            output.writeSignedLEB128(-624485);
        }
        Assertions.assertArrayEquals(new byte[]{0x00, 0x01, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x07, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x78, (byte) 0x9B, (byte) 0xF1, 0x59}, byteArrayOutputStream.toByteArray());
    }

   private final class TestAbstractOutput extends AbstractOutput {
        private TestAbstractOutput(OutputStream outputStream) {
            super(outputStream);
        }

        @Override
        public void writeUnsignedShort(int s) {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public void writeUnsignedInt(long i) {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public void writeUnsignedLong(BigInteger l) {
            throw new UnsupportedOperationException("not implemented");
        }
    }
}
