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
import java.math.BigInteger;

/**
 * Reads data.
 *
 * @author Joachim Vandersmissen
 */
public interface Input extends AutoCloseable {
    /**
     * Returns the amount of bytes read by this input since its creation.
     *
     * @return The amount of bytes.
     */
    int position();

    /**
     * Reads an unsigned byte (8-bit integer).
     *
     * @return The unsigned byte.
     */
    int readUnsignedByte() throws IOException;

    /**
     * Reads a signed byte (8-bit integer).
     *
     * @return The byte.
     */
    byte readByte() throws IOException;

    /**
     * Reads length signed bytes and stores them in an array, starting at start (inclusive).
     *
     * @param bytes  The array to store the read bytes in.
     * @param start  The start position (inclusive).
     * @param length The amount of bytes to read.
     * @return The array.
     */
    byte[] readBytes(byte[] bytes, int start, int length) throws IOException;

    /**
     * Reads signed bytes and stores them in an array.
     *
     * @param bytes The array to store the read bytes in.
     * @return The array.
     */
    byte[] readBytes(byte... bytes) throws IOException;

    /**
     * Reads an unsigned short (16-bit integer).
     *
     * @return The unsigned short.
     */
    int readUnsignedShort() throws IOException;

    /**
     * Reads a signed short (16-bit integer).
     *
     * @return The short.
     */
    short readShort() throws IOException;

    /**
     * Reads an unsigned int (32-bit integer).
     *
     * @return The unsigned int.
     */
    long readUnsignedInt() throws IOException;

    /**
     * Reads a signed int (32-bit integer).
     *
     * @return The int.
     */
    int readInt() throws IOException;

    /**
     * Reads an unsigned long (64-bit integer).
     *
     * @return The unsigned long.
     */
    BigInteger readUnsignedLong() throws IOException;

    /**
     * Reads a signed long (64-bit integer).
     *
     * @return The long.
     */
    long readLong() throws IOException;

    /**
     * Reads a char (unsigned 16-bit integer).
     *
     * @return The char.
     */
    char readChar() throws IOException;

    /**
     * Reads a float (IEEE 754 binary32).
     *
     * @return The float.
     */
    float readFloat() throws IOException;

    /**
     * Reads a double (IEEE 754 binary64).
     *
     * @return The double.
     */
    double readDouble() throws IOException;

    /**
     * Reads an unsigned Little Endian Base 128 (32 bit integer decoded).
     *
     * @return The decoded unsigned int.
     */
    long readUnsignedLEB128() throws IOException;

    /**
     * Reads an unsigned Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @return The decoded unsigned int.
     */
    long readUnsignedLEB128Plus1() throws IOException;

    /**
     * Reads a signed Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @return The decoded signed int.
     */
    int readSignedLEB128() throws IOException;

    @Override
    void close() throws IOException;
}
