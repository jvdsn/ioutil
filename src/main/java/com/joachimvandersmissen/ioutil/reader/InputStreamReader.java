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
import java.io.InputStream;

/**
 * Reads from an input stream.
 *
 * @author Joachim Vandersmissen
 */
public class InputStreamReader extends AbstractReader {
    protected final InputStream inputStream;

    /**
     * Constructs a new input stream reader.
     *
     * @param littleEndian whether the reader should read in a little endian way
     * @param inputStream  the input stream to read from
     */
    protected InputStreamReader(boolean littleEndian, InputStream inputStream) {
        super(littleEndian);
        this.inputStream = inputStream;
    }

    /**
     * Constructs a new little endian input stream reader.
     *
     * @param inputStream the input stream to read from
     * @return The new input stream reader
     */
    public static InputStreamReader littleEndian(InputStream inputStream) {
        return new InputStreamReader(true, inputStream);
    }

    /**
     * Constructs a new big endian input stream reader.
     *
     * @param inputStream the input stream to read from
     * @return The new input stream reader
     */
    public static InputStreamReader bigEndian(InputStream inputStream) {
        return new InputStreamReader(false, inputStream);
    }

    @Override
    public int readUnsignedByte() throws IOException {
        int i = this.inputStream.read();
        if (i == -1) {
            throw new EOFException("End of stream.");
        }

        return i;
    }

    @Override
    public byte[] readBytes(byte[] bytes, int start, int length) throws IOException {
        if (this.inputStream.read(bytes, start, length) == -1) {
            throw new EOFException("End of stream.");
        }

        return bytes;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }

    @Override
    public byte[] readBytes(byte... bytes) throws IOException {
        if (this.inputStream.read(bytes) == -1) {
            throw new EOFException("End of stream.");
        }

        return bytes;
    }
}
