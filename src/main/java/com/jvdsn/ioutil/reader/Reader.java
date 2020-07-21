/*
 * Copyright 2020 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.jvdsn.ioutil.reader;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * A generic reader.
 *
 * @author Joachim Vandersmissen
 */
public interface Reader extends AutoCloseable {
    /**
     * Reads an unsigned byte (8-bit integer).
     *
     * @return the unsigned byte
     * @throws EOFException if no more data can be read
     */
    int readUnsignedByte() throws IOException;

    /**
     * Reads a signed byte (8-bit integer).
     *
     * @return the byte
     * @throws EOFException if no more data can be read
     */
    byte readByte() throws IOException;

    /**
     * Reads length signed bytes in an array, starting at start (inclusive).
     *
     * @param bytes  the array to read the bytes in
     * @param start  the start position (inclusive)
     * @param length the amount of bytes to read
     * @return the byte array
     * @throws EOFException if no more data can be read
     */
    byte[] readBytes(byte[] bytes, int start, int length) throws IOException;

    /**
     * Reads signed bytes in an array.
     *
     * @param bytes the array to read the bytes in
     * @return the byte array
     * @throws EOFException if no more data can be read
     */
    byte[] readBytes(byte... bytes) throws IOException;

    /**
     * Reads an unsigned short (16-bit integer).
     *
     * @return the unsigned short
     * @throws EOFException if no more data can be read
     */
    int readUnsignedShort() throws IOException;

    /**
     * Reads a signed short (16-bit integer).
     *
     * @return the short
     * @throws EOFException if no more data can be read
     */
    short readShort() throws IOException;

    /**
     * Reads an unsigned int (32-bit integer).
     *
     * @return the unsigned int
     * @throws EOFException if no more data can be read
     */
    long readUnsignedInt() throws IOException;

    /**
     * Reads a signed int (32-bit integer).
     *
     * @return the int
     * @throws EOFException if no more data can be read
     */
    int readInt() throws IOException;

    /**
     * Reads an unsigned long (64-bit integer).
     *
     * @return the unsigned long
     * @throws EOFException if no more data can be read
     */
    BigInteger readUnsignedLong() throws IOException;

    /**
     * Reads a signed long (64-bit integer).
     *
     * @return the long
     * @throws EOFException if no more data can be read
     */
    long readLong() throws IOException;

    /**
     * Reads a char (unsigned 16-bit integer).
     *
     * @return the char
     * @throws EOFException if no more data can be read
     */
    char readChar() throws IOException;

    /**
     * Reads a float (IEEE 754 binary32).
     *
     * @return the float
     * @throws EOFException if no more data can be read
     */
    float readFloat() throws IOException;

    /**
     * Reads a double (IEEE 754 binary64).
     *
     * @return the double
     * @throws EOFException if no more data can be read
     */
    double readDouble() throws IOException;

    /**
     * Reads an unsigned Little Endian Base 128 (32 bit integer decoded).
     *
     * @return the decoded unsigned int
     * @throws EOFException if no more data can be read
     */
    long readUnsignedLEB128() throws IOException;

    /**
     * Reads an unsigned Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @return the decoded unsigned int
     * @throws EOFException if no more data can be read
     */
    long readUnsignedLEB128Plus1() throws IOException;

    /**
     * Reads a signed Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @return the decoded signed int
     * @throws EOFException if no more data can be read
     */
    int readSignedLEB128() throws IOException;

    @Override
    void close() throws IOException;
}
