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

import org.jdom2.Element;
import org.jdom2.located.Located;
import org.jdom2.located.LocatedElement;

/**
 * An alternative to {@link Element#clone()}.
 */
public class ClonedElement extends LocatedElement {
  public ClonedElement(final Element that) {
    super(that.getName(), that.getNamespace());

    // Element#clone() does not copy over the parent, but we do
    this.setParent(that.getParent());

    // The source element is not required to be located
    if(that instanceof Located) {
      this.setLine(((Located) that).getLine());
      this.setColumn(((Located) that).getColumn());
    }

    // Copy over additional namespaces
    that.getAdditionalNamespaces().forEach(this::addNamespaceDeclaration);

    // Copy over attributes
    that.getAttributes().forEach(attribute -> this.setAttribute(attribute.clone()));

    // Copy content
    this.setContent(that.cloneContent());
  }
}
