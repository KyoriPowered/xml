/*
 * This file is part of xml, licensed under the MIT License.
 *
 * Copyright (c) 2018-2020 KyoriPowered
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
class FloatParserTest extends NumberParserTest<Float> {
  FloatParserTest() {
    super(FloatParser.get());
  }

  @Test
  void testNegativeInfinityParse() {
    this.assertNegativeInfinityParse(Float.NEGATIVE_INFINITY);
  }

  @Test
  void testNegativeParse() {
    this.assertParse(-0.6846304f, "-0.6846304");
    this.assertParse(-0.3423152f, "-0.3423152");
  }

  @Test
  void testPositiveParse() {
    this.assertParse(0.3423152f, "0.3423152");
    this.assertParse(0.9952266f, "0.9952266");
  }

  @Test
  void testPositiveInfinityParse() {
    this.assertPositiveInfinityParse(Float.POSITIVE_INFINITY);
  }
}
