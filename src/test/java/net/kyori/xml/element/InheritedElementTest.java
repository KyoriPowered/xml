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
package net.kyori.xml.element;

import java.io.IOException;
import java.util.NoSuchElementException;
import net.kyori.xml.Testing;
import net.kyori.xml.node.ElementNode;
import net.kyori.xml.node.Node;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InheritedElementTest {
  private Node root;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.root = Testing.read("/inherited_test.xml");
  }

  @Test
  void testInherit() {
    final Node source = this.root.element("things").orThrow(NoSuchElementException::new).element("thing").orThrow(NoSuchElementException::new);
    assertEquals("fed", source.attribute("abc").orThrow(NoSuchElementException::new).value());
    assertNull(source.attribute("ghi").orDefault(null));

    final Node target = ((ElementNode) source).inherited();
    assertEquals("fed", target.attribute("abc").orThrow(NoSuchElementException::new).value());
    assertEquals("jkl", target.attribute("ghi").orThrow(NoSuchElementException::new).value());

    final Node again = ((ElementNode) target).inherited();
    assertSame(target, again);
  }
}
