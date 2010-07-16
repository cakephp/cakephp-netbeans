/*
 * TODO: add license
 */

package org.cakephp.netbeans.util;

import java.io.File;
import java.util.regex.Pattern;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.spi.project.support.ant.PropertyUtils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public final class CakePhpUtils {
    private static final String CONTROLLER_CLASS_SUFFIX = "Controller"; // NOI18N
    private static final String CONTROLLER_FILE_SUFIX = "_controller"; // NOI18N
    private static final String DIR_CONTROLLERS = "controllers"; // NOI18N
    private static final String DIR_VIEWS = "views"; // NOI18N
    private static final String FILE_VIEW_EXT = "ctp"; // NOI18N
    private static final String UNDERSCORE = "_"; // NOI18N

    private static final String FILE_CONTROLLER_RELATIVE = "../../" + DIR_CONTROLLERS + "/%s.php"; // NOI18N
    private static final String FILE_VIEW_RELATIVE = "../" + DIR_VIEWS + "/%s/%s." + FILE_VIEW_EXT; // NOI18N

    private CakePhpUtils() {
    }

    public static boolean isView(FileObject fo) {
        if (!fo.isData() || !fo.getExt().equals(FILE_VIEW_EXT)) {
            return false;
        }
        File file = FileUtil.toFile(fo);
        File parent = file.getParentFile(); // controller
        if (parent == null) {
            return false;
        }
        parent = parent.getParentFile(); // scripts
        if (parent == null) {
            return false;
        }
        return DIR_VIEWS.equals(parent.getName());
    }

    public static FileObject getView(FileObject controller, PhpBaseElement phpElement) {
        FileObject view = null;
        if (phpElement instanceof PhpClass.Method) {
            view = getView(controller, getViewFileName(phpElement.getName()));
        }
        return view;
    }

    private static FileObject getView(FileObject controller, String viewName) {
        File parent = FileUtil.toFile(controller).getParentFile();
        File view = PropertyUtils.resolveFile(parent, String.format(FILE_VIEW_RELATIVE, getViewFolderName(controller.getName()), viewName));
        if (view.isFile()) {
            return FileUtil.toFileObject(view);
        }
        return null;
    }

    public static boolean isControllerName(String name) {
        return name.endsWith(CakePhpUtils.CONTROLLER_CLASS_SUFFIX);
    }

    public static boolean isControllerFileName(String filename) {
        return filename.endsWith(CakePhpUtils.CONTROLLER_FILE_SUFIX);
    }

    public static boolean isController(FileObject fo) {
        return fo.isData()
                && isControllerFileName(fo.getName())
                && fo.getParent().getNameExt().equals(DIR_CONTROLLERS);
    }

    public static FileObject getController(FileObject view) {
        File parent = FileUtil.toFile(view).getParentFile();
        File action = PropertyUtils.resolveFile(parent, String.format(FILE_CONTROLLER_RELATIVE, getControllerFileName(parent.getName())));
        if (action.isFile()) {
            return FileUtil.toFileObject(action);
        }
        return null;
    }

    public static String getActionName(FileObject view) {
        return getActionName(view.getName());
    }

    // unit tests
    static String getActionName(String viewName) {
        return toCamelCase(viewName, true);
    }

    private static String getControllerFileName(String viewName) {
        return viewName + CONTROLLER_FILE_SUFIX;
    }

    // unit tests
    static String getViewFileName(String actionName) {
        return toUnderscore(actionName);
    }

    private static String getViewFolderName(String controllerFileName) {
        return controllerFileName.replace(CONTROLLER_FILE_SUFIX, ""); // NOI18N
    }

    // my_posts -> MyPosts or myPosts
    private static String toCamelCase(String underscored, boolean firstLowerCase) {
        StringBuilder sb = new StringBuilder(underscored.length());
        boolean first = firstLowerCase;
        for (String part : underscored.split(Pattern.quote(UNDERSCORE))) { // NOI18N
            if (first) {
                first = false;
                sb.append(part);
            } else {
                sb.append(part.substring(0, 1).toUpperCase());
                sb.append(part.substring(1));
            }
        }
        return sb.toString();
    }

    // MyPosts -> my_posts
    private static String toUnderscore(String input) {
        StringBuilder sb = new StringBuilder(2 * input.length());
        for (int i = 0; i < input.length(); ++i) {
            char ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (i != 0) {
                    sb.append(UNDERSCORE);
                }
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
