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
package net.kyori.xml.node.filter;

import com.google.common.collect.Sets;
import net.kyori.xml.node.Node;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NodeFilterTest {
  @Test
  void testAlwaysFalse() {
    final NodeFilter filter = NodeFilters.alwaysFalse();
    final Node node = Node.of(new Element("name"));
    assertFalse(filter.test(node, 0));
    assertFalse(filter.test(node, 1));
  }

  @Test
  void testAlwaysTrue() {
    final NodeFilter filter = NodeFilters.alwaysTrue();
    final Node node = Node.of(new Element("name"));
    assertTrue(filter.test(node, 0));
    assertTrue(filter.test(node, 1));
  }

  @Test
  void testOnlyAttributes() {
    final NodeFilter filter = NodeFilters.onlyAttributes();
    assertFalse(filter.test(Node.of(new Element("name")), 0));
    assertTrue(filter.test(Node.of(new Attribute("name", "value")), 1));
  }

  @Test
  void testOnlyElements() {
    final NodeFilter filter = NodeFilters.onlyElements();
    assertTrue(filter.test(Node.of(new Element("name")), 0));
    assertFalse(filter.test(Node.of(new Attribute("name", "value")), 1));
  }

  @Test
  void testMinimumDepth() {
    final NodeFilter filter = NodeFilters.minimumDepth(3);
    final Node node = Node.of(new Element("name"));
    assertFalse(filter.test(node, 0));
    assertFalse(filter.test(node, 1));
    assertFalse(filter.test(node, 2));
    assertTrue(filter.test(node, 3));
    assertTrue(filter.test(node, 4));
  }

  @Test
  void testSingleNamed() {
    final NodeFilter filter = NodeFilters.named("bar");
    assertFalse(filter.test(Node.of(new Element("foo")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 1));
    assertFalse(filter.test(Node.of(new Element("baz")), 2));
  }

  @Test
  void testArrayNamed() {
    final NodeFilter filter = NodeFilters.named("foo", "bar");
    assertTrue(filter.test(Node.of(new Element("foo")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 1));
    assertFalse(filter.test(Node.of(new Element("baz")), 2));
  }

  @Test
  void testCollectionNamed() {
    final NodeFilter filter = NodeFilters.named(Sets.newHashSet("foo", "bar"));
    assertTrue(filter.test(Node.of(new Element("foo")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 0));
    assertTrue(filter.test(Node.of(new Element("bar")), 1));
    assertFalse(filter.test(Node.of(new Element("baz")), 2));
  }
}
