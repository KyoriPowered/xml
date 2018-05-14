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
package net.kyori.xml.node.parser.number;

import com.google.inject.TypeLiteral;
import net.kyori.violet.AbstractModule;
import net.kyori.xml.node.parser.ParserBinder;
import net.kyori.xml.node.parser.RangeParser;

/**
 * A module that binds parsers for numbers.
 */
public final class NumberParserModule extends AbstractModule {
  @Override
  protected void configure() {
    final ParserBinder parsers = new ParserBinder(this.binder());

    // normal
    parsers.bindParser(Byte.class).to(ByteParser.class);
    parsers.bindParser(Double.class).to(DoubleParser.class);
    parsers.bindParser(Float.class).to(FloatParser.class);
    parsers.bindParser(Integer.class).to(IntegerParser.class);
    parsers.bindParser(Long.class).to(LongParser.class);
    parsers.bindParser(Short.class).to(ShortParser.class);

    // primitive
    parsers.bindPrimitiveParser(Byte.class).to(ByteParser.class);
    parsers.bindPrimitiveParser(Double.class).to(DoubleParser.class);
    parsers.bindPrimitiveParser(Float.class).to(FloatParser.class);
    parsers.bindPrimitiveParser(Integer.class).to(IntegerParser.class);
    parsers.bindPrimitiveParser(Long.class).to(LongParser.class);
    parsers.bindPrimitiveParser(Short.class).to(ShortParser.class);

    // range
    parsers.bindRangeParser(Byte.class).to(new TypeLiteral<RangeParser<Byte>>() {});
    parsers.bindRangeParser(Double.class).to(new TypeLiteral<RangeParser<Double>>() {});
    parsers.bindRangeParser(Float.class).to(new TypeLiteral<RangeParser<Float>>() {});
    parsers.bindRangeParser(Integer.class).to(new TypeLiteral<RangeParser<Integer>>() {});
    parsers.bindRangeParser(Long.class).to(new TypeLiteral<RangeParser<Long>>() {});
    parsers.bindRangeParser(Short.class).to(new TypeLiteral<RangeParser<Short>>() {});
  }
}
