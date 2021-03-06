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

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.kyori.mu.Maybe;
import net.kyori.mu.function.ThrowingSupplier;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Parses a {@link Node} into {@code T}.
 *
 * @param <T> the parsed type
 */
public interface Parser<T> extends Function<Node, T> {
  /**
   * @deprecated only exists to implement {@link Function}
   */
  @Deprecated
  @Override
  default @NonNull T apply(final @NonNull Node node) {
    return this.parse(node);
  }

  /**
   * Parses a {@link Node} into {@code T}.
   *
   * @param node the node
   * @return the parsed value
   */
  default @NonNull Maybe<T> parse(final @NonNull Maybe<Node> node) {
    return node.map(this::parse);
  }

  /**
   * Parses a {@link Node} into {@code T}.
   *
   * @param node the node
   * @return the parsed value
   */
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  default @NonNull Optional<T> parse(final @NonNull Optional<Node> node) {
    return node.map(this::parse);
  }

  /**
   * Parses a stream of {@link Node} into a stream of {@code T}.
   *
   * @param stream the node stream
   * @return the parsed value
   */
  default @NonNull Stream<T> parse(final @NonNull Stream<Node> stream) {
    return stream.map(this::parse);
  }

  /**
   * Parses a {@link Node} into {@code T}.
   *
   * @param node the node
   * @return the parsed value
   */
  default @NonNull T parse(final @NonNull Node node) {
    return ThrowingSupplier.get(() -> this.throwingParse(node));
  }

  /**
   * Parses a {@link Node} into {@code T}.
   *
   * @param node the node
   * @return the parsed value
   * @throws XMLException if an exception occurred while parsing
   */
  @NonNull T throwingParse(final @NonNull Node node) throws XMLException;
}
