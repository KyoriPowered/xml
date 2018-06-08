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
import net.kyori.xml.node.parser.PrimitiveParser;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Parses a {@link Node} into a number of type {@code T}.
 *
 * @param <T> the parsed number type
 */
public interface NumberParser<T extends Number> extends PrimitiveParser<T> {
  String INFINITY_SYMBOL_A = "∞";
  String INFINITY_SYMBOL_B = "oo";
  // Negative
  String NEGATIVE_INFINITY_SYMBOL_A = '-' + INFINITY_SYMBOL_A;
  String NEGATIVE_INFINITY_SYMBOL_B = '-' + INFINITY_SYMBOL_B;
  // Positive
  String POSITIVE_INFINITY_SYMBOL_A = '+' + INFINITY_SYMBOL_A;
  String POSITIVE_INFINITY_SYMBOL_B = '+' + INFINITY_SYMBOL_B;

  @Override
  default @NonNull T throwingParse(final @NonNull Node node, final @NonNull String string) throws XMLException {
    switch(string) {
      case NEGATIVE_INFINITY_SYMBOL_A:
      case NEGATIVE_INFINITY_SYMBOL_B:
        return this.negativeInfinity(node, string);
      default:
        return this.finite(node, string);
      case POSITIVE_INFINITY_SYMBOL_A:
      case POSITIVE_INFINITY_SYMBOL_B:
        return this.positiveInfinity(node, string);
    }
  }

  /**
   * Parses a {@link Node}'s {@link Node#value() value} into {@code T}'s negative infinity.
   *
   * @param node the node
   * @param string the node value
   * @return the parsed value
   * @throws XMLException if an exception occurred while parsing
   */
  @NonNull T negativeInfinity(final @NonNull Node node, final @NonNull String string) throws XMLException;

  /**
   * Parses a {@link Node}'s {@link Node#value() value} into a finite {@code T}.
   *
   * @param node the node
   * @param string the node value
   * @return the parsed value
   * @throws XMLException if an exception occurred while parsing
   */
  @NonNull T finite(final @NonNull Node node, final @NonNull String string) throws XMLException;

  /**
   * Parses a {@link Node}'s {@link Node#value() value} into {@code T}'s positive infinity.
   *
   * @param node the node
   * @param string the node value
   * @return the parsed value
   * @throws XMLException if an exception occurred while parsing
   */
  @NonNull T positiveInfinity(final @NonNull Node node, final @NonNull String string) throws XMLException;
}
