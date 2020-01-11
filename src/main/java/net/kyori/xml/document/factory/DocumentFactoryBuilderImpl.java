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
package net.kyori.xml.document.factory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jdom2.input.SAXBuilder;

import static java.util.Objects.requireNonNull;

final class DocumentFactoryBuilderImpl implements DocumentFactory.Builder {
  @MonotonicNonNull SAXBuilder builder;
  @NonNull List<Path> includePaths = Collections.emptyList();

  @Override
  public DocumentFactory.@NonNull Builder builder(final @NonNull SAXBuilder builder) {
    this.builder = requireNonNull(builder, "builder");
    return this;
  }

  @Override
  public DocumentFactory.@NonNull Builder includePaths(final @NonNull List<Path> includePaths) {
    this.includePaths = requireNonNull(includePaths, "include paths");
    return this;
  }

  @Override
  public @NonNull DocumentFactory build() {
    requireNonNull(this.builder, "builder");
    final List<Path> includePaths = this.includePaths;
    if(includePaths.isEmpty()) {
      return new SimpleDocumentFactory(this.builder);
    } else {
      return new IncludingDocumentFactory(this.builder, new ArrayList<>(includePaths));
    }
  }
}
