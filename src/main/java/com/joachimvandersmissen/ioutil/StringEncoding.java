/*
 * Copyright 2019 Joachim Vandersmissen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.joachimvandersmissen.ioutil;

import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * Represents different string encodings.
 *
 * @author Joachim Vandersmissen
 */
public enum StringEncoding {
    UTF_8() {
        @Override
        public byte[] encode(String string) throws UTFDataFormatException {
            int length = string.length();
            // Up to 3 bytes encoded per char.
            byte[] data = new byte[length * 3];
            int j = 0;
            for (int i = 0; i < length; i++) {
                char c = string.charAt(i);
                // Code points in the range '\u0000' to '\u007F' are represented by a single byte.
                if (c <= '\u007F') {
                    data[j++] = (byte) (c & 0b01111111);
                }
                // Code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y:
                if (c >= '\u0080' && c <= '\u07FF') {
                    data[j++] = (byte) (0b11000000 | c >>> 6 & 0b00011111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z:
                // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                if (c >= '\u0800' && c <= '\uD7FF' || c >= '\uE000') {
                    data[j++] = (byte) (0b11100000 | c >>> 12 & 0b00001111);
                    data[j++] = (byte) (0b10000000 | c >>> 6 & 0b00111111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Encoding of surrogate pairs.
                if (c >= '\uD800' && c <= '\uDBFF') {
                    // Low surrogate.
                    char d = string.charAt(++i);
                    if (d <= '\uDBFF' || d >= '\uE000') {
                        throw new UTFDataFormatException("invalid low surrogate " + Integer.toBinaryString(d) + " at position " + i + " in " + string);
                    }
                    int codepoint = (c - 0xD800 << 10 | d - 0xDC00) + 0x10000;
                    data[j++] = (byte) (0b11110000 | codepoint >>> 18 & 0b00000111);
                    data[j++] = (byte) (0b10000000 | codepoint >>> 12 & 0b00111111);
                    data[j++] = (byte) (0b10000000 | codepoint >>> 6 & 0b00111111);
                    data[j++] = (byte) (0b10000000 | codepoint & 0b00111111);
                }
                if (c >= '\uDC00' && c <= '\uDFFF') {
                    throw new UTFDataFormatException("unexpected low surrogate " + Integer.toBinaryString(c) + " at position " + i + " in " + string);
                }
            }

            byte[] newData = new byte[j];
            System.arraycopy(data, 0, newData, 0, j);
            return newData;
        }

        @Override
        public String decode(byte... bytes) throws UTFDataFormatException {
            char[] chars = new char[bytes.length];
            int i = 0;
            int j = 0;
            while (j < bytes.length) {
                int x = bytes[j++] & 0xFF;
                if ((x & 0b10000000) == 0b00000000) {
                    // Code points in the range '\u0000' to '\u007F' are represented by a single byte.
                    chars[i++] = (char) (x & 0b01111111);
                } else if ((x & 0b11100000) == 0b11000000) {
                    // Code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }

                    chars[i++] = (char) ((x & 0b00011111) << 6 | y & 0b00111111);
                } else if ((x & 0b11110000) == 0b11100000) {
                    // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z.
                    // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }
                    if (x == 0b11101101 && (y & 0b00100000) != 0) {
                        if ((y & 0b00010000) == 0) {
                            throw new UTFDataFormatException("invalid encoded code point: high surrogate");
                        }

                        throw new UTFDataFormatException("invalid encoded code point: low surrogate");
                    }

                    chars[i++] = (char) ((x & 0b00001111) << 12 | (y & 0b00111111) << 6 | z & 0b00111111);
                } else if ((x & 0b11111000) == 0b11110000) {
                    // Code points in the range '\u10000' to '\u10FFFF' are represented by 4 bytes x, y, z, and w.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }
                    int w = bytes[j++] & 0xFF;
                    if ((w & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 4 format, expected 10xxxxxx but got " + Integer.toBinaryString(w));
                    }

                    // Decode and encode as surrogate pairs.
                    // The usage of + (plus) instead of | (binary or) in the low surrogate construction is intended and part of a hack.
                    chars[i++] = (char) (0b1101011111000000 + ((x & 0b00000011) << 8 | (y & 0b00111111) << 2 | (z & 0b00110000) >>> 4));
                    chars[i++] = (char) (0b1101110000000000 | (z & 0b00001111) << 6 | w & 0b00111111);
                } else {
                    throw new UTFDataFormatException("invalid byte 1 format, expected 0xxxxxxx, 110xxxxx, 1110xxxx or 11110xxx but got " + Integer.toBinaryString(x));
                }
            }

            return new String(chars, 0, i);
        }
    },
    CESU_8() {
        @Override
        public byte[] encode(String string) throws UTFDataFormatException {
            int length = string.length();
            // Up to 3 bytes encoded per char.
            byte[] data = new byte[length * 3];
            int j = 0;
            for (int i = 0; i < length; i++) {
                char c = string.charAt(i);
                // Code points in the range '\u0000' to '\u007F' are represented by a single byte.
                if (c <= '\u007F') {
                    data[j++] = (byte) (c & 0b01111111);
                }
                // Code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y:
                if (c >= '\u0080' && c <= '\u07FF') {
                    data[j++] = (byte) (0b11000000 | c >>> 6 & 0b00011111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z:
                // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                if (c >= '\u0800' && c <= '\uD7FF' || c >= '\uE000') {
                    data[j++] = (byte) (0b11100000 | c >>> 12 & 0b00001111);
                    data[j++] = (byte) (0b10000000 | c >>> 6 & 0b00111111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Encoding of surrogate pairs.
                if (c >= '\uD800' && c <= '\uDBFF') {
                    // Low surrogate.
                    char d = string.charAt(++i);
                    if (d <= '\uDBFF' || d >= '\uE000') {
                        throw new UTFDataFormatException("invalid low surrogate " + Integer.toBinaryString(d) + " at position " + i + " in " + string);
                    }
                    data[j++] = (byte) 0b11101101;
                    int top = c - 0xD800;
                    data[j++] = (byte) (0b10100000 | top & 0b1111000000);
                    data[j++] = (byte) (0b10000000 | top & 0b0000111111);
                    data[j++] = (byte) 0b11101101;
                    int bottom = d - 0xDC00;
                    data[j++] = (byte) (0b10110000 | bottom & 0b1111000000);
                    data[j++] = (byte) (0b10000000 | bottom & 0b0000111111);
                }
                if (c >= '\uDC00' && c <= '\uDFFF') {
                    throw new UTFDataFormatException("unexpected low surrogate " + Integer.toBinaryString(c) + " at position " + i + " in " + string);
                }
            }

            byte[] newData = new byte[j];
            System.arraycopy(data, 0, newData, 0, j);
            return newData;
        }

        @Override
        public String decode(byte... bytes) throws UTFDataFormatException {
            char[] chars = new char[bytes.length];
            int i = 0;
            int j = 0;
            while (j < bytes.length) {
                int x = bytes[j++] & 0xFF;
                if ((x & 0b10000000) == 0b00000000) {
                    // Code points in the range '\u0000' to '\u007F' are represented by a single byte.
                    chars[i++] = (char) (x & 0b01111111);
                } else if ((x & 0b11100000) == 0b11000000) {
                    // Code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }

                    chars[i++] = (char) (((x & 0b00011111) << 6) + (y & 0b00111111));
                } else if (x == 0b11101101) {
                    // Characters with code points above U+FFFF (so-called supplementary characters) are represented by separately encoding the two surrogate code units of their UTF-16 representation.
                    // Each of the surrogate code units is represented by three bytes.
                    // This means supplementary characters are represented by six bytes, x, y, z, a, b, and c.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11110000) != 0b10100000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 1010xxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }
                    int a = bytes[j++] & 0xFF;
                    if (a != 0b11101101) {
                        throw new UTFDataFormatException("invalid byte 4 format, expected 11101101 but got " + Integer.toBinaryString(a));
                    }
                    int b = bytes[j++] & 0xFF;
                    if ((b & 0b11110000) != 0b10110000) {
                        throw new UTFDataFormatException("invalid byte 5 format, expected 1011xxxx but got " + Integer.toBinaryString(b));
                    }
                    int c = bytes[j++] & 0xFF;
                    if ((c & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 6 format, expected 10xxxxxx but got " + Integer.toBinaryString(c));
                    }

                    // Decode and encode as surrogate pairs.
                    chars[i++] = (char) (0b1101100000000000 | (y & 0b00001111) << 6 | z & 0b00111111);
                    chars[i++] = (char) (0b1101110000000000 | (b & 0b00001111) << 6 | c & 0b00111111);
                } else if ((x & 0b11110000) == 0b11100000) {
                    // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z.
                    // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }

                    chars[i++] = (char) ((x & 0b00001111) << 12 | (y & 0b00111111) << 6 | z & 0b00111111);
                } else {
                    throw new UTFDataFormatException("invalid byte 1 format, expected 0xxxxxxx, 110xxxxx, 1110xxxx or 11101101 but got " + Integer.toBinaryString(x));
                }
            }

            return new String(chars, 0, i);
        }
    },
    MUTF_8() {
        @Override
        public byte[] encode(String string) throws UTFDataFormatException {
            int length = string.length();
            // Up to 3 bytes encoded per char
            byte[] data = new byte[length * 3];
            int j = 0;
            for (int i = 0; i < length; i++) {
                char c = string.charAt(i);
                // Code points in the range '\u0001' to '\u007F' are represented by a single byte.
                if (c >= '\u0001' && c <= '\u007F') {
                    data[j++] = (byte) (c & 0b01111111);
                }
                // The null code point ('\u0000') and code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y:
                if (c == '\u0000' || c >= '\u0080' && c <= '\u07FF') {
                    data[j++] = (byte) (0b11000000 | c >>> 6 & 0b00011111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z:
                // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                if (c >= '\u0800' && c <= '\uD7FF' || c >= '\uE000') {
                    data[j++] = (byte) (0b11100000 | c >>> 12 & 0b00001111);
                    data[j++] = (byte) (0b10000000 | c >>> 6 & 0b00111111);
                    data[j++] = (byte) (0b10000000 | c & 0b00111111);
                }
                // Encoding of surrogate pairs.
                if (c >= '\uD800' && c <= '\uDBFF') {
                    // Low surrogate.
                    char d = string.charAt(++i);
                    if (d <= '\uDBFF' || d >= '\uE000') {
                        throw new UTFDataFormatException("invalid low surrogate " + Integer.toBinaryString(d) + " at position " + i + " in " + string);
                    }
                    data[j++] = (byte) 0b11101101;
                    int top = c - 0xD800;
                    data[j++] = (byte) (0b10100000 | top & 0b1111000000);
                    data[j++] = (byte) (0b10000000 | top & 0b0000111111);
                    data[j++] = (byte) 0b11101101;
                    int bottom = d - 0xDC00;
                    data[j++] = (byte) (0b10110000 | bottom & 0b1111000000);
                    data[j++] = (byte) (0b10000000 | bottom & 0b0000111111);
                }
                if (c >= '\uDC00' && c <= '\uDFFF') {
                    throw new UTFDataFormatException("unexpected low surrogate " + Integer.toBinaryString(c) + " at position " + i + " in " + string);
                }
            }

            byte[] newData = new byte[j];
            System.arraycopy(data, 0, newData, 0, j);
            return newData;
        }

        @Override
        public String decode(byte... bytes) throws UTFDataFormatException {
            char[] chars = new char[bytes.length];
            int i = 0;
            int j = 0;
            while (j < bytes.length) {
                int x = bytes[j++] & 0xFF;
                if ((x & 0b10000000) == 0b00000000) {
                    if (x == 0b00000000) {
                        throw new UTFDataFormatException("invalid encoded code point: 0b00000000");
                    }

                    // Code points in the range '\u0001' to '\u007F' are represented by a single byte.
                    chars[i++] = (char) (x & 0b01111111);
                } else if ((x & 0b11100000) == 0b11000000) {
                    // The null code point ('\u0000') and code points in the range '\u0080' to '\u07FF' are represented by a pair of bytes x and y.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }

                    chars[i++] = (char) ((x & 0b00011111) << 6 | y & 0b00111111);
                } else if (x == 0b11101101) {
                    // Characters with code points above U+FFFF (so-called supplementary characters) are represented by separately encoding the two surrogate code units of their UTF-16 representation.
                    // Each of the surrogate code units is represented by three bytes.
                    // This means supplementary characters are represented by six bytes, x, y, z, a, b, and c.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11110000) != 0b10100000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 1010xxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }
                    int a = bytes[j++] & 0xFF;
                    if (a != 0b11101101) {
                        throw new UTFDataFormatException("invalid byte 4 format, expected 11101101 but got " + Integer.toBinaryString(a));
                    }
                    int b = bytes[j++] & 0xFF;
                    if ((b & 0b11110000) != 0b10110000) {
                        throw new UTFDataFormatException("invalid byte 5 format, expected 1011xxxx but got " + Integer.toBinaryString(b));
                    }
                    int c = bytes[j++] & 0xFF;
                    if ((c & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 6 format, expected 10xxxxxx but got " + Integer.toBinaryString(c));
                    }

                    // Decode and encode as surrogate pairs
                    chars[i++] = (char) (0b1101100000000000 | (y & 0b00001111) << 6 | z & 0b00111111);
                    chars[i++] = (char) (0b1101110000000000 | (b & 0b00001111) << 6 | c & 0b00111111);
                } else if ((x & 0b11110000) == 0b11100000) {
                    // Code points in the range '\u0800' to '\uFFFF' are represented by 3 bytes x, y, and z:
                    // NOTE: code points in the range '\uD800' to '\uDFFF' are reserved for the encoding of surrogate pairs.
                    int y = bytes[j++] & 0xFF;
                    if ((y & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 2 format, expected 10xxxxxx but got " + Integer.toBinaryString(y));
                    }
                    int z = bytes[j++] & 0xFF;
                    if ((z & 0b11000000) != 0b10000000) {
                        throw new UTFDataFormatException("invalid byte 3 format, expected 10xxxxxx but got " + Integer.toBinaryString(z));
                    }

                    chars[i++] = (char) ((x & 0b00001111) << 12 | (y & 0b00111111) << 6 | z & 0b00111111);
                } else {
                    throw new UTFDataFormatException("invalid byte 1 format, expected 0xxxxxxx, 110xxxxx, 1110xxxx or 11101101 but got " + Integer.toBinaryString(x));
                }
            }

            return new String(chars, 0, i);
        }
    };

    /**
     * Encodes a string.
     *
     * @param string the string to encode
     * @return the encoded bytes
     */
    public abstract byte[] encode(String string) throws IOException;

    /**
     * Decodes a string.
     *
     * @param bytes the encoded bytes
     * @return the decoded string
     */
    public abstract String decode(byte... bytes) throws IOException;
}
