package org.eclipse.papyrus.cpp.codegen.xtend;

import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.cpp.codegen.xtend.CppAttribute;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * @author Önder GÜRCAN (onder.gurcan@cea.fr)
 */
@SuppressWarnings("all")
public class CppClassAttributesDeclaration {
  public static CharSequence CppClassAttributesDeclaration(final Classifier clazz, final VisibilityKind visibilityFilter) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Property> _ownedAttributes = CppAttribute.getOwnedAttributes(clazz);
      final Function1<Property, Boolean> _function = new Function1<Property, Boolean>() {
        public Boolean apply(final Property it) {
          VisibilityKind _visibility = it.getVisibility();
          return Boolean.valueOf(Objects.equal(_visibility, visibilityFilter));
        }
      };
      Iterable<Property> _filter = IterableExtensions.<Property>filter(_ownedAttributes, _function);
      for(final Property oa : _filter) {
        CharSequence _CppAttributeDeclaration = CppAttribute.CppAttributeDeclaration(oa);
        _builder.append(_CppAttributeDeclaration, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
