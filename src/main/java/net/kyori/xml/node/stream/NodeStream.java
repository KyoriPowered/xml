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
package net.kyori.xml.node.stream;

import com.google.common.collect.MoreCollectors;
import com.google.common.collect.Sets;
import net.kyori.lunar.CheckedAutoCloseable;
import net.kyori.xml.node.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * A stream of {@link Node node}s.
 *
 * @see Stream
 */
public interface NodeStream extends CheckedAutoCloseable {
  static NodeStream empty() {
    return NodeStream.of(Stream.empty());
  }

  static NodeStream of(final Node node) {
    return of(Stream.of(node));
  }

  static NodeStream of(final Stream<Node> stream) {
    return new NodeStreamImpl(stream);
  }

  static NodeStream concat(final NodeStream a, final NodeStream b) {
    return of(Stream.concat(a.stream(), b.stream()));
  }

  /**
   * Gets the backing stream.
   *
   * @return the stream
   */
  Stream<Node> stream();

  /**
   * Returns a stream consisting of the nodes of this stream that match the given predicate.
   *
   * @param predicate a non-interfering, stateless predicate to apply to each node to determine if it should be included
   * @return a new stream
   * @see Stream#filter(Predicate)
   */
  NodeStream filter(final Predicate<? super Node> predicate);

  /**
   * Returns a stream consisting of the nodes of this stream that are named {@code name}.
   *
   * @param name the name
   * @return a new stream
   */
  default NodeStream named(final String name) {
    return this.named(Collections.singleton(name));
  }

  /**
   * Returns a stream consisting of the nodes of this stream whose name is in {@code names}.
   *
   * @param names the names
   * @return a new stream
   */
  default NodeStream named(final String... names) {
    return this.named(Sets.newHashSet(names));
  }

  /**
   * Returns a stream consisting of the nodes of this stream whose name is in {@code names}.
   *
   * @param names the names
   * @return a new stream
   */
  default NodeStream named(final Collection<String> names) {
    return this.filter(node -> names.contains(node.name()));
  }

  /**
   * Returns a stream consisting of the results of applying the given
   * function to the nodes of this stream.
   *
   * @param mapper a non-interfering, stateless function to apply to each node
   * @param <R> the type of the new stream
   * @return a new stream
   */
  default <R> Stream<R> map(final Function<? super Node, ? extends R> mapper) {
    return this.stream().map(mapper);
  }

  /**
   * Returns a stream consisting of the results of replacing each node of this stream with the contents of a mapped stream
   * produced by applying the provided mapping function to each node.
   *
   * @param function a non-interfering, stateless function to apply to each node which produces a stream of new values
   * @return a new stream
   * @see Stream#flatMap(Function)
   */
  NodeStream flatMap(final Function<? super Node, ? extends NodeStream> function);

  /**
   * Performs an action for each node of this stream.
   *
   * @param action a non-interfering action to perform on the nodes
   * @see Stream#forEach(Consumer)
   */
  default void forEach(final Consumer<? super Node> action) {
    this.stream().forEach(action);
  }

  /**
   * Performs a mutable reduction operation on the nodes of this stream using a {@link Collector}.
   *
   * @param collector the collector describing the reduction
   * @return the result of the reduction
   * @see Stream#collect(Collector)
   */
  default <R, A> R collect(final Collector<? super Node, A, R> collector) {
    return this.stream().collect(collector);
  }

  /**
   * Returns an {@link Optional} describing some node of the stream, or an empty {@code Optional} if the stream is empty.
   *
   * @return an optional describing some node of the stream, or an empty optional if the stream is empty
   * @see Stream#findAny()
   */
  default Optional<Node> findAny() {
    return this.stream().findAny();
  }

  /**
   * Returns an {@link Optional} describing the first node of this stream, or an empty {@code Optional} if the stream is empty.
   *
   * @return an optional describing the first node of this stream, or an empty optional if the stream is empty
   * @see Stream#findFirst()
   */
  default Optional<Node> findFirst() {
    return this.stream().findFirst();
  }

  /**
   * Gets a stream element representing a single node.
   *
   * @return a stream element representing a single node
   * @throws IllegalArgumentException if the stream consists of two or more nodes
   */
  default NodeStreamElement<Node> one() {
    final Optional<Node> node = this.collect(MoreCollectors.toOptional());
    return () -> node;
  }
}
