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
import org.jdom2.Attribute;
import org.jdom2.Element;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface Node {
  /**
   * Creates a node from an attribute.
   *
   * @param attribute the attribute
   * @return the node
   */
  static AttributeNode of(final Attribute attribute) {
    return new AttributeNodeImpl(attribute);
  }

  /**
   * Creates a node from an element.
   *
   * @param element the element
   * @return the node
   */
  static ElementNode of(final Element element) {
    return new ElementNodeImpl(element);
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
  default Stream<Node> nodes() {
    return Stream.concat(this.attributes(), this.elements());
  }

  /**
   * Creates a stream of nodes from the attributes and children elements matching a set of names of this node.
   *
   * @param names the names
   * @return the node stream
   */
  default Stream<Node> nodes(final String... names) {
    return this.nodes(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the attributes and children elements matching a set of names of this node.
   *
   * @param names the names
   * @return the node stream
   */
  default Stream<Node> nodes(final Collection<String> names) {
    return Stream.concat(this.attributes(names), this.elements(names));
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
  Stream<Node> attributes();

  /**
   * Creates a stream of nodes from the attributes matching a set of names of this node.
   *
   * @param names the names
   * @return the attribute node stream
   */
  default Stream<Node> attributes(final String... names) {
    return this.attributes(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the attributes matching a set of names of this node.
   *
   * @param names the names
   * @return the attribute node stream
   */
  Stream<Node> attributes(final Collection<String> names);

  /**
   * Creates a stream of nodes from the children elements of this node.
   *
   * @return the element node stream
   */
  Stream<Node> elements();

  /**
   * Creates a stream of nodes from the children elements matching a set of names of this node.
   *
   * @param names the names
   * @return the element node stream
   */
  default Stream<Node> elements(final String... names) {
    return this.elements(ImmutableSet.copyOf(names));
  }

  /**
   * Creates a stream of nodes from the children elements matching a set of names of this node.
   *
   * @param names the names
   * @return the element node stream
   */
  Stream<Node> elements(final Collection<String> names);
}
