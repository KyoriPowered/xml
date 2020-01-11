/*
 * This file is part of xml, licensed under the MIT License.
 *
 * Copyright (c) 2018-2020 KyoriPowered
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
package net.kyori.xml.node.finder;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import net.kyori.xml.node.Node;
import net.kyori.xml.node.function.NodeFunction;
import net.kyori.xml.node.function.NodePredicate;
import net.kyori.xml.node.function.NodePredicates;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BranchLeafNodeFinder implements NodeDepthFinder {
  private final NodePredicate branchPredicate;
  private final NodeFunction branchMapper;
  private final NodePredicate leafPredicate;
  private final NodeFunction leafMapper;

  public BranchLeafNodeFinder(final @NonNull String branch, final @NonNull String leaf) {
    this(Collections.singleton(branch), Collections.singleton(leaf));
  }

  public BranchLeafNodeFinder(final @NonNull Collection<String> branches, final @NonNull Collection<String> leafs) {
    this(NodePredicates.named(branches), NodeFunction.identity(), NodePredicates.named(leafs), NodeFunction.identity());
  }

  public BranchLeafNodeFinder(final @NonNull NodePredicate branchPredicate, final @NonNull NodePredicate leafPredicate, final @NonNull NodeFunction mapper) {
    this(branchPredicate, mapper, leafPredicate, mapper);
  }

  public BranchLeafNodeFinder(final @NonNull NodePredicate branchPredicate, final @NonNull NodeFunction branchMapper, final @NonNull NodePredicate leafPredicate, final @NonNull NodeFunction leafMapper) {
    this.branchPredicate = branchPredicate;
    this.branchMapper = branchMapper;
    this.leafPredicate = leafPredicate;
    this.leafMapper = leafMapper;
  }

  @Override
  public @NonNull Stream<Node> nodes(final @NonNull Node node, final int depth) {
    if(this.branchPredicate.test(node, depth)) {
      return this.branchMapper.apply(node, depth).nodes().flatMap(parent -> this.nodes(parent, depth + 1));
    } else if(this.leafPredicate.test(node, depth)) {
      return Stream.of(this.leafMapper.apply(node, depth));
    }
    return Stream.empty();
  }
}
