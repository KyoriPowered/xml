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

import net.kyori.lunar.EvenMoreObjects;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.stream.NodeStream;
import net.kyori.xml.node.stream.NodeStreamElement;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.Element;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

final class ElementNodeImpl implements ElementNode {
  private final @NonNull Element element;

  ElementNodeImpl(final @NonNull Element element) {
    this.element = element;
  }

  @Override
  public @NonNull Element element() {
    return this.element;
  }

  @Override
  public @NonNull NodeStreamElement<Node> attribute(final @NonNull String name) {
    final Optional<Node> node = Optional.ofNullable(this.element.getAttribute(name)).map(Node::of);
    return new NodeStreamElement<Node>() {
      @Override
      public @NonNull Optional<Node> optional() {
        return node;
      }

      @Override
      public @NonNull Node required() {
        final XMLException exception = new XMLException(ElementNodeImpl.this, "missing required attribute '" + name + '\'');
        return this.optional().orElseThrow(() -> EvenMoreObjects.make(new NoSuchElementException(exception.getMessage()), e -> e.initCause(exception)));
      }
    };
  }

  @Override
  public @NonNull NodeStream attributes() {
    return NodeStream.of(this.element.getAttributes().stream().map(Node::of));
  }

  @Override
  public @NonNull NodeStream attributes(final @NonNull Collection<String> names) {
    return NodeStream.of(this.element.getAttributes().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of));
  }

  @Override
  public @NonNull NodeStream elements() {
    return NodeStream.of(this.element.getChildren().stream().map(Node::of));
  }

  @Override
  public @NonNull NodeStream elements(final @NonNull Collection<String> names) {
    return NodeStream.of(this.element.getChildren().stream()
      .filter(attribute -> names.contains(attribute.getName()))
      .map(Node::of));
  }

  @Override
  public boolean equals(final @Nullable Object other) {
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
