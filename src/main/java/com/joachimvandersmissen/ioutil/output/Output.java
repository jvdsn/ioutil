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
import java.math.BigInteger;

/**
 * Writes data.
 *
 * @author Joachim Vandersmissen
 */
public interface Output extends AutoCloseable {
    /**
     * Returns the amount of bytes written by this output since its creation.
     *
     * @return The amount of bytes.
     */
    int position();

    /**
     * Writes an unsigned byte (8-bit integer).
     *
     * @param b The unsigned byte.
     */
    void writeUnsignedByte(int b) throws IOException;

    /**
     * Writes a signed byte (8-bit integer).
     *
     * @param b The byte.
     */
    void writeByte(byte b) throws IOException;

    /**
     * Writes length signed bytes from an array, starting at start (inclusive).
     *
     * @param bytes  The array of bytes to write from.
     * @param start  The start position (inclusive).
     * @param length The amount of bytes to read.
     */
    void writeBytes(byte[] bytes, int start, int length) throws IOException;

    /**
     * Writes all signed bytes from an array.
     *
     * @param bytes The array of bytes to write from.
     */
    void writeBytes(byte... bytes) throws IOException;

    /**
     * Writes an unsigned short (16-bit integer).
     *
     * @param s The unsigned short.
     */
    void writeUnsignedShort(int s) throws IOException;

    /**
     * Writes a signed short (16-bit integer).
     *
     * @param s The short.
     */
    void writeShort(short s) throws IOException;

    /**
     * Writes an unsigned int (32-bit integer).
     *
     * @param i The unsigned int.
     */
    void writeUnsignedInt(long i) throws IOException;

    /**
     * Writes a signed int (32-bit integer).
     *
     * @param i The int.
     */
    void writeInt(int i) throws IOException;

    /**
     * Writes an unsigned long (64-bit integer).
     *
     * @param l The unsigned long.
     */
    void writeUnsignedLong(BigInteger l) throws IOException;

    /**
     * Writes a signed long (64-bit integer).
     *
     * @param l The long.
     */
    void writeLong(long l) throws IOException;

    /**
     * Writes a char (unsigned 16-bit integer).
     *
     * @param c The char.
     */
    void writeChar(char c) throws IOException;

    /**
     * Writes a float (IEEE 754 binary32).
     *
     * @param f The float.
     */
    void writeFloat(float f) throws IOException;

    /**
     * Writes a double (IEEE 754 binary64).
     *
     * @param d The double.
     */
    void writeDouble(double d) throws IOException;

    /**
     * Writes an unsigned Little Endian Base 128 (32-bit integer decoded).
     *
     * @param i The 32-bit unsigned int.
     */
    void writeUnsignedLEB128(long i) throws IOException;

    /**
     * Writes an unsigned Little Endian Base 128 plus 1 (32-bit integer decoded).
     *
     * @param i The 32-bit unsigned int.
     */
    void writeUnsignedLEB128Plus1(long i) throws IOException;

    /**
     * Writes a signed Little Endian Base 128 (32-bit integer decoded).
     *
     * @param i The 32-bit signed int.
     */
    void writeSignedLEB128(int i) throws IOException;

    @Override
    void close() throws IOException;
}
