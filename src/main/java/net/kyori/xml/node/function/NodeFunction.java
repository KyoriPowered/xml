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

import java.util.function.UnaryOperator;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A function that accepts a node and depth.
 */
@FunctionalInterface
public interface NodeFunction extends UnaryOperator<Node> {
  static @NonNull NodeFunction identity() {
    return (node, depth) -> node;
  }

  @Override
  default @NonNull Node apply(final @NonNull Node node) {
    return this.apply(node, 0);
  }

  /**
   * Transforms {@code node}, with depth.
   *
   * @param node the node
   * @param depth the depth
   * @return the transformed node
   */
  @NonNull Node apply(final @NonNull Node node, final int depth);
}
