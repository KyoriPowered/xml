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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public interface AttributeNode extends Node {
  /**
   * Creates a node from an attribute.
   *
   * @param attribute the attribute
   * @return a node
   */
  static @NonNull Node of(final org.jdom2.@NonNull Attribute attribute) {
    return new JDOMAttributeNode(attribute);
  }

  /*
   * Attributes don't have children.
   */

  @Override
  default @NonNull Stream<Node> elements() {
    return Stream.empty();
  }

  @Override
  default @NonNull Stream<Node> elements(final @NonNull Collection<String> names) {
    return Stream.empty();
  }

  @Override
  default @NonNull Stream<Node> attributes() {
    return Stream.empty();
  }

  @Override
  default @NonNull Stream<Node> attributes(final @NonNull Collection<String> names) {
    return Stream.empty();
  }
}

/* package */ abstract class AbstractAttributeNode<A> implements AttributeNode {
  /* package */ final A attribute;

  /* package */ AbstractAttributeNode(final @NonNull A attribute) {
    this.attribute = attribute;
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if(this == other) return true;
    if(other == null || this.getClass() != other.getClass()) return false;
    final AbstractAttributeNode that = (AbstractAttributeNode) other;
    return Objects.equals(this.attribute, that.attribute);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.attribute);
  }

  @Override
  public @NonNull String toString() {
    return "AttributeNode{" + this.name() + '}';
  }
}

/* package */ final class JDOMAttributeNode extends AbstractAttributeNode<org.jdom2.Attribute> implements AttributeNode {
  /* package */ JDOMAttributeNode(final org.jdom2.@NonNull Attribute attribute) {
    super(attribute);
  }

  @Override
  public @NonNull String name() {
    return this.attribute.getName();
  }

  @Override
  public @NonNull String value() {
    return this.attribute.getValue();
  }
}
