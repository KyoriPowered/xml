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

import com.google.common.collect.ImmutableSet;
import net.kyori.xml.node.AttributeNode;
import net.kyori.xml.node.ElementNode;
import net.kyori.xml.node.Node;

import java.util.Collections;
import java.util.Set;

public final class NodeFilters {
  private static final NodeFilter ALWAYS_FALSE = (node, depth) -> false;
  private static final NodeFilter ALWAYS_TRUE = (node, depth) -> true;
  private static final NodeFilter ONLY_ATTRIBUTES = (node, depth) -> node instanceof AttributeNode;
  private static final NodeFilter ONLY_ELEMENTS = (node, depth) -> node instanceof ElementNode;

  private NodeFilters() {
  }

  /**
   * Gets a node filter that returns a constant value of {@code false}.
   *
   * @return a node filter
   */
  public static NodeFilter alwaysFalse() {
    return ALWAYS_FALSE;
  }

  /**
   * Gets a node filter that returns a constant value of {@code true}.
   *
   * @return a node filter
   */
  public static NodeFilter alwaysTrue() {
    return ALWAYS_TRUE;
  }

  /**
   * Gets a node filter that returns {@code true} when the node is a {@link AttributeNode}.
   *
   * @return a node filter
   */
  public static NodeFilter onlyAttributes() {
    return ONLY_ATTRIBUTES;
  }

  /**
   * Gets a node filter that returns {@code true} when the node is a {@link ElementNode}.
   *
   * @return a node filter
   */
  public static NodeFilter onlyElements() {
    return ONLY_ELEMENTS;
  }

  /**
   * Gets a node filter that returns {@code true} when the depth is at least {@code minimumDepth}.
   *
   * @return a node filter
   */
  public static NodeFilter minimumDepth(final int minimumDepth) {
    return (node, depth) -> depth >= minimumDepth;
  }

  /**
   * Gets a node filter that returns {@code true} if {@code names} is equal to the {@link Node#name() name} of the node.
   *
   * @param name the name
   * @return a node filter
   */
  public static NodeFilter named(final String name) {
    return named(Collections.singleton(name));
  }

  /**
   * Gets a node filter that returns {@code true} if {@code names} contains the {@link Node#name() name} of the node.
   *
   * @param names the names
   * @return a node filter
   */
  public static NodeFilter named(final String... names) {
    return named(ImmutableSet.copyOf(names));
  }

  /**
   * Gets a node filter that returns {@code true} if {@code names} contains the {@link Node#name() name} of the node.
   *
   * @param names the names
   * @return a node filter
   */
  public static NodeFilter named(final Set<String> names) {
    return (node, depth) -> names.contains(node.name());
  }
}
