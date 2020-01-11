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
package net.kyori.xml.node.parser;

import net.kyori.xml.XMLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnumParserTest {
  private static final ParserTest<Thing> THING = new ParserTest<>(new EnumParser<>(Thing.class));
  private static final ParserTest<Another> ANOTHER = new ParserTest<>(new EnumParser<>(Another.class));

  @Test
  void testParse() {
    THING.assertParse(Thing.FOO_BAR, "FOO_BAR");
    THING.assertParse(Thing.FOO_BAR, "FOO BAR");
    THING.assertParse(Thing.FOO_BAR, "foo bar");
    THING.assertParse(Thing.FOO_BAR, "foo_bar");

    assertThrows(XMLException.class, () -> ANOTHER.assertParse(Another.FOO_BAR, "FOO_BAR"));
    assertThrows(XMLException.class, () -> ANOTHER.assertParse(Another.FOO_BAR, "FOO BAR"));
    assertThrows(XMLException.class, () -> ANOTHER.assertParse(Another.FOO_BAR, "foo bar"));
    ANOTHER.assertParse(Another.FOO_BAR, "foo_bar");
  }

  public enum Thing {
    FOO_BAR;
  }

  public enum Another {
    @EnumParser.Names("foo_bar")
    FOO_BAR;
  }
}
