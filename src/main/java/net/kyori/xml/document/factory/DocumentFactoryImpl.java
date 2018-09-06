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

import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

final class DocumentFactoryImpl implements DocumentFactory {
  private final SAXBuilder builder;
  private final List<Path> includePaths;

  DocumentFactoryImpl(final @NonNull DocumentFactoryBuilderImpl builder) {
    this.builder = builder.builder;
    this.includePaths = builder.includePaths;
  }

  @Override
  public @NonNull Document read(final @NonNull Path path) throws XMLException {
    final Document document = this.build(path);

    if(!this.includePaths.isEmpty()) {
      final PreProcessor pp = new PreProcessor(path);
      pp.processChildren(document.getRootElement());
    }

    return document;
  }

  private @NonNull Document build(final @NonNull Path path) throws XMLException {
    try {
      return this.builder.build(path.toFile());
    } catch(final IOException e) {
      throw new XMLException("Encountered an exception while reading", e);
    } catch(final JDOMException e) {
      throw new XMLException("Encountered an exception while parsing", e);
    }
  }

  final class PreProcessor {
    private final Path path;

    PreProcessor(final @NonNull Path path) {
      this.path = path;
    }

    void processChildren(final Element parent) throws XMLException {
      for(int i = 0; i < parent.getContentSize(); i++) {
        final Content content = parent.getContent(i);
        if(!(content instanceof Element)) {
          continue;
        }

        final Element child = (Element) content;
        if(this.processChild(i, parent, child)) {
          i--;
        } else {
          this.processChildren(child);
        }
      }
    }

    private boolean processChild(final int index, final Element parent, final Element child) throws XMLException {
      if(child.getName().equals(INCLUDE_ELEMENT_NAME)) {
        parent.setContent(index, this.readInclude(child));
        return true;
      }
      return false;
    }

    private List<Content> readInclude(final Element element) throws XMLException {
      final Path src = Paths.get(Node.of(element).attribute("src").get().value());
      return this.readInclude(src, element);
    }

    private List<Content> readInclude(final Path src, final Element include) throws XMLException {
      final /* @Nullable */ Path path = this.findInclude(src);
      if(path == null) {
        throw new XMLException(Node.of(include), "Failed to find include: " + src);
      }
      return this.readDocument(path).getRootElement().cloneContent();
    }

    private Document readDocument(final Path path) throws XMLException {
      final Document document = DocumentFactoryImpl.this.build(path);
      this.processChildren(document.getRootElement());
      return document;
    }

    private @Nullable Path findInclude(final Path include) {
      final List<Path> includePaths = new ArrayList<>(DocumentFactoryImpl.this.includePaths);
      final @Nullable Path basePath = this.path.getParent();
      if(basePath != null) {
        includePaths.add(0, basePath);
      }
      for(final Path includePath : includePaths) {
        final Path path = includePath.resolve(include);
        if(Files.isRegularFile(path)) {
          return path.toAbsolutePath();
        }
      }
      return null;
    }
  }
}
