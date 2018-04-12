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
package net.kyori.xml.flattener;

import net.kyori.xml.element.Elements;
import net.kyori.xml.node.Node;

import java.util.stream.Stream;

/*
 * If the word "flattener" is in the scrabble dictionary then it is a word, IntelliJ.
 * https://www.wordgamedictionary.com/dictionary/word/flattener/
 */

/**
 * Takes a node and flattens it into a stream of nodes, similar to
 * a {@link Stream#flatMap flatMap} operation on a {@link Stream}.
 */
@FunctionalInterface
public interface NodeFlattener {
  /**
   * Flattens a node.
   *
   * @param node the node
   * @return the flattened nodes
   */
  default Stream<Node> flatten(final Node node) {
    return this.flatten(node, 0);
  }

  /**
   * Flattens a node.
   *
   * @param node the node
   * @param depth the depth
   * @return the flattened nodes
   */
  Stream<Node> flatten(final Node node, final int depth);

  abstract class Impl implements NodeFlattener {
    protected Node node(final Node node, final int depth) {
      if(depth < 1) {
        return node;
      }
      return Elements.inherited(node);
    }
  }
}
