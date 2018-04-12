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
package net.kyori.xml.filter;

import net.kyori.xml.node.Node;

/**
 * A filter which accepts a node and depth.
 */
@FunctionalInterface
public interface NodeFilter {
  /**
   * Tests if this filter allows {@code node} at a depth of {@code depth}.
   *
   * @param node the node
   * @param depth the depth
   * @return {@code true} if the filter allows the node
   */
  boolean test(final Node node, final int depth);

  /**
   * Logical and with {@code this} with {@code that}.
   *
   * @param that the other node depth filter
   * @return a node filter
   */
  default NodeFilter and(final NodeFilter that) {
    return (node, depth) -> this.test(node, depth) && that.test(node, depth);
  }

  /**
   * Logical and with {@code this} with {@code that}.
   *
   * @param that the other node depth filter
   * @return a node filter
   */
  default NodeFilter or(final NodeFilter that) {
    return (node, depth) -> this.test(node, depth) || that.test(node, depth);
  }
}
