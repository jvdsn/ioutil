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
 * Writes data in a little endian fashion.
 *
 * @author Joachim Vandersmissen
 */
public class LittleEndianOutput extends AbstractOutput {
    /**
     * Constructs a new little endian output.
     *
     * @param outputStream The output stream to write to.
     */
    public LittleEndianOutput(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void writeUnsignedShort(int s) throws IOException {
        this.writeUnsignedByte(s & 0xFF);
        this.writeUnsignedByte(s >> 8 & 0xFF);
    }

    @Override
    public void writeUnsignedInt(long i) throws IOException {
        this.writeUnsignedByte((int) (i & 0xFF));
        this.writeUnsignedByte((int) (i >> 8 & 0xFF));
        this.writeUnsignedByte((int) (i >> 16 & 0xFF));
        this.writeUnsignedByte((int) (i >> 24 & 0xFF));
    }

    @Override
    public void writeUnsignedLong(BigInteger l) throws IOException {
        this.writeUnsignedByte(l.and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(8).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(16).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(24).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(32).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(40).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(48).and(BigInteger.valueOf(0xFF)).intValue());
        this.writeUnsignedByte(l.shiftRight(56).and(BigInteger.valueOf(0xFF)).intValue());
    }
}
