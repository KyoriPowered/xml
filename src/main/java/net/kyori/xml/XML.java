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
package net.kyori.xml;

import net.kyori.xml.node.Node;
import net.kyori.xml.node.parser.BooleanParser;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * XML utilities.
 */
public interface XML {
  /**
   * Parses an attribute named {@code name} in {@code node}, or returns {@code defaultValue}.
   *
   * @param node the node
   * @param name the attribute name
   * @param defaultValue the default value
   * @return a boolean
   */
  static boolean attrBoolean(final @NonNull Node node, final @NonNull String name, final boolean defaultValue) {
    return node.attribute(name)
      .map(BooleanParser.get())
      .orDefault(defaultValue);
  }
}
