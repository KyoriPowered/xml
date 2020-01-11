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

import net.kyori.xml.node.ElementNode;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface NodeFunctions {
  /**
   * Inherit attributes if the node is an element.
   *
   * @return a unary operator
   * @see ElementNode#inherited()
   */
  static @NonNull NodeFunction inherit() {
    return inherit(0);
  }

  /**
   * Inherit attributes if the node is an element.
   *
   * @param minimumDepth the minimum depth to apply the function
   * @return a unary operator
   * @see ElementNode#inherited()
   */
  static @NonNull NodeFunction inherit(final int minimumDepth) {
    return (node, depth) -> {
      if(depth >= minimumDepth && node instanceof ElementNode) {
        return ((ElementNode) node).inherited();
      }
      return node;
    };
  }
}
