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
package net.kyori.xml.node;

import net.kyori.xml.node.stream.NodeStream;
import net.kyori.xml.node.stream.NodeStreamElement;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jdom2.Attribute;

import java.util.Collection;

/**
 * A node around an {@link Attribute attribute}.
 */
public interface AttributeNode extends Node {
  /**
   * Gets the attribute.
   *
   * @return the attribute
   */
  @NonNull Attribute attribute();

  @Override
  default @NonNull String name() {
    return this.attribute().getName();
  }

  @Override
  default @NonNull String value() {
    return this.attribute().getValue();
  }

  // Attribute values don't need to be normalised.
  @Override
  default @NonNull String normalizedValue() {
    return this.value();
  }

  /*
   * Attributes don't have children.
   */

  @Override
  default @NonNull NodeStreamElement<Node> attribute(final @NonNull String name) {
    return NodeStreamElement.empty();
  }

  @Override
  default @NonNull NodeStream attributes() {
    return NodeStream.empty();
  }

  @Override
  default @NonNull NodeStream attributes(final @NonNull Collection<String> names) {
    return NodeStream.empty();
  }

  @Override
  default @NonNull NodeStream elements() {
    return NodeStream.empty();
  }

  @Override
  default @NonNull NodeStream elements(final @NonNull Collection<String> names) {
    return NodeStream.empty();
  }
}
