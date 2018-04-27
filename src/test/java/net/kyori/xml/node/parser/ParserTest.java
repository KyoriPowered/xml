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

import com.google.inject.Guice;
import com.google.inject.TypeLiteral;
import net.kyori.violet.AbstractModule;
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
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Inject;

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
  void testInjectedBoolean() {
    this.npTest("boolean", this.parsers().booleanParser, value -> assertEquals(false, value), value -> assertEquals(true, value));
  }

  @Test
  void testByte() {
    this.npTest("byte", new ByteParser(), value -> assertEquals(-1, (byte) value), value -> assertEquals(2, (byte) value));
  }

  @Test
  void testInjectedByte() {
    this.npTest("byte", this.parsers().byteParser, value -> assertEquals(-1, (byte) value), value -> assertEquals(2, (byte) value));
  }

  @Test
  void testDouble() {
    this.npTest("double", new DoubleParser(), value -> assertEquals(-64.024399791242338265d, (double) value), value -> assertEquals(64.28541278239204515d, (double) value));
  }

  @Test
  void testInjectedDouble() {
    this.npTest("double", this.parsers().doubleParser, value -> assertEquals(-64.024399791242338265d, (double) value), value -> assertEquals(64.28541278239204515d, (double) value));
  }

  @Test
  void testEnum() {
    this.testEnum(new EnumParser<>(Thing.class));
    this.testEnum(new EnumParser<>(TypeLiteral.get(Thing.class)));
  }

  @Test
  void testInjectedEnum() {
    this.testEnum(this.parsers().thingParser);
  }

  private void testEnum(final Parser<Thing> parser) {
    final List<Thing> things = this.root.elements("enum")
      .flatMap(Node::elements)
      .named("enum")
      .map(parser)
      .collect(Collectors.toList());
    assertEquals(4, things.size());
    assertEquals(Thing.A, things.get(0));
    assertEquals(Thing.FOO_BAR, things.get(1));
    assertEquals(Thing.FOO_BAR, things.get(2));
    assertEquals(Thing.FOO_BAR, things.get(3));
  }

  @Test
  void testFloat() {
    this.npTest("float", new FloatParser(), value -> assertEquals(-64.5677465f, (float) value), value -> assertEquals(64.21948814f, (float) value));
  }

  @Test
  void testInjectedFloat() {
    this.npTest("float", this.parsers().floatParser, value -> assertEquals(-64.5677465f, (float) value), value -> assertEquals(64.21948814f, (float) value));
  }

  @Test
  void testInt() {
    this.npTest("int", new IntegerParser(), value -> assertEquals(-1644266465, (float) value), value -> assertEquals(533695713, (float) value));
  }

  @Test
  void testInjectedInt() {
    this.npTest("int", this.parsers().integerParser, value -> assertEquals(-1644266465, (float) value), value -> assertEquals(533695713, (float) value));
  }

  @Test
  void testLong() {
    this.npTest("long", new LongParser(), value -> assertEquals(-2122657314852929763L, (long) value), value -> assertEquals(4370982310787917920L, (long) value));
  }

  @Test
  void testInjectedLong() {
    this.npTest("long", this.parsers().longParser, value -> assertEquals(-2122657314852929763L, (long) value), value -> assertEquals(4370982310787917920L, (long) value));
  }

  @Test
  void testShort() {
    this.npTest("short", new ShortParser(), value -> assertEquals(-21738, (short) value), value -> assertEquals(17212, (short) value));
  }

  @Test
  void testInjectedShort() {
    this.npTest("short", this.parsers().shortParser, value -> assertEquals(-21738, (short) value), value -> assertEquals(17212, (short) value));
  }

  @Test
  void testString() {
    assertEquals("foo bar watermelon", this.root.elements("string").one().map(new StringParser()).need());
  }

  @Test
  void testInjectedString() {
    assertEquals("foo bar watermelon", this.root.elements("string").one().map(this.parsers().stringParser).need());
  }

  private Parsers parsers() {
    return Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        this.install(new ParserModule());

        final ParserBinder parsers = new ParserBinder(this.binder());
        parsers.bindParser(Thing.class).to(new TypeLiteral<EnumParser<Thing>>() {});
      }
    }).getInstance(Parsers.class);
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

  private enum Thing {
    A,
    FOO_BAR;
  }

  static class Parsers {
    @Inject Parser<Boolean> booleanParser;
    @Inject Parser<Byte> byteParser;
    @Inject Parser<Double> doubleParser;
    @Inject Parser<Float> floatParser;
    @Inject Parser<Integer> integerParser;
    @Inject Parser<Long> longParser;
    @Inject Parser<Short> shortParser;
    @Inject Parser<String> stringParser;
    @Inject Parser<Thing> thingParser;
  }
}
