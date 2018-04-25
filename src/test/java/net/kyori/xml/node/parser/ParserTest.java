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
package net.kyori.xml.node.parser;

import net.kyori.xml.Testing;
import net.kyori.xml.node.Node;
import net.kyori.xml.node.parser.number.ByteParser;
import net.kyori.xml.node.parser.number.DoubleParser;
import net.kyori.xml.node.parser.number.FloatParser;
import net.kyori.xml.node.parser.number.IntegerParser;
import net.kyori.xml.node.parser.number.LongParser;
import net.kyori.xml.node.parser.number.ShortParser;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {
  private Node root;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.root = Testing.read("/parser_test.xml");
  }

  @Test
  void testBoolean() {
    this.npTest("boolean", new BooleanParser(), value -> assertEquals(false, value), value -> assertEquals(true, value));
  }

  @Test
  void testByte() {
    this.npTest("byte", new ByteParser(), value -> assertEquals(-1, (byte) value), value -> assertEquals(2, (byte) value));
  }

  @Test
  void testDouble() {
    this.npTest("double", new DoubleParser(), value -> assertEquals(-64.024399791242338265d, (double) value), value -> assertEquals(64.28541278239204515d, (double) value));
  }

  @Test
  void testFloat() {
    this.npTest("float", new FloatParser(), value -> assertEquals(-64.5677465f, (float) value), value -> assertEquals(64.21948814f, (float) value));
  }

  @Test
  void testInt() {
    this.npTest("int", new IntegerParser(), value -> assertEquals(-1644266465, (float) value), value -> assertEquals(533695713, (float) value));
  }

  @Test
  void testLong() {
    this.npTest("long", new LongParser(), value -> assertEquals(-2122657314852929763L, (long) value), value -> assertEquals(4370982310787917920L, (long) value));
  }

  @Test
  void testShort() {
    this.npTest("short", new ShortParser(), value -> assertEquals(-21738, (short) value), value -> assertEquals(17212, (short) value));
  }

  private <T> void npTest(final String type, final Parser<T> parser, final Consumer<T> negative, final Consumer<T> positive) {
    negative.accept(
      this.root.elements("np")
        .flatMap(Node::elements)
        .named(type)
        .flatMap(Node::elements)
        .named("n")
        .one()
        .map(parser)
        .need()
    );
    positive.accept(
      this.root.elements("np")
        .flatMap(Node::elements)
        .named(type)
        .flatMap(Node::elements)
        .named("p")
        .one()
        .map(parser)
        .need()
    );
  }
}
