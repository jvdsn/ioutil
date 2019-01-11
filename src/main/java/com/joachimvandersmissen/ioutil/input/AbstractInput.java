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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads endian-independent data.
 *
 * @author Joachim Vandersmissen
 */
public abstract class AbstractInput implements Input {
    protected final InputStream inputStream;
    protected int position;

    protected AbstractInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    protected AbstractInput(byte... bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    @Override
    public int position() {
        return this.position;
    }

    @Override
    public int readUnsignedByte() throws IOException {
        int i = this.inputStream.read();
        if (i < 0) {
            throw new EOFException("end of stream");
        }

        this.position++;
        return i;
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) this.readUnsignedByte();
    }

    @Override
    public byte[] readBytes(byte[] bytes, int start, int length) throws IOException {
        for (int i = start; i < start + length; i++) {
            bytes[i] = this.readByte();
        }

        return bytes;
    }

    @Override
    public byte[] readBytes(byte... bytes) throws IOException {
        return this.readBytes(bytes, 0, bytes.length);
    }

    @Override
    public short readShort() throws IOException {
        return (short) this.readUnsignedShort();
    }

    @Override
    public int readInt() throws IOException {
        return (int) this.readUnsignedInt();
    }

    @Override
    public long readLong() throws IOException {
        return this.readUnsignedLong().longValue();
    }

    @Override
    public char readChar() throws IOException {
        return (char) this.readUnsignedShort();
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public long readUnsignedLEB128() throws IOException {
        long result = 0;
        long i = 0;
        int b;
        do {
            b = this.readUnsignedByte();
            // The 7 last bits in the byte are the 7 most significant ones as of yet
            result |= (b & 0b01111111L) << i;
            i += 7;
            // If the first bit is not set, no more data is coming
        } while ((b & 0b10000000) != 0);
        return result;
    }

    @Override
    public long readUnsignedLEB128Plus1() throws IOException {
        return this.readUnsignedLEB128() - 1;
    }

    @Override
    public int readSignedLEB128() throws IOException {
        int result = 0;
        int i = 0;
        int b;
        do {
            b = this.readUnsignedByte();
            // The 7 last bits in the byte are the 7 most significant ones as of yet
            result |= (b & 0b01111111) << i;
            i += 7;
            // If the first bit is not set, no more data is coming
        } while ((b & 0b10000000) != 0);
        // If the last sign bit is set, sign extend
        if (i < 31 && (b & 0b01000000) != 0) {
            result |= 0b11111111111111111111111111111111 << i;
        }

        return result;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }
}
