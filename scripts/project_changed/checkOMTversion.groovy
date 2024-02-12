/* :name =   Check OmegaT version :description=
 * 
 * @author:  Kos Ivantsov
 * @date:    2024-02-11
 * @version: 0.1
 */

import java.awt.Desktop
import org.omegat.util.OStrings

import static javax.swing.JOptionPane.*
import static org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE.*
import static org.omegat.gui.main.ProjectUICommands.*
import static org.omegat.util.Platform.*


if (eventType == LOAD) {
    reqVersion = "5.7.2"
    reqRevision = "a978d82ee"
    title = "Check OmegaT version"
    openURL = false
    winURL="https://cat.capstan.be/OmegaT/exe/OmegaT_5.7.2_Windows_64_Signed.exe"
    macURL="https://cat.capstan.be/OmegaT/exe/OmegaT_5.7.2_Mac.zip"
    closeProject = false
    
    props = project.projectProperties
    projName = props ? props.projectName : null
    
    if (!props || !projName.startsWith("pisa_2025")) {
        msg = "No project opened or not a PISA project."
        console.println("== ${title} ==")
        console.println(msg)
        //showMessageDialog(null, msg, title, INFORMATION_MESSAGE)
        return
    }
    
    if (OStrings.VERSION != reqVersion || OStrings.REVISION != reqRevision) {
        msg="OmegaT 5.7.2 built by cApStAn is required for PISA projects."
        console.println("== ${title} ==")
        console.println(msg)
        showMessageDialog null, msg, title, INFORMATION_MESSAGE
        openURL = true
        closeProject = true
    } else {
        console.println("== ${title} ==")
        console.println("OmegaT version ${OStrings.VERSION} (${OStrings.REVISION})")
    }
    
    if (closeProject) {
        org.omegat.util.gui.UIThreadsUtil.executeInSwingThread {
            projectClose()
        }
    }
    
    if (openURL) {
        os = System.getProperty("os.name").toLowerCase()
        switch (os) {
            case ~/(?i).*win.*/:
                url = winURL
                break
            case ~/(?i).*mac.*/:
                url = macURL
                break
            default:
                openURL = false
        }
    }
    if (openURL) {
        title = "Download OmegaT"
        msg = "Do you want to download the installation file?"
        userChoice = showConfirmDialog(null, msg, title, OK_CANCEL_OPTION)
        if (userChoice == OK_OPTION) {
            console.println("Opening the download link...")
            Desktop.getDesktop().browse(new URI(url))
        } else {
            return
        }
    }
}
