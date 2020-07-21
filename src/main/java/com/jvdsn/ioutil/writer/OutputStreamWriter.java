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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes to an output stream.
 *
 * @author Joachim Vandersmissen
 */
public class OutputStreamWriter extends AbstractWriter {
    protected final OutputStream outputStream;

    /**
     * Constructs a new output stream writer.
     *
     * @param endianness   the endianness of the writer
     * @param outputStream the output stream to write to
     */
    public OutputStreamWriter(Endianness endianness, OutputStream outputStream) {
        super(endianness);
        this.outputStream = outputStream;
    }

    @Override
    public void writeUnsignedByte(int b) throws IOException {
        this.outputStream.write(b);
    }

    @Override
    public void writeBytes(byte[] bytes, int start, int length) throws IOException {
        this.outputStream.write(bytes, start, length);
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }

    @Override
    public void writeBytes(byte... bytes) throws IOException {
        this.outputStream.write(bytes);
    }
}
