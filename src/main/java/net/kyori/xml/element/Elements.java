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
package net.kyori.xml.element;

import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jdom2.Element;
import org.jdom2.Parent;
import org.jdom2.located.Located;
import org.jdom2.located.LocatedElement;

public interface Elements {
  /**
   * Copies an element from {@code source} into {@code target}.
   *
   * @param source the source element
   * @param target the target element
   * @see Element#clone()
   */
  static void copy(final @NonNull Element source, final @NonNull Element target) {
    copy(source, target, null);
  }

  /**
   * Copies an element from {@code source} into {@code target}.
   *
   * @param source the source element
   * @param target the target element
   * @param parent the parent setter
   * @see Element#clone()
   */
  static void copy(final @NonNull Element source, final @NonNull Element target, final @Nullable Consumer<Parent> parent) {
    if(parent != null) {
      // Element#clone() does not copy over the parent, but we do
      parent.accept(source.getParent());
    }

    // Copy over additional namespaces
    source.getAdditionalNamespaces().forEach(target::addNamespaceDeclaration);

    // Copy over attributes
    source.getAttributes().forEach(attribute -> target.setAttribute(attribute.clone()));

    // Copy content
    target.setContent(source.cloneContent());

    // The element is not guaranteed to be located
    if(source instanceof Located && target instanceof Located) {
      ((Located) target).setLine(((Located) source).getLine());
      ((Located) target).setColumn(((Located) source).getColumn());
    }
  }

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
   * @param source the source parent
   * @param target the target element
   */
  static void inherit(final @Nullable Element source, final @NonNull Element target) {
    if(source != null) {
      // Copy attributes from the source to the target if the target doesn't already
      // have an attribute with the same name.
      source.getAttributes().stream()
        .filter(attribute -> target.getAttribute(attribute.getName()) == null)
        .forEach(attribute -> target.setAttribute(attribute.clone()));
    }
  }

  /**
   * An element which inherits attributes from its parent element, if present.
   */
  interface Inherited {
    /**
     * Checks if {@code element} is an inherited element.
     *
     * @param element the element
     * @return {@code true} if {@code element} is an inherited element, {@code false} otherwise
     */
    static boolean is(final Element element) {
      return element instanceof Inherited;
    }

    /**
     * Creates an inherited element.
     *
     * @param element the element
     * @return the inherited element
     */
    static @NonNull Element of(final @NonNull Element element) {
      if(element instanceof LocatedElement) {
        return new LocatedImpl((LocatedElement) element);
      }
      return new Impl(element);
    }

    final class Impl extends Element implements Inherited {
      /* package */ Impl(final @NonNull Element that) {
        super(that.getName(), that.getNamespace());
        copy(that, this, this::setParent);
        inherit(that.getParent(), this);
      }
    }

    final class LocatedImpl extends LocatedElement implements Inherited {
      /* package */ LocatedImpl(final @NonNull LocatedElement that) {
        super(that.getName(), that.getNamespace());
        copy(that, this, this::setParent);
        inherit(that.getParent(), this);
      }
    }
  }
}
