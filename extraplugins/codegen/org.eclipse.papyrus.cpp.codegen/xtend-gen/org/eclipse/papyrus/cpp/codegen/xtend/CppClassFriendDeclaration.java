package org.eclipse.papyrus.cpp.codegen.xtend;

import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.C_Cpp.Friend;
import org.eclipse.papyrus.C_Cpp.NoCodeGen;
import org.eclipse.papyrus.acceleo.GenUtils;
import org.eclipse.papyrus.cpp.codegen.utils.CppGenUtils;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * @author Önder GÜRCAN (onder.gurcan@cea.fr)
 */
@SuppressWarnings("all")
public class CppClassFriendDeclaration {
  public static CharSequence CppClassFriendDeclaration(final Classifier friend) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("friend class ");
    String _cppQualifiedName = CppGenUtils.cppQualifiedName(friend);
    _builder.append(_cppQualifiedName, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static void CppClassIncludeFriendDeclaration(final Classifier clazz) {
    EList<Classifier> _usedClassifiers = GenUtils.getUsedClassifiers(clazz);
    for (final Classifier uc : _usedClassifiers) {
      boolean _and = false;
      boolean _hasStereotype = GenUtils.hasStereotype(uc, Friend.class);
      if (!_hasStereotype) {
        _and = false;
      } else {
        boolean _hasStereotype_1 = GenUtils.hasStereotype(uc, NoCodeGen.class);
        boolean _not = (!_hasStereotype_1);
        _and = _not;
      }
      if (_and) {
        CppClassFriendDeclaration.CppClassFriendDeclaration(uc);
      }
    }
  }
}
