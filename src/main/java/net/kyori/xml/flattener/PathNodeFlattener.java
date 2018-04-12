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
package net.kyori.xml.flattener;

import net.kyori.xml.filter.NodeFilter;
import net.kyori.xml.node.Node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * A flattener which follows a path of node names.
 */
public class PathNodeFlattener extends NodeFlattener.Impl {
  private final NodeFilter filter;
  private final List<String> path;

  public PathNodeFlattener(final NodeFilter filter, final String... path) {
    this(filter, Arrays.asList(path));
  }

  public PathNodeFlattener(final NodeFilter filter, final List<String> path) {
    this.filter = filter;
    this.path = path;
  }

  @Override
  public Stream<Node> flatten(final Node node, final int depth) {
    if(this.path.size() > depth && this.filter.test(node, depth)) {
      return node.nodes(this.path.get(depth)).flatMap(that -> this.flatten(that, depth + 1));
    }
    return Stream.of(this.node(node, depth));
  }
}
