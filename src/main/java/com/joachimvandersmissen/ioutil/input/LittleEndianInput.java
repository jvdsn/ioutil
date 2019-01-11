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
 * Reads data in a little endian fashion.
 *
 * @author Joachim Vandersmissen
 */
public class LittleEndianInput extends AbstractInput {
    public LittleEndianInput(InputStream inputStream) {
        super(inputStream);
    }

    public LittleEndianInput(byte... bytes) {
        super(bytes);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        int b1 = this.readUnsignedByte();
        int b2 = this.readUnsignedByte();
        return b2 << 8 | b1;
    }

    @Override
    public long readUnsignedInt() throws IOException {
        long b1 = this.readUnsignedByte();
        long b2 = this.readUnsignedByte();
        long b3 = this.readUnsignedByte();
        long b4 = this.readUnsignedByte();
        return b4 << 24 | b3 << 16 | b2 << 8 | b1;
    }

    @Override
    public BigInteger readUnsignedLong() throws IOException {
        byte[] bytes = this.readBytes(new byte[8]);

        // Reverse endiannes
        for (int i = 0; i < 4; i++) {
            byte tmp = bytes[i];
            bytes[i] = bytes[7 - i];
            bytes[7 - i] = tmp;
        }

        return new BigInteger(1, bytes);
    }
}
