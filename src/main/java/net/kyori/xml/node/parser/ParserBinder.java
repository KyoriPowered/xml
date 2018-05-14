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
package net.kyori.xml.node.parser;

import com.google.common.collect.Range;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import net.kyori.violet.FriendlyTypeLiteral;
import net.kyori.violet.TypeArgument;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A parser binder.
 */
public class ParserBinder {
  private final Binder binder;

  public ParserBinder(final @NonNull Binder binder) {
    this.binder = binder;
  }

  /**
   * Creates a binding builder for binding a parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T> @NonNull AnnotatedBindingBuilder<Parser<T>> bindParser(final @NonNull Class<T> type) {
    return this.bindParser(TypeLiteral.get(type));
  }

  /**
   * Creates a binding builder for binding a parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T> @NonNull AnnotatedBindingBuilder<Parser<T>> bindParser(final @NonNull TypeLiteral<T> type) {
    return this.binder.bind(new FriendlyTypeLiteral<Parser<T>>() {}.where(new TypeArgument<T>(type) {}));
  }

  /**
   * Creates a binding builder for binding a primitive parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T> @NonNull AnnotatedBindingBuilder<PrimitiveParser<T>> bindPrimitiveParser(final @NonNull Class<T> type) {
    return this.bindPrimitiveParser(TypeLiteral.get(type));
  }

  /**
   * Creates a binding builder for binding a primitive parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T> @NonNull AnnotatedBindingBuilder<PrimitiveParser<T>> bindPrimitiveParser(final @NonNull TypeLiteral<T> type) {
    return this.binder.bind(new FriendlyTypeLiteral<PrimitiveParser<T>>() {}.where(new TypeArgument<T>(type) {}));
  }

  /**
   * Creates a binding builder for binding a range parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T extends Comparable<T>> @NonNull AnnotatedBindingBuilder<Parser<Range<T>>> bindRangeParser(final @NonNull Class<T> type) {
    return this.bindRangeParser(TypeLiteral.get(type));
  }

  /**
   * Creates a binding builder for binding a range parser for {@code T}.
   *
   * @param type the type
   * @param <T> the type
   * @return a binding builder
   */
  public <T extends Comparable<T>> @NonNull AnnotatedBindingBuilder<Parser<Range<T>>> bindRangeParser(final @NonNull TypeLiteral<T> type) {
    return this.binder.bind(new FriendlyTypeLiteral<Parser<Range<T>>>() {}.where(new TypeArgument<T>(type) {}));
  }
}