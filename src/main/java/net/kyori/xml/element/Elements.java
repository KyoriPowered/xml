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

import net.kyori.xml.node.ElementNode;
import net.kyori.xml.node.Node;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.Element;
import org.jdom2.Parent;

public interface Elements {
  /**
   * Inherit attributes from {@code source} into {@code target}.
   *
   * @param source the source parent
   * @param target the target element
   */
  static void inherit(final @Nullable Parent source, final @NonNull Element target) {
    if(source instanceof Element) {
      inherit((Element) source, target);
    }
  }

  /**
   * Inherit attributes from {@code source} into {@code target}.
   *
   * @param source the source element
   * @param target the target element
   */
  static void inherit(final @Nullable Element source, final @NonNull Element target) {
    if(source == null) {
      return;
    }

    // Copy attributes from the source to the target if the target doesn't already
    // have an attribute with the same name.
    source.getAttributes().stream()
      .filter(attribute -> target.getAttribute(attribute.getName()) == null)
      .forEach(attribute -> target.setAttribute(attribute.clone()));
  }

  /**
   * Returns an element which inherits attributes from its parent element, if present.
   *
   * @param node the node
   * @return the inherited element
   */
  static @NonNull Node inherited(final @NonNull Node node) {
    if(node instanceof ElementNode) {
      return Node.of(inherited(((ElementNode) node).element()));
    }
    return node;
  }

  /**
   * Returns an element which inherits attributes from its parent element, if present.
   *
   * @param element the element
   * @return the inherited element
   */
  static @NonNull Element inherited(final @NonNull Element element) {
    // Avoid inheriting on an already inherited element
    if(element instanceof InheritedElement) {
      return element;
    }
    return new InheritedElement(element);
  }
}
