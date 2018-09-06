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

import net.kyori.xml.element.Elements;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public interface ElementNode extends Node {
  /**
   * Creates a node from an element.
   *
   * @param element the element
   * @return a node
   */
  static @NonNull Node of(final org.jdom2.@NonNull Element element) {
    return new JDOMElementNode(element);
  }

  /**
   * Creates a copy of this node with parent attributes inherited.
   *
   * @return an inherited copy
   */
  @NonNull ElementNode inherited();
}

/* package */ abstract class AbstractElementNode<E> implements ElementNode {
  /* package */ final E element;

  /* package */ AbstractElementNode(final @NonNull E element) {
    this.element = element;
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if(this == other) return true;
    if(other == null || this.getClass() != other.getClass()) return false;
    final AbstractElementNode that = (AbstractElementNode) other;
    return Objects.equals(this.element, that.element);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.element);
  }

  @Override
  public @NonNull String toString() {
    return "ElementNode{" + this.name() + '}';
  }
}

/* package */ final class JDOMElementNode extends AbstractElementNode<org.jdom2.Element> implements ElementNode {
  /* package */ JDOMElementNode(final org.jdom2.@NonNull Element element) {
    super(element);
  }

  @Override
  public @NonNull String name() {
    return this.element.getName();
  }

  @Override
  public @NonNull String value() {
    return this.element.getValue();
  }

  @Override
  public @NonNull Stream<Node> elements() {
    return this.element.getChildren().stream().map(Node::of);
  }

  @Override
  public @NonNull Stream<Node> elements(final @NonNull Collection<String> names) {
    return this.element.getChildren().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of);
  }

  @Override
  public @NonNull Stream<Node> attributes() {
    return this.element.getAttributes().stream().map(Node::of);
  }

  @Override
  public @NonNull Stream<Node> attributes(final @NonNull Collection<String> names) {
    return this.element.getAttributes().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of);
  }

  @Override
  public @NonNull ElementNode inherited() {
    if(Elements.Inherited.is(this.element)) {
      return this;
    }
    return new JDOMElementNode(Elements.Inherited.of(this.element));
  }
}
