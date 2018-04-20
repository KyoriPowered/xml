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

import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class NodeStreamImpl implements NodeStream {
  private final @NonNull Stream<Node> stream;

  NodeStreamImpl(final @NonNull Stream<Node> stream) {
    this.stream = stream;
  }

  @Override
  public @NonNull Stream<Node> stream() {
    return this.stream;
  }

  @Override
  public @NonNull NodeStream filter(final @NonNull Predicate<? super Node> predicate) {
    return new NodeStreamImpl(this.stream.filter(predicate));
  }

  @Override
  public @NonNull NodeStream flatMap(final @NonNull Function<? super Node, ? extends NodeStream> function) {
    return new NodeStreamImpl(this.stream.flatMap(node -> function.apply(node).stream()));
  }

  @Override
  public void close() {
    this.stream.close();
  }
}
