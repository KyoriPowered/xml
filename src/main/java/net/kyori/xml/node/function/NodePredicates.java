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
package net.kyori.xml.node.function;

import net.kyori.lambda.collection.MoreSets;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Collections;

public final class NodePredicates {
  private static final NodePredicate ALWAYS_FALSE = (node, depth) -> false;
  private static final NodePredicate ALWAYS_TRUE = (node, depth) -> true;

  private NodePredicates() {
  }

  /**
   * Gets a node filter that returns a constant value of {@code false}.
   *
   * @return a node filter
   */
  public static @NonNull NodePredicate alwaysFalse() {
    return ALWAYS_FALSE;
  }

  /**
   * Gets a node filter that returns a constant value of {@code true}.
   *
   * @return a node filter
   */
  public static @NonNull NodePredicate alwaysTrue() {
    return ALWAYS_TRUE;
  }

  /**
   * Gets a node predicate that returns {@code true} if {@code names} is equal to the {@link Node#name() name} of the node.
   *
   * @param name the name
   * @return a node predicate
   */
  public static @NonNull NodePredicate named(final @NonNull String name) {
    return named(Collections.singleton(name));
  }

  /**
   * Gets a node predicate that returns {@code true} if {@code names} contains the {@link Node#name() name} of the node.
   *
   * @param names the names
   * @return a node predicate
   */
  public static @NonNull NodePredicate named(final @NonNull String... names) {
    return named(MoreSets.mutable(names));
  }

  /**
   * Gets a node predicate that returns {@code true} if {@code names} contains the {@link Node#name() name} of the node.
   *
   * @param names the names
   * @return a node predicate
   */
  public static @NonNull NodePredicate named(final @NonNull Collection<String> names) {
    return (node, depth) -> names.contains(node.name());
  }

  /**
   * Gets a node predicate that returns {@code true} when the depth is at least {@code minimumDepth}.
   *
   * @param minimumDepth the minimum depth
   * @return a node predicate
   */
  public static @NonNull NodePredicate minimumDepth(final int minimumDepth) {
    return (node, depth) -> depth >= minimumDepth;
  }
}
