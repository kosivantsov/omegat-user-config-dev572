/*:name = MainMenuShortcuts for Mac :description = 
 * @author:  Kos Ivantsov
 * @date:    2024-11-15
 * @version: 0.0.1
 *
 */
import java.nio.file.*
import org.omegat.util.StaticUtils

configDir = StaticUtils.getConfigDir()
os = System.getProperty("os.name")
if ( os != 'Mac OS X') {
    return
} else {
    macMenuShortcuts = configDir + File.separator + 'MainMenuShortcuts.mac.properties'
    defaultMenuShortcuts = configDir + File.separator + 'MainMenuShortcuts.properties'
    if (new File(macMenuShortcuts).exists()) {
        macMenuShortcutsFile = Paths.get(macMenuShortcuts)
        defaultMenuShortcutsFile = Paths.get(defaultMenuShortcuts)
        Files.move(macMenuShortcutsFile, defaultMenuShortcutsFile, StandardCopyOption.REPLACE_EXISTING)
    }
}
