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
import org.checkerframework.checker.nullness.qual.NonNull;

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
  static @NonNull NodeStream empty() {
    return NodeStream.of(Stream.empty());
  }

  static @NonNull NodeStream of(final @NonNull Node node) {
    return of(Stream.of(node));
  }

  static @NonNull NodeStream of(final @NonNull Stream<Node> stream) {
    return new NodeStreamImpl(stream);
  }

  static @NonNull NodeStream concat(final @NonNull NodeStream a, final @NonNull NodeStream b) {
    return of(Stream.concat(a.stream(), b.stream()));
  }

  /**
   * Gets the backing stream.
   *
   * @return the stream
   */
  @NonNull Stream<Node> stream();

  /**
   * Returns a stream consisting of the nodes of this stream that match the given predicate.
   *
   * @param predicate a non-interfering, stateless predicate to apply to each node to determine if it should be included
   * @return a new stream
   * @see Stream#filter(Predicate)
   */
  @NonNull NodeStream filter(final @NonNull Predicate<? super Node> predicate);

  /**
   * Returns a stream consisting of the nodes of this stream that are named {@code name}.
   *
   * @param name the name
   * @return a new stream
   */
  default @NonNull NodeStream named(final @NonNull String name) {
    return this.named(Collections.singleton(name));
  }

  /**
   * Returns a stream consisting of the nodes of this stream whose name is in {@code names}.
   *
   * @param names the names
   * @return a new stream
   */
  default @NonNull NodeStream named(final @NonNull String... names) {
    return this.named(Sets.newHashSet(names));
  }

  /**
   * Returns a stream consisting of the nodes of this stream whose name is in {@code names}.
   *
   * @param names the names
   * @return a new stream
   */
  default @NonNull NodeStream named(final @NonNull Collection<String> names) {
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
  @NonNull NodeStream flatMap(final @NonNull Function<? super Node, ? extends NodeStream> function);

  /**
   * Performs an action for each node of this stream.
   *
   * @param action a non-interfering action to perform on the nodes
   * @see Stream#forEach(Consumer)
   */
  default void forEach(final @NonNull Consumer<? super Node> action) {
    this.stream().forEach(action);
  }

  /**
   * Performs a mutable reduction operation on the nodes of this stream using a {@link Collector}.
   *
   * @param collector the collector describing the reduction
   * @param <R> the type of the result
   * @param <A> the intermediate accumulation type of the {@code Collector}
   * @return the result of the reduction
   * @see Stream#collect(Collector)
   */
  default <R, A> R collect(final @NonNull Collector<? super Node, A, R> collector) {
    return this.stream().collect(collector);
  }

  /**
   * Returns an {@link Optional} describing some node of the stream, or an empty {@code Optional} if the stream is empty.
   *
   * @return an optional describing some node of the stream, or an empty optional if the stream is empty
   * @see Stream#findAny()
   */
  default @NonNull Optional<Node> findAny() {
    return this.stream().findAny();
  }

  /**
   * Returns an {@link Optional} describing the first node of this stream, or an empty {@code Optional} if the stream is empty.
   *
   * @return an optional describing the first node of this stream, or an empty optional if the stream is empty
   * @see Stream#findFirst()
   */
  default @NonNull Optional<Node> findFirst() {
    return this.stream().findFirst();
  }

  /**
   * Gets a stream element representing a single node.
   *
   * @return a stream element representing a single node
   * @throws IllegalArgumentException if the stream consists of two or more nodes
   */
  default @NonNull NodeStreamElement<Node> one() {
    final Optional<Node> node = this.collect(MoreCollectors.toOptional());
    return () -> node;
  }
}
