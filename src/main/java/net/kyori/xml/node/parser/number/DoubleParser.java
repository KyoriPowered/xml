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

import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Singleton;

/**
 * Parses a {@link Node} into a {@link Double double}.
 */
@Singleton
public class DoubleParser implements NumberParser<Double> {
  private static final DoubleParser INSTANCE = new DoubleParser();

  /**
   * Gets the parser.
   *
   * @return the parser
   * @deprecated prefer injection
   */
  @Deprecated
  public static @NonNull DoubleParser get() {
    return INSTANCE;
  }

  @Override
  public @NonNull Double negativeInfinity(final @NonNull Node node, final @NonNull String string) {
    return Double.NEGATIVE_INFINITY;
  }

  @Override
  public @NonNull Double finite(final @NonNull Node node, final @NonNull String string) throws XMLException {
    try {
      return Double.parseDouble(string);
    } catch(final NumberFormatException e) {
      throw new XMLException(node, "Could not parse '" + string + "' as a double", e);
    }
  }

  @Override
  public @NonNull Double positiveInfinity(final @NonNull Node node, final @NonNull String string) {
    return Double.POSITIVE_INFINITY;
  }
}
