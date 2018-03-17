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

import org.jdom2.Attribute;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

final class AttributeNodeImpl implements AttributeNode {
  private final Attribute attribute;

  AttributeNodeImpl(final Attribute attribute) {
    this.attribute = attribute;
  }

  @Override
  public Attribute attribute() {
    return this.attribute;
  }

  @Override
  public String name() {
    return this.attribute.getName();
  }

  @Override
  public String value() {
    return this.attribute.getValue();
  }

  @Override
  public String normalizedValue() {
    return this.value();
  }

  @Override
  public Optional<Node> attribute(final String name) {
    return Optional.empty();
  }

  @Override
  public Stream<Node> attributes() {
    return Stream.empty();
  }

  @Override
  public Stream<Node> attributes(final Collection<String> names) {
    return Stream.empty();
  }

  @Override
  public Stream<Node> elements() {
    return Stream.empty();
  }

  @Override
  public Stream<Node> elements(final Collection<String> names) {
    return Stream.empty();
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) {
      return true;
    }
    if(other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final AttributeNodeImpl that = (AttributeNodeImpl) other;
    return Objects.equals(this.attribute, that.attribute);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.attribute);
  }
}
