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
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Parses a {@link Node} into a {@link Boolean boolean}.
 */
public class BooleanParser implements PrimitiveParser<Boolean> {
  private static final BooleanParser INSTANCE = new BooleanParser();

  /**
   * Gets the parser.
   *
   * @return the parser
   */
  public static @NonNull BooleanParser get() {
    return INSTANCE;
  }

  @Override
  public @NonNull Boolean throwingParse(final @NonNull Node node, final @NonNull String string) throws XMLException {
    if(this.isTrue(string)) {
      return true;
    } else if(this.isFalse(string)) {
      return false;
    }
    throw new ParseException(node, "Could not parse '" + string + "' as a boolean");
  }

  // override to allow more true-like choices
  protected boolean isTrue(final @NonNull String string) {
    return string.equals("true");
  }

  // override to allow more false-like choices
  protected boolean isFalse(final @NonNull String string) {
    return string.equals("false");
  }
}
