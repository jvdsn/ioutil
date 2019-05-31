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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * Reads data in a big endian fashion.
 *
 * @author Joachim Vandersmissen
 */
public class BigEndianInput extends AbstractInput {
    /**
     * Constructs a new big endian input.
     *
     * @param inputStream The input stream to read from.
     */
    public BigEndianInput(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * Constructs a new big endian input.
     *
     * @param bytes The bytes to read from.
     */
    public BigEndianInput(byte... bytes) {
        super(bytes);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        int b1 = this.readUnsignedByte();
        int b2 = this.readUnsignedByte();
        return b1 << 8 | b2;
    }

    @Override
    public long readUnsignedInt() throws IOException {
        long b1 = this.readUnsignedByte();
        long b2 = this.readUnsignedByte();
        long b3 = this.readUnsignedByte();
        long b4 = this.readUnsignedByte();
        return b1 << 24 | b2 << 16 | b3 << 8 | b4;
    }

    @Override
    public BigInteger readUnsignedLong() throws IOException {
        return new BigInteger(1, this.readBytes(new byte[8]));
    }
}
