/*
 * TODO: add license
 */

package org.cakephp.netbeans;

import java.util.Collections;
import java.util.List;
import javax.swing.Action;
import org.cakephp.netbeans.ui.actions.RunBakeAction;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.openide.util.NbBundle;

public class CakePhpActionsExtender extends PhpModuleActionsExtender {
    private static final List<Action> ACTIONS = Collections.<Action>singletonList(RunBakeAction.getInstance());

    @Override
    public String getMenuName() {
        return NbBundle.getMessage(CakePhpActionsExtender.class, "LBL_MenuName");
    }

    @Override
    public List<? extends Action> getActions() {
        return ACTIONS;
    }
}
