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

import javax.inject.Singleton;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import net.kyori.xml.node.parser.ParseException;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Parses a {@link Node} into a {@link Byte byte}.
 */
@Singleton
public class ByteParser implements NumberParser<Byte> {
  private static final ByteParser INSTANCE = new ByteParser();

  /**
   * Gets the parser.
   *
   * @return the parser
   */
  public static @NonNull ByteParser get() {
    return INSTANCE;
  }

  @Override
  public @NonNull Byte negativeInfinity(final @NonNull Node node, final @NonNull String string) {
    return Byte.MIN_VALUE;
  }

  @Override
  public @NonNull Byte finite(final @NonNull Node node, final @NonNull String string) throws XMLException {
    try {
      return Byte.parseByte(string);
    } catch(final NumberFormatException e) {
      throw new ParseException(node, "Could not parse '" + string + "' as a byte", e);
    }
  }

  @Override
  public @NonNull Byte positiveInfinity(final @NonNull Node node, final @NonNull String string) {
    return Byte.MAX_VALUE;
  }
}
