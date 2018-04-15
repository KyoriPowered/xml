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

import com.google.common.collect.ImmutableSet;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.stream.NodeStream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Node {
  /**
   * Creates a node from an attribute.
   *
   * @param attribute the attribute
   * @return the node
   */
  static AttributeNode of(final @NonNull Attribute attribute) {
    return new AttributeNodeImpl(attribute);
  }

  /**
   * Creates a node from an attribute.
   *
   * @param attribute the attribute
   * @return the node
   */
  static Optional<AttributeNode> maybeOf(final @Nullable Attribute attribute) {
    return attribute != null ? Optional.of(new AttributeNodeImpl(attribute)) : Optional.empty();
  }

  /**
   * Creates a node from an element.
   *
   * @param element the element
   * @return the node
   */
  static ElementNode of(final @NonNull Element element) {
    return new ElementNodeImpl(element);
  }

  /**
   * Creates a node from an element.
   *
   * @param element the element
   * @return the node
   */
  static Optional<ElementNode> maybeOf(final @Nullable Element element) {
    return element != null ? Optional.of(new ElementNodeImpl(element)) : Optional.empty();
  }

  /**
   * Gets the name of this node.
   *
   * @return the name
   */
  String name();

  /**
   * Gets the value of this node.
   *
   * @return the value
   */
  String value();

  /**
   * Gets the normalized value of this node.
   *
   * @return the normalized value
   */
  String normalizedValue();

  /**
   * Creates a stream of nodes from the attributes and children elements of this node.
   *
   * @return the node stream
   */
  default NodeStream nodes() {
    return NodeStream.concat(this.attributes(), this.elements());
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a name.
   *
   * @param name the name
   * @return the node stream
   */
  default NodeStream nodes(final String name) {
    return this.nodes(Collections.singleton(name));
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a set of names.
   *
   * @param names the names
   * @return the node stream
   */
  default NodeStream nodes(final String... names) {
    return this.nodes(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a set of names.
   *
   * @param names the names
   * @return the node stream
   */
  default NodeStream nodes(final Collection<String> names) {
    return NodeStream.concat(this.attributes(names), this.elements(names));
  }

  /**
   * Gets an attribute by its name.
   *
   * @param name the name
   * @return the attribute
   */
  Optional<Node> attribute(final String name);

  /**
   * Gets a required attribute by its name.
   *
   * @param name the name
   * @return the attribute
   * @throws XMLException if the attribute is not available
   */
  default Node requireAttribute(final String name) throws XMLException {
    return this.attribute(name).orElseThrow(() -> new XMLException(this, "missing required attribute '" + name + '\''));
  }

  /**
   * Creates a stream of nodes from the attributes of this node.
   *
   * @return the attribute node stream
   */
  NodeStream attributes();

  /**
   * Creates a stream of nodes from the attributes of this node matching a set of names.
   *
   * @param names the names
   * @return the attribute node stream
   */
  default NodeStream attributes(final String... names) {
    return this.attributes(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the attributes of this node matching a set of names.
   *
   * @param names the names
   * @return the attribute node stream
   */
  NodeStream attributes(final Collection<String> names);

  /**
   * Creates a stream of nodes from the children elements of this node.
   *
   * @return the element node stream
   */
  NodeStream elements();

  /**
   * Creates a stream of nodes from the children elements of this node matching a name.
   *
   * @param name the name
   * @return the element node stream
   */
  default NodeStream elements(final String name) {
    return this.elements(Collections.singleton(name));
  }

  /**
   * Creates a stream of nodes from the children elements of this node matching a set of names.
   *
   * @param names the names
   * @return the element node stream
   */
  default NodeStream elements(final String... names) {
    return this.elements(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the children elements of this node matching a set of names.
   *
   * @param names the names
   * @return the element node stream
   */
  NodeStream elements(final Collection<String> names);
}
