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
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

/**
 * Parses a {@link Node} into an enum constant.
 */
public class EnumParser<E extends Enum<E>> implements PrimitiveParser<E> {
  private final TypeLiteral<E> type;
  private final Map<String, E> map = new HashMap<>();

  public EnumParser(final @NonNull Class<E> type) {
    this(TypeLiteral.get(type));
  }

  @Inject
  public EnumParser(final @NonNull TypeLiteral<E> type) {
    this.type = type;

    for(final E constant : ((Class<E>) type.getRawType()).getEnumConstants()) {
      this.map.put(constant.name().toLowerCase(Locale.ENGLISH), constant);
    }
  }

  @Override
  public @NonNull E throwingParse(final @NonNull Node node, final @NonNull String string) throws XMLException {
    final /* @Nullable */ E constant = this.map.get(string.toLowerCase(Locale.ENGLISH).replace(' ', '_'));
    if(constant != null) {
      return constant;
    }
    throw new XMLException(node, "Could not find " + this.type.getRawType().getName() + " with name '" + string + '\'');
  }
}
