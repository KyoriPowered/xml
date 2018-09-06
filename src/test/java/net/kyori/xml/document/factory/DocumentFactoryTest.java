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
package net.kyori.xml.document.factory;

import net.kyori.lambda.Composer;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.located.LocatedJDOMFactory;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentFactoryTest {
  @Test
  void test() throws URISyntaxException, XMLException {
    final DocumentFactory factory = DocumentFactory.builder()
      .builder(Composer.accept(new SAXBuilder(), builder -> builder.setJDOMFactory(new LocatedJDOMFactory())))
      .includePaths(path("/includes"))
      .build();
    final Document document = factory.read(path("/include_test.xml"));
    final Node node = Node.of(document.getRootElement());
    assertEquals(3, node.nodes().count());
    assertEquals(1, node.nodes("things").count());
    assertEquals(2, node.nodes("thingy").count());
  }

  private static Path path(final String path) throws URISyntaxException {
    return Paths.get(DocumentFactoryTest.class.getResource(path).toURI());
  }
}
