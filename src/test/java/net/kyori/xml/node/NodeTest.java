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
package net.kyori.xml.node;

import java.io.IOException;
import java.util.Arrays;
import net.kyori.xml.Testing;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.google.common.truth.Truth8.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NodeTest {
  private static final int EXPECTED_ATTRIBUTES = 2;
  private static final int EXPECTED_ELEMENTS = 4;
  private Node node;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.node = Testing.read("/node/test.xml");
  }

  @Test
  void testNodes() {
    assertThat(this.node.nodes()).hasSize(EXPECTED_ATTRIBUTES + EXPECTED_ELEMENTS);
    assertThat(this.node.nodes("a-attribute")).hasSize(1);
    assertThat(this.node.nodes("b-element")).hasSize(2);
    assertThat(this.node.nodes("a-element", "b-element")).hasSize(3);
    assertThat(this.node.nodes(Arrays.asList("b-element", "c-element"))).hasSize(3);
  }

  @Test
  void testNode() {
    assertThat(this.node.node("a-attribute").map(Node::value).optional()).hasValue("foo");
    assertThat(this.node.node("a-element").map(Node::value).optional()).hasValue("baz");
    assertThat(this.node.node("abc").map(Node::value).optional()).isEmpty();
  }

  @Test
  void testElements() {
    assertThat(this.node.elements()).hasSize(EXPECTED_ELEMENTS);
    assertThat(this.node.elements("b-element")).hasSize(2);
    assertThat(this.node.elements("a-element", "b-element")).hasSize(3);
    assertThat(this.node.elements(Arrays.asList("a-element", "b-element"))).hasSize(3);
  }

  @Test
  void testElement() {
    assertThat(this.node.element("a-element").map(Node::value).optional()).hasValue("baz");
    assertThat(this.node.element("abc").map(Node::value).optional()).isEmpty();
  }

  @Test
  void testAttributes() {
    assertThat(this.node.attributes()).hasSize(EXPECTED_ATTRIBUTES);
    assertThat(this.node.attributes("a-attribute")).hasSize(1);
    assertThat(this.node.attributes("a-attribute", "b-attribute")).hasSize(2);
    assertThat(this.node.attributes(Arrays.asList("a-attribute", "b-attribute"))).hasSize(2);
  }

  @Test
  void testAttribute() {
    assertThat(this.node.attribute("a-attribute").map(Node::value).optional()).hasValue("foo");
    assertThat(this.node.attribute("abc").map(Node::value).optional()).isEmpty();
  }
}
