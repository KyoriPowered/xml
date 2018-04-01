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

import com.google.common.collect.MoreCollectors;
import net.kyori.xml.Testing;
import net.kyori.xml.XMLException;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NodeTest {
  private Node root;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.root = Testing.read("/node_test.xml");
  }

  @Test
  void testFilter() {
    assertEquals(3, this.root.elements().count());
    assertEquals(2, this.root.elements("name", "some-number").count());
  }

  @Test
  void testValues() {
    assertEquals("okay", this.root.attribute("potato").map(Node::value).orElse(null));
    assertEquals("test", this.root.elements("name").collect(MoreCollectors.onlyElement()).value());
    assertEquals("100", this.root.elements("some-number").collect(MoreCollectors.onlyElement()).value());
    assertEquals(Arrays.asList("bar", "baz"), this.root.elements("nested").collect(Collectors.toList()).stream()
      .flatMap(node -> node.elements("foo")).map(Node::value).collect(Collectors.toList()));
  }

  @Test
  void testMissingAttribute() {
    final XMLException exception = assertThrows(XMLException.class, () -> this.root.requireAttribute("nothing"));
    assertEquals("missing required attribute 'nothing'", exception.getMessage());
  }
}
