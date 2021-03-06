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
package net.kyori.xml.node.function;

import com.google.common.collect.Sets;
import java.util.function.Predicate;
import net.kyori.xml.node.Node;
import org.junit.jupiter.api.Test;

import static net.kyori.xml.Testing.element;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NodePredicateTest {
  @Test
  void testSingleNamed() {
    final Predicate<Node> predicate = NodePredicates.named("bar");
    assertFalse(predicate.test(element("foo")));
    assertTrue(predicate.test(element("bar")));
    assertFalse(predicate.test(element("baz")));
  }

  @Test
  void testArrayNamed() {
    final Predicate<Node> predicate = NodePredicates.named("foo", "bar");
    assertTrue(predicate.test(element("foo")));
    assertTrue(predicate.test(element("bar")));
    assertFalse(predicate.test(element("baz")));
  }

  @Test
  void testCollectionNamed() {
    final Predicate<Node> predicate = NodePredicates.named(Sets.newHashSet("foo", "bar"));
    assertTrue(predicate.test(element("foo")));
    assertTrue(predicate.test(element("bar")));
    assertFalse(predicate.test(element("baz")));
  }
}
