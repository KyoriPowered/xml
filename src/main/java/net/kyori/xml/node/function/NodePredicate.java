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
package net.kyori.xml.node.function;

import java.util.function.Predicate;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A predicate which accepts a node and depth.
 */
@FunctionalInterface
public interface NodePredicate extends Predicate<Node> {
  @Override
  default boolean test(final @NonNull Node node) {
    return this.test(node, 0);
  }

  /**
   * Tests.
   *
   * @param node the node
   * @param depth the depth
   * @return a stream of nodes
   */
  boolean test(final @NonNull Node node, final int depth);

  @Override
  default @NonNull NodePredicate and(final @NonNull Predicate<? super Node> other) {
    return (node, depth) -> this.test(node, depth) && test(other, node, depth);
  }

  @Override
  default @NonNull NodePredicate or(final @NonNull Predicate<? super Node> other) {
    return (node, depth) -> this.test(node, depth) || test(other, node, depth);
  }

  static boolean test(final @NonNull Predicate<? super Node> predicate, final @NonNull Node node, final int depth) {
    return predicate instanceof NodePredicate ? ((NodePredicate) predicate).test(node, depth) : predicate.test(node);
  }
}
