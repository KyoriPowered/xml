/*
 * This file is part of xml, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.xml.node.parser.number;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LongParserTest extends AbstractNumberParserTest<Long> {
  LongParserTest() {
    super(LongParser.get());
  }

  @Test
  void testNegativeInfinityParse() {
    this.assertNegativeInfinityParse(Long.MIN_VALUE);
  }

  @Test
  void testNegativeParse() {
    this.assertParse(-6280110368799927205L, "-6280110368799927205");
    this.assertParse(-3140055184399963602L, "-3140055184399963602");
  }

  @Test
  void testPositiveParse() {
    this.assertParse(3140055184399963602L, "3140055184399963602");
    this.assertParse(8568549957979054705L, "8568549957979054705");
  }

  @Test
  void testPositiveInfinityParse() {
    this.assertPositiveInfinityParse(Long.MAX_VALUE);
  }
}
