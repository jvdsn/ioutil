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

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Writes endian-independent data.
 *
 * @author Joachim Vandersmissen
 */
public abstract class AbstractOutput implements Output {
    protected final OutputStream outputStream;
    protected int position;

    /**
     * Constructs a new abstract output.
     *
     * @param outputStream The output stream to write to.
     */
    protected AbstractOutput(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public int position() {
        return this.position;
    }

    @Override
    public void writeUnsignedByte(int b) throws IOException {
        this.outputStream.write(b);
        this.position++;
    }

    @Override
    public void writeByte(byte b) throws IOException {
        this.writeUnsignedByte(b);
    }

    @Override
    public void writeBytes(byte[] bytes, int start, int length) throws IOException {
        this.outputStream.write(bytes, start, length);
    }

    @Override
    public void writeBytes(byte... bytes) throws IOException {
        this.outputStream.write(bytes);
    }

    @Override
    public void writeShort(short s) throws IOException {
        this.writeUnsignedShort(s);
    }

    @Override
    public void writeInt(int i) throws IOException {
        this.writeUnsignedInt(i);
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

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}
