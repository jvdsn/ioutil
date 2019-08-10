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
 * A generic writer.
 *
 * @author Joachim Vandersmissen
 */
public interface Writer extends AutoCloseable {
    /**
     * Writes an unsigned byte (8-bit integer).
     *
     * @param b the unsigned byte
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedByte(int b) throws IOException;

    /**
     * Writes a signed byte (8-bit integer).
     *
     * @param b the byte
     * @throws EOFException if no more data can be written
     */
    void writeByte(byte b) throws IOException;

    /**
     * Writes length signed bytes from an array, starting at start (inclusive).
     *
     * @param bytes  the array to write the bytes from
     * @param start  the start position (inclusive)
     * @param length the amount of bytes to write
     * @throws EOFException if no more data can be written
     */
    void writeBytes(byte[] bytes, int start, int length) throws IOException;

    /**
     * Writes signed bytes from an array.
     *
     * @param bytes the array to write the bytes from
     * @throws EOFException if no more data can be written
     */
    void writeBytes(byte... bytes) throws IOException;

    /**
     * Writes an unsigned short (16-bit integer).
     *
     * @param s the unsigned short
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedShort(int s) throws IOException;

    /**
     * Writes a signed short (16-bit integer).
     *
     * @param s the short
     * @throws EOFException if no more data can be written
     */
    void writeShort(short s) throws IOException;

    /**
     * Writes an unsigned int (32-bit integer).
     *
     * @param i the unsigned int
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedInt(long i) throws IOException;

    /**
     * Writes a signed int (32-bit integer).
     *
     * @param i the int
     * @throws EOFException if no more data can be written
     */
    void writeInt(int i) throws IOException;

    /**
     * Writes an unsigned long (64-bit integer).
     *
     * @param l the unsigned long
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedLong(BigInteger l) throws IOException;

    /**
     * Writes a signed long (64-bit integer).
     *
     * @param l the long
     * @throws EOFException if no more data can be written
     */
    void writeLong(long l) throws IOException;

    /**
     * Writes a char (unsigned 16-bit integer).
     *
     * @param c the char
     * @throws EOFException if no more data can be written
     */
    void writeChar(char c) throws IOException;

    /**
     * Writes a float (IEEE 754 binary32).
     *
     * @param f the float
     * @throws EOFException if no more data can be written
     */
    void writeFloat(float f) throws IOException;

    /**
     * Writes a double (IEEE 754 binary64).
     *
     * @param d the double
     * @throws EOFException if no more data can be written
     */
    void writeDouble(double d) throws IOException;

    /**
     * Writes an unsigned Little Endian Base 128 (32 bit integer decoded).
     *
     * @param i the decoded unsigned int
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedLEB128(long i) throws IOException;

    /**
     * Writes an unsigned Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @param i the decoded unsigned int
     * @throws EOFException if no more data can be written
     */
    void writeUnsignedLEB128Plus1(long i) throws IOException;

    /**
     * Writes a signed Little Endian Base 128 plus 1 (32 bit integer decoded).
     *
     * @param i the decoded signed int
     * @throws EOFException if no more data can be written
     */
    void writeSignedLEB128(int i) throws IOException;

    @Override
    void close() throws IOException;
}
