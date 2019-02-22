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

import net.kyori.mu.Maybe;
import net.kyori.mu.collection.MuSets;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public interface Node {
  /**
   * Creates a node from an attribute.
   *
   * @param attribute the attribute
   * @return a node
   */
  static @NonNull Node of(final org.jdom2.@NonNull Attribute attribute) {
    return AttributeNode.of(attribute);
  }

  /**
   * Creates a node from an element.
   *
   * @param element the element
   * @return a node
   */
  static @NonNull Node of(final org.jdom2.@NonNull Element element) {
    return ElementNode.of(element);
  }

  /**
   * Gets the name of this node.
   *
   * @return the name
   */
  @NonNull String name();

  /**
   * Gets the value of this node.
   *
   * @return the value
   */
  @NonNull String value();

  /**
   * Creates a stream of nodes from the attributes and children elements of this node.
   *
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> nodes() {
    return Stream.concat(this.attributes(), this.elements());
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a name.
   *
   * @param name the name
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> nodes(final @NonNull String name) {
    return this.nodes(Collections.singleton(name));
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> nodes(final @NonNull String... names) {
    return this.nodes(MuSets.mutable(names));
  }

  /**
   * Creates a stream of nodes from the attributes and children elements of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> nodes(final @NonNull Collection<String> names) {
    return Stream.concat(this.attributes(names), this.elements(names));
  }

  /**
   * Gets a single node by its name.
   *
   * @param name the name
   * @return the node
   * @throws IllegalArgumentException if more than one node named {@code name} is found
   */
  default @NonNull Maybe<Node> node(final @NonNull String name) {
    return this.nodes(name).collect(Maybe.collector());
  }

  /**
   * Creates a stream of nodes from the children elements of this node.
   *
   * @return a stream of nodes
   */
  @NonNull Stream<Node> elements();

  /**
   * Creates a stream of nodes from the children elements of this node matching a name.
   *
   * @param name the name
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> elements(final @NonNull String name) {
    return this.elements(Collections.singleton(name));
  }

  /**
   * Creates a stream of nodes from the children elements of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> elements(final @NonNull String... names) {
    return this.elements(MuSets.mutable(names));
  }

  /**
   * Creates a stream of nodes from the children elements of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  @NonNull Stream<Node> elements(final @NonNull Collection<String> names);

  /**
   * Gets a single element node by its name.
   *
   * @param name the name
   * @return the node
   * @throws IllegalArgumentException if more than one element named {@code name} is found
   */
  default @NonNull Maybe<Node> element(final @NonNull String name) {
    return this.elements(name).collect(Maybe.collector());
  }

  /**
   * Creates a stream of nodes from the attributes of this node.
   *
   * @return a stream of nodes
   */
  @NonNull Stream<Node> attributes();

  /**
   * Creates a stream of nodes from the attributes of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  default @NonNull Stream<Node> attributes(final @NonNull String... names) {
    return this.attributes(MuSets.mutable(names));
  }

  /**
   * Creates a stream of nodes from the attributes of this node matching a set of names.
   *
   * @param names the names
   * @return a stream of nodes
   */
  @NonNull Stream<Node> attributes(final @NonNull Collection<String> names);

  /**
   * Gets a single attribute node by its name.
   *
   * @param name the name
   * @return the node
   * @throws IllegalArgumentException if more than one attribute named {@code name} is found
   */
  default @NonNull Maybe<Node> attribute(final @NonNull String name) {
    return this.attributes(name).collect(Maybe.collector());
  }
}
