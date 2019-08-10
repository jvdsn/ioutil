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

import java.io.IOException;
import java.math.BigInteger;

/**
 * An abstract writer.
 *
 * @author Joachim Vandersmissen
 */
public abstract class AbstractWriter implements Writer {
    public static final BigInteger MASK = BigInteger.valueOf(0xFF);

    protected boolean littleEndian;

    /**
     * Constructs a new abstract writer.
     *
     * @param littleEndian whether the writer should write in a little endian way
     */
    protected AbstractWriter(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public void writeByte(byte b) throws IOException {
        this.writeUnsignedByte(b);
    }

    @Override
    public void writeBytes(byte... bytes) throws IOException {
        this.writeBytes(bytes, 0, bytes.length);
    }

    @Override
    public void writeUnsignedShort(int s) throws IOException {
        if (this.littleEndian) {
            this.writeUnsignedByte(s & 0xFF);
            this.writeUnsignedByte(s >> 8 & 0xFF);
        } else {
            this.writeUnsignedByte(s >> 8 & 0xFF);
            this.writeUnsignedByte(s & 0xFF);
        }
    }

    @Override
    public void writeShort(short s) throws IOException {
        this.writeUnsignedShort(s);
    }

    @Override
    public void writeUnsignedInt(long i) throws IOException {
        if (this.littleEndian) {
            this.writeUnsignedByte((int) (i & 0xFF));
            this.writeUnsignedByte((int) (i >> 8 & 0xFF));
            this.writeUnsignedByte((int) (i >> 16 & 0xFF));
            this.writeUnsignedByte((int) (i >> 24 & 0xFF));
        } else {
            this.writeUnsignedByte((int) (i >> 24 & 0xFF));
            this.writeUnsignedByte((int) (i >> 16 & 0xFF));
            this.writeUnsignedByte((int) (i >> 8 & 0xFF));
            this.writeUnsignedByte((int) (i & 0xFF));
        }
    }

    @Override
    public void writeInt(int i) throws IOException {
        this.writeUnsignedInt(i);
    }

    @Override
    public void writeUnsignedLong(BigInteger l) throws IOException {
        if (this.littleEndian) {
            this.writeUnsignedByte(l.and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(8).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(16).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(24).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(32).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(40).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(48).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(56).and(MASK).intValue());
        } else {
            this.writeUnsignedByte(l.shiftRight(56).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(48).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(40).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(32).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(24).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(16).and(MASK).intValue());
            this.writeUnsignedByte(l.shiftRight(8).and(MASK).intValue());
            this.writeUnsignedByte(l.and(MASK).intValue());
        }
    }

    @Override
    public void writeLong(long l) throws IOException {
        this.writeUnsignedLong(BigInteger.valueOf(l));
    }

    @Override
    public void writeChar(char c) throws IOException {
        this.writeUnsignedShort(c);
    }

    @Override
    public void writeFloat(float f) throws IOException {
        this.writeInt(Float.floatToRawIntBits(f));
    }

    @Override
    public void writeDouble(double d) throws IOException {
        this.writeLong(Double.doubleToRawLongBits(d));
    }

    @Override
    public void writeUnsignedLEB128(long i) throws IOException {
        do {
            int b = (int) (i & 0b01111111);
            i >>>= 7;
            // Not the last byte so set the first bit
            if (i != 0) {
                b |= 0b10000000;
            }
            this.writeUnsignedByte(b);
        } while (i != 0);
    }

    @Override
    public void writeUnsignedLEB128Plus1(long i) throws IOException {
        this.writeUnsignedLEB128(i + 1);
    }

    @Override
    public void writeSignedLEB128(int i) throws IOException {
        int b;
        do {
            b = i & 0b01111111;
            i >>= 7;
            // Not the last byte so set the first bit
            if ((i != 0 || (b & 0b01000000) != 0) && (i != -1 || (b & 0b01000000) == 0)) {
                b |= 0b10000000;
            }
            this.writeUnsignedByte(b);
        } while ((i != 0 || (b & 0b01000000) != 0) && (i != -1 || (b & 0b01000000) == 0));
    }
}
