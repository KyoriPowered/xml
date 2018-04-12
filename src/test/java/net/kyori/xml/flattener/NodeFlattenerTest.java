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

import com.google.common.collect.MoreCollectors;
import net.kyori.xml.Testing;
import net.kyori.xml.filter.NodeFilters;
import net.kyori.xml.node.ElementNode;
import net.kyori.xml.node.Node;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NodeFlattenerTest {
  private ElementNode root;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.root = Testing.read("/flattener_test.xml");
  }

  @Test
  void testBranch() {
    final NodeFlattener flattener = new BranchLeafNodeFlattener(NodeFilters.named("things"), NodeFilters.named("thing"));

    final List<Node> nodes = flattener.flatten(this.root.elements().collect(MoreCollectors.onlyElement())).collect(Collectors.toList());
    assertEquals(2, nodes.size());
    nodes.forEach(node -> assertEquals("thing", node.name()));
  }

  @Test
  void testPath() {
    final NodeFlattener flattener = new PathNodeFlattener(NodeFilters.alwaysTrue(), "things", "thing", "deeper", "child");

    final List<Node> nodes = flattener.flatten(this.root).collect(Collectors.toList());
    assertEquals(12, nodes.size());
    nodes.forEach(node -> assertEquals("child", node.name()));
  }
}
