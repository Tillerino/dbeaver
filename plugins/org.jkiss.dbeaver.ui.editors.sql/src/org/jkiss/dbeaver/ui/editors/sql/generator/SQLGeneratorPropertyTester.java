/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.editors.sql.generator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.jkiss.dbeaver.ui.ActionUtils;
import org.jkiss.dbeaver.ui.controls.resultset.IResultSetSelection;
import org.jkiss.dbeaver.ui.navigator.NavigatorUtils;

/**
 * SQLGeneratorPropertyTester
 */
public class SQLGeneratorPropertyTester extends PropertyTester
{
    public static final String NAMESPACE = "org.jkiss.dbeaver.ui.editors.sql.util";
    public static final String PROP_CAN_GENERATE = "canGenerate";

    public SQLGeneratorPropertyTester() {
        super();
    }

    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        if (!(receiver instanceof IWorkbenchPart)) {
            return false;
        }
        IStructuredSelection structuredSelection = NavigatorUtils.getSelectionFromPart((IWorkbenchPart)receiver);
        if (structuredSelection == null || structuredSelection.isEmpty()) {
            return false;
        }
        switch (property) {
            case PROP_CAN_GENERATE:
                if (structuredSelection instanceof IResultSetSelection) {
                    // Results
                    return ((IResultSetSelection) structuredSelection).getController().getModel().isSingleSource();
                } else {
                    return SQLGeneratorContributor.hasContributions(structuredSelection);
                }
        }
        return false;
    }

    public static void firePropertyChange(String propName)
    {
        ActionUtils.evaluatePropertyState(NAMESPACE + "." + propName);
    }

}