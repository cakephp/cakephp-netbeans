/*
 * TODO: add license
 */

package org.cakephp.netbeans;

import java.io.File;
import org.netbeans.modules.php.api.phpmodule.BadgeIcon;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.netbeans.modules.php.spi.commands.FrameworkCommandSupport;
import org.netbeans.modules.php.spi.editor.EditorExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpFrameworkProvider;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleIgnoredFilesExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public final class CakePhpFrameworkProvider extends PhpFrameworkProvider {

    // TODO: provide better badge icon
    private static final String ICON_PATH = "org/cakephp/netbeans/ui/resources/cakephp_badge_8.png"; // NOI18N
    private static final CakePhpFrameworkProvider INSTANCE = new CakePhpFrameworkProvider();

    private final BadgeIcon badgeIcon;

    private CakePhpFrameworkProvider() {
        super(NbBundle.getMessage(CakePhpFrameworkProvider.class, "LBL_CakePhpFramework"), NbBundle.getMessage(CakePhpFrameworkProvider.class, "LBL_CakePhpDescription"));
        badgeIcon = new BadgeIcon(
                ImageUtilities.loadImage(ICON_PATH),
                CakePhpFrameworkProvider.class.getResource("/" + ICON_PATH)); // NOI18N
    }

    @PhpFrameworkProvider.Registration(position=500)
    public static CakePhpFrameworkProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public BadgeIcon getBadgeIcon() {
        return badgeIcon;
    }

    @Override
    public boolean isInPhpModule(PhpModule phpModule) {
        // TODO: is this detection enough?
        FileObject cake = phpModule.getSourceDirectory().getFileObject("cake"); // NOI18N
        return cake != null && cake.isFolder();
    }

    @Override
    public File[] getConfigurationFiles(PhpModule phpModule) {
        return new File[0];
    }

    @Override
    public PhpModuleExtender createPhpModuleExtender(PhpModule phpModule) {
        // TODO: can we non-interactively create a project via 'cake' command?
        return null;
    }

    @Override
    public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {
        return new PhpModuleProperties();
    }

    @Override
    public PhpModuleActionsExtender getActionsExtender(PhpModule phpModule) {
        return null;
    }

    @Override
    public PhpModuleIgnoredFilesExtender getIgnoredFilesExtender(PhpModule phpModule) {
        return null;
    }

    @Override
    public FrameworkCommandSupport getFrameworkCommandSupport(PhpModule phpModule) {
        // TODO: provide list of commands (preferably in XML format)
        return null;
    }

    @Override
    public EditorExtender getEditorExtender(PhpModule phpModule) {
        return null;
    }
}
