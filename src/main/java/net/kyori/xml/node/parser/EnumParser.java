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

import com.google.inject.TypeLiteral;
import net.kyori.lambda.Maybe;
import net.kyori.lambda.reflect.Fields;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import javax.inject.Inject;

/**
 * Parses a {@link Node} into an enum constant.
 */
public class EnumParser<E extends Enum<E>> implements PrimitiveParser<E> {
  private final Class<E> type;
  private final Map<String, E> map;

  @Inject
  public EnumParser(final @NonNull TypeLiteral<E> type) {
    this((Class<E>) type.getRawType());
  }

  public EnumParser(final @NonNull Class<E> type) {
    this.type = type;

    final E[] constants = type.getEnumConstants();
    this.map = new HashMap<>(constants.length);
    for(final E constant : constants) {
      getNames(constant).forEach(name -> this.map.put(name, constant));
    }
  }

  @Override
  public @NonNull E throwingParse(final @NonNull Node node, final @NonNull String string) throws XMLException {
    final /* @Nullable */ E constant = this.map.get(string);
    if(constant != null) {
      return constant;
    }
    throw new XMLException(node, "Could not find " + this.type.getName() + " with name '" + string + '\'');
  }

  private static Stream<String> getNames(final Enum<?> constant) {
    return Maybe.maybe(Fields.get(constant).getAnnotation(Names.class)).map(names -> Arrays.stream(names.value())).orGet(() -> {
      final String name = constant.name();
      return Stream.of(
        name,
        name.replace('_', ' '),
        name.toLowerCase(Locale.ENGLISH),
        name.toLowerCase(Locale.ENGLISH).replace('_', ' ')
      );
    });
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface Names {
    /**
     * Gets the names used for parsing.
     *
     * @return the names
     */
    String[] value();
  }
}
