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
package net.kyori.xml.element;

import net.kyori.xml.Testing;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClonedElementTest {
  private Element root;

  @BeforeAll
  void init() throws IOException, JDOMException {
    this.root = Testing.read("/clone_test.xml").element();
  }

  // sanity check - something has broken badly if this fails
  @Test
  void testClone() {
    assertElementEquals(this.root, this.root.clone());
  }

  @Test
  void testClonedElement() {
    assertElementEquals(this.root, new ClonedElement(this.root));
  }

  private static void assertElementEquals(final Element expected, final Element actual) {
    // The elements shouldn't be the same object...
    assertNotSame(actual, expected);

    // ...but they should both have the same about of attributes and children...
    assertEquals(expected.getAttributes().size(), actual.getAttributes().size());
    assertEquals(expected.getChildren().size(), actual.getChildren().size());

    // ...and the children should be equal...
    final Iterator<Element> expectedChildrenIt = expected.getChildren().iterator();
    final Iterator<Element> actualChildrenIt = actual.getChildren().iterator();
    while(expectedChildrenIt.hasNext()) {
      assertElementEquals(expectedChildrenIt.next(), actualChildrenIt.next());
    }

    // ...as should the attributes.
    expected.getAttributes().forEach(expectedAttribute -> {
      final Attribute actualAttribute = actual.getAttribute(expectedAttribute.getName());
      assertNotNull(actualAttribute);
      assertEquals(expectedAttribute.getValue(), actualAttribute.getValue());
    });
  }
}
