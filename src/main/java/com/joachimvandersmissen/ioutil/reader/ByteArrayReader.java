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

import java.io.EOFException;
import java.io.IOException;

/**
 * Reads from a byte array.
 *
 * @author Joachim Vandersmissen
 */
public class ByteArrayReader extends AbstractReader {
    protected final byte[] bytes;
    protected final int start;
    protected final int length;
    protected int pos;

    /**
     * Constructs a new byte array reader.
     *
     * @param littleEndian whether the reader should read in a little endian way
     * @param bytes        the byte array to read from
     * @param start        the position to start reading at
     * @param length       the amount of bytes to read before throwing EOFException
     */
    protected ByteArrayReader(boolean littleEndian, byte[] bytes, int start, int length) {
        super(littleEndian);
        this.bytes = bytes;
        this.start = start;
        this.length = length;
        this.pos = start;
    }

    /**
     * Constructs a new byte array reader reading from an entire byte array.
     *
     * @param littleEndian whether the reader should read in a little endian way
     * @param bytes        the byte array to read from
     */
    protected ByteArrayReader(boolean littleEndian, byte... bytes) {
        this(littleEndian, bytes, 0, bytes.length);
    }

    /**
     * Constructs a new little endian byte array reader reading from an entire byte array.
     *
     * @param bytes the byte array to read from
     * @return The new byte array reader
     */
    public static ByteArrayReader littleEndian(byte... bytes) {
        return new ByteArrayReader(true, bytes);
    }

    /**
     * Constructs a new big endian byte array reader reading from an entire byte array.
     *
     * @param bytes the byte array to read from
     * @return The new byte array reader
     */
    public static ByteArrayReader bigEndian(byte... bytes) {
        return new ByteArrayReader(false, bytes);
    }

    /**
     * Constructs a new little endian byte array reader.
     *
     * @param bytes  the byte array to read from
     * @param start  the position to start reading at
     * @param length the amount of bytes to read before throwing EOFException
     * @return The new byte array reader
     */
    public static ByteArrayReader littleEndian(byte[] bytes, int start, int length) {
        return new ByteArrayReader(true, bytes, start, length);
    }

    /**
     * Constructs a new big endian byte array reader.
     *
     * @param bytes  the byte array to read from
     * @param start  the position to start reading at
     * @param length the amount of bytes to read before throwing EOFException
     * @return The new byte array reader
     */
    public static ByteArrayReader bigEndian(byte[] bytes, int start, int length) {
        return new ByteArrayReader(false, bytes, start, length);
    }

    /**
     * Returns the position of the reader in the byte array.
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
    public int readUnsignedByte() throws IOException {
        // Also check for the actual byte array length, just to be sure.
        if (this.pos < this.bytes.length && this.pos - this.start < this.length) {
            return this.bytes[this.pos++] & 0xFF;
        }

        this.pos = this.start + this.length;
        throw new EOFException("End of stream.");
    }

    @Override
    public byte[] readBytes(byte[] bytes, int start, int length) throws IOException {
        // Also check for the actual byte array length, just to be sure.
        if (this.pos < this.bytes.length - length + 1 && this.pos - this.start < this.length - length + 1) {
            System.arraycopy(this.bytes, this.pos, bytes, start, length);
            this.pos += length;
            return bytes;
        }

        this.pos = this.start + this.length;
        throw new EOFException("End of stream.");
    }

    @Override
    public void close() throws IOException {
        this.pos = this.start + this.length;
    }
}
