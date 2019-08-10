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

import java.io.IOException;
import java.math.BigInteger;

/**
 * An abstract reader.
 *
 * @author Joachim Vandersmissen
 */
public abstract class AbstractReader implements Reader {
    protected boolean littleEndian;

    /**
     * Constructs a new abstract reader.
     *
     * @param littleEndian whether the reader should read in a little endian way
     */
    protected AbstractReader(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) this.readUnsignedByte();
    }

    @Override
    public byte[] readBytes(byte... bytes) throws IOException {
        return this.readBytes(bytes, 0, bytes.length);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        int b1 = this.readUnsignedByte();
        int b2 = this.readUnsignedByte();
        return this.littleEndian ? b2 << 8 | b1 : b1 << 8 | b2;
    }

    @Override
    public short readShort() throws IOException {
        return (short) this.readUnsignedShort();
    }

    @Override
    public long readUnsignedInt() throws IOException {
        long b1 = this.readUnsignedByte();
        long b2 = this.readUnsignedByte();
        long b3 = this.readUnsignedByte();
        long b4 = this.readUnsignedByte();
        return this.littleEndian ? b4 << 24 | b3 << 16 | b2 << 8 | b1 : b1 << 24 | b2 << 16 | b3 << 8 | b4;

    }

    @Override
    public int readInt() throws IOException {
        return (int) this.readUnsignedInt();
    }

    @Override
    public BigInteger readUnsignedLong() throws IOException {
        byte[] bytes = this.readBytes(new byte[8]);
        if (this.littleEndian) {
            // Reverse endiannes because BigInteger is big endian.
            for (int i = 0; i < 4; i++) {
                byte tmp = bytes[i];
                bytes[i] = bytes[7 - i];
                bytes[7 - i] = tmp;
            }
        }

        return new BigInteger(1, bytes);
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
}
