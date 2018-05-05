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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A document factory.
 */
public interface DocumentFactory {
  /**
   * The name of the include element.
   */
  String INCLUDE_ELEMENT_NAME = "include";

  /**
   * Creates a new builder.
   *
   * @return a new builder
   */
  static @NonNull Builder builder() {
    return new DocumentFactoryBuilderImpl();
  }

  /**
   * Reads a document.
   *
   * @param path the path
   * @return the document
   * @throws XMLException if an exception was encountered while reading
   * @throws XMLException if an exception was encountered while parsing
   */
  @NonNull Document read(final @NonNull Path path) throws XMLException;

  /**
   * A document factory builder.
   */
  interface Builder {
    /**
     * Sets the sax builder.
     *
     * @param builder the sax builder
     * @return this builder
     */
    @NonNull Builder builder(final @NonNull SAXBuilder builder);

    /**
     * Sets the include paths.
     *
     * @param includePaths the include paths
     * @return this builder
     */
    default @NonNull Builder includePaths(final @NonNull Path... includePaths) {
      requireNonNull(includePaths, "include paths");
      return this.includePaths(Arrays.asList(includePaths));
    }

    /**
     * Sets the include paths.
     *
     * @param includePaths the include paths
     * @return this builder
     */
    @NonNull Builder includePaths(final @NonNull List<Path> includePaths);

    /**
     * Builds a document factory.
     *
     * @return the document factory
     */
    @NonNull DocumentFactory build();
  }
}
