/**
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   CEA LIST - Initial API and implementation
 */
package aspects.impl.diagram.editparts;

import org.eclipse.gmf.codegen.gmfgen.GenExternalNodeLabel;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * @author Mickael ADAM
 */
@SuppressWarnings("all")
public class ExternalNodeLabelEditPart extends impl.diagram.editparts.ExternalNodeLabelEditPart {
  public CharSequence additionalEditPolicies(final GenExternalNodeLabel it) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.ExternalLabelPrimaryDragRoleEditPolicy());");
    _builder.newLine();
    return _builder;
  }
}
