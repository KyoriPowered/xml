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
package net.kyori.xml.node.flattener;

import net.kyori.xml.node.Node;
import net.kyori.xml.node.filter.NodeFilter;
import net.kyori.xml.node.filter.NodeFilters;
import net.kyori.xml.node.stream.NodeStream;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.Set;

/**
 * A node flattener that flattens a node into a stream of leaf nodes gathered from branch nodes.
 */
public class BranchLeafNodeFlattener extends NodeFlattener.Impl {
  private final @NonNull NodeFilter branchFilter;
  private final @NonNull NodeFilter leafFilter;

  public BranchLeafNodeFlattener(final @NonNull String branch, final @NonNull String leaf) {
    this(Collections.singleton(branch), Collections.singleton(leaf));
  }

  public BranchLeafNodeFlattener(final @NonNull Set<String> branches, final @NonNull Set<String> leafs) {
    this(NodeFilters.named(branches), NodeFilters.named(leafs));
  }

  public BranchLeafNodeFlattener(final @NonNull NodeFilter branchFilter, final @NonNull NodeFilter leafFilter) {
    this.branchFilter = branchFilter;
    this.leafFilter = leafFilter;
  }

  @Override
  public @NonNull NodeStream flatten(final @NonNull Node node, final int depth) {
    if(this.branchFilter.test(node, depth)) {
      return this.node(node, depth).nodes().flatMap(child -> this.flatten(child, depth + 1));
    } else if(this.leafFilter.test(node, depth)) {
      return NodeStream.of(this.node(node, depth));
    }
    return NodeStream.empty();
  }
}
