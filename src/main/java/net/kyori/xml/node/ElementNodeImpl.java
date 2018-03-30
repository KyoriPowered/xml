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

import org.jdom2.Element;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

final class ElementNodeImpl implements ElementNode {
  private final Element element;

  ElementNodeImpl(final Element element) {
    this.element = element;
  }

  @Override
  public Element element() {
    return this.element;
  }

  @Override
  public String name() {
    return this.element.getName();
  }

  @Override
  public String value() {
    return this.element.getText();
  }

  @Override
  public String normalizedValue() {
    return this.element.getTextNormalize();
  }

  @Override
  public Optional<Node> attribute(final String name) {
    return Optional.ofNullable(this.element.getAttribute(name)).map(Node::of);
  }

  @Override
  public Stream<Node> attributes() {
    return this.element.getAttributes().stream().map(Node::of);
  }

  @Override
  public Stream<Node> attributes(final Collection<String> names) {
    return this.element.getAttributes().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of);
  }

  @Override
  public Stream<Node> elements() {
    return this.element.getChildren().stream().map(Node::of);
  }

  @Override
  public Stream<Node> elements(final Collection<String> names) {
    return this.element.getChildren().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of);
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) {
      return true;
    }
    if(other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final ElementNodeImpl that = (ElementNodeImpl) other;
    return Objects.equals(this.element, that.element);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.element);
  }

  @Override
  public String toString() {
    return "ElementNode{" + this.element.getName() + '}';
  }
}
