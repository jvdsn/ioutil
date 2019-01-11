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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UTFDataFormatException;

/**
 * @author Joachim Vandersmissen
 */
public class StringEncodingTest {
    @Test
    public void testUtf8Encode() throws UTFDataFormatException {
        Assertions.assertArrayEquals(new byte[]{0x00, 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xF0, (byte) 0x90, (byte) 0x8D, (byte) 0x88, (byte) 0xF0, (byte) 0x90, (byte) 0x90, (byte) 0x80}, StringEncoding.UTF_8.encode("\u0000\u0024\u00A2\u20AC\uD800\uDF48\uD801\uDC00"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.encode("\uD800\u0000"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.encode("\uDC00"));
    }
    @Test
    public void testUtf8Decode() throws UTFDataFormatException {
        Assertions.assertEquals("\u0000\u0024\u00A2\u20AC\uD800\uDF48\uD801\uDC00", StringEncoding.UTF_8.decode((byte) 0x00, (byte) 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xF0, (byte) 0x90, (byte) 0x8D, (byte) 0x88, (byte) 0xF0, (byte) 0x90, (byte) 0x90, (byte) 0x80));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xC0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xED, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xED, (byte) 0xB0, (byte) 0x80));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xF0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xF0, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xF0, (byte) 0x80, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.UTF_8.decode((byte) 0xF8));
    }

    @Test
    public void testCesu8Encode() throws UTFDataFormatException {
        Assertions.assertArrayEquals(new byte[]{0x00, 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xED, (byte) 0xA0, (byte) 0x81, (byte) 0xED, (byte) 0xB0, (byte) 0x80}, StringEncoding.CESU_8.encode("\u0000\u0024\u00A2\u20AC\uD801\uDC00"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.encode("\uD800\u0000"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.encode("\uDC00"));
    }

    @Test
    public void testCesu8Decode() throws UTFDataFormatException {
        Assertions.assertEquals("\u0000\u0024\u00A2\u20AC\uD801\uDC00", StringEncoding.CESU_8.decode((byte) 0x00, (byte) 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xED, (byte) 0xA0, (byte) 0x81, (byte) 0xED, (byte) 0xB0, (byte) 0x80));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xC0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xED, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0xED, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0xED, (byte) 0xB0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xE0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xE0, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.CESU_8.decode((byte) 0xF0));
    }

    @Test
    public void testMutf8Encode() throws UTFDataFormatException {
        Assertions.assertArrayEquals(new byte[]{(byte) 0xC0, (byte) 0x80, 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xED, (byte) 0xA0, (byte) 0x81, (byte) 0xED, (byte) 0xB0, (byte) 0x80}, StringEncoding.MUTF_8.encode("\u0000\u0024\u00A2\u20AC\uD801\uDC00"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.encode("\uD800\u0000"));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.encode("\uDC00"));
    }

    @Test
    public void testMutf8Decode() throws UTFDataFormatException {
        Assertions.assertEquals("\u0000\u0024\u00A2\u20AC\uD801\uDC00", StringEncoding.MUTF_8.decode((byte) 0xC0, (byte) 0x80, (byte) 0x24, (byte) 0xC2, (byte) 0xA2, (byte) 0xE2, (byte) 0x82, (byte) 0xAC, (byte) 0xED, (byte) 0xA0, (byte) 0x81, (byte) 0xED, (byte) 0xB0, (byte) 0x80));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xC0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xED, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0xED, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xED, (byte) 0xA0, (byte) 0x80, (byte) 0xED, (byte) 0xB0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xE0, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xE0, (byte) 0x80, (byte) 0x00));
        Assertions.assertThrows(UTFDataFormatException.class, () -> StringEncoding.MUTF_8.decode((byte) 0xF0));
    }
}
