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

import java.io.EOFException;
import java.io.IOException;

/**
 * Writes to a byte array.
 *
 * @author Joachim Vandersmissen
 */
public class ByteArrayWriter extends AbstractWriter {
    protected final byte[] bytes;
    protected final int start;
    protected final int length;
    protected int pos;

    /**
     * Constructs a new byte array writer.
     *
     * @param endianness the endianness of the writer
     * @param bytes      the byte array to write to
     * @param start      the position to start writing at
     * @param length     the amount of bytes to write before throwing EOFException
     */
    public ByteArrayWriter(Endianness endianness, byte[] bytes, int start, int length) {
        super(endianness);
        this.bytes = bytes;
        this.start = start;
        this.length = length;
        this.pos = start;
    }

    /**
     * Constructs a new byte array writer writing to an entire byte array.
     *
     * @param endianness the endianness of the writer
     * @param bytes      the byte array to write to
     */
    public ByteArrayWriter(Endianness endianness, byte... bytes) {
        this(endianness, bytes, 0, bytes.length);
    }

    /**
     * Returns the position of the writer in the byte array.
     *
     * @return the position
     */
    public int position() {
        return this.pos;
    }

    /**
     * Returns the amount of bytes remaining in the byte array.
     *
     * @return the amount of bytes remaining
     */
    public int remaining() {
        return this.length - this.pos + this.start;
    }

    @Override
    public void writeUnsignedByte(int b) throws IOException {
        // Also check for the actual byte array length, just to be sure.
        if (this.pos < this.bytes.length && this.pos - this.start < this.length) {
            this.bytes[this.pos++] = (byte) b;
            return;
        }

        this.pos = this.bytes.length;
        throw new EOFException("End of stream.");
    }

    @Override
    public void writeBytes(byte[] bytes, int start, int length) throws IOException {
        // Also check for the actual byte array length, just to be sure.
        if (this.pos < this.bytes.length - length + 1 && this.pos - this.start < this.length - length + 1) {
            System.arraycopy(bytes, start, this.bytes, this.pos, length);
            this.pos += length;
            return;
        }

        this.pos = this.bytes.length;
        throw new EOFException("End of stream.");
    }

    @Override
    public void close() throws IOException {
        this.pos = this.bytes.length;
    }
}
