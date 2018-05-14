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

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import net.kyori.xml.XMLException;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RangeParser<C extends Comparable<C>> implements PrimitiveParser<Range<C>> {
  private static final Pattern PATTERN = Pattern.compile("([(|\\[])((?:\\d+\\.?\\d*?|-∞|-oo))?(?:\\.{2}|‥)((?:\\d+\\.?\\d*?|\\+∞|\\+oo))?([)|\\]])");
  private final PrimitiveParser<C> parser;

  @Inject
  public RangeParser(final PrimitiveParser<C> parser) {
    this.parser = parser;
  }

  @Override
  public @NonNull Range<C> throwingParse(final @NonNull Node node, final @NonNull String string) throws XMLException {
    final Matcher matcher = PATTERN.matcher(string);
    if(!matcher.matches()) {
      final C single = this.parser.throwingParse(node, string);
      if(single != null) {
        return Range.singleton(single);
      }
      throw new XMLException(node, "Could not parse '" + string + "' as a range");
    }

    final BoundType lowerType = boundType(matcher.group(1));
    final /* @Nullable */ C lower = this.parse(node, matcher.group(2));
    final /* @Nullable */ C upper = this.parse(node, matcher.group(3));
    final BoundType upperType = boundType(matcher.group(4));

    if(lower == null) {
      if(upper == null) {
        return Range.all();
      } else {
        return Range.upTo(upper, upperType);
      }
    } else if(upper == null) {
      return Range.downTo(lower, lowerType);
    } else {
      return Range.range(lower, lowerType, upper, upperType);
    }
  }

  private C parse(final Node node, final String string) throws XMLException {
    switch(string) {
      case "-∞":
      case "-oo":
        return null;
      default:
        return this.parser.throwingParse(node, string);
      case "+∞":
      case "+oo":
        return null;
    }
  }

  private static BoundType boundType(final String string) {
    if("(".equals(string) || ")".equals(string)) {
      return BoundType.OPEN;
    }
    return BoundType.CLOSED;
  }
}
