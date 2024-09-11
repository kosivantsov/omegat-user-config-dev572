/* :name = Import Credentials :description=
 *
 * @author      Manuel Souto Pico, Kos Ivantsov
 * @date        2024-06-20
 * @update      2024-08-29 [overwrite lines if the imported file contains creds for repos already existing in 'repositories.properties']
 * @version     0.3
 */

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import javax.swing.JFileChooser
import org.omegat.util.StaticUtils
import static javax.swing.JOptionPane.*

// define constants
configDir = StaticUtils.getConfigDir()
credsFile = new File(configDir + File.separator + "repositories.properties")

// titles and info in console
scriptName = "Import Credentials"
scriptConsoleTag = "${scriptName}\n${"="*scriptName.size()}"

// function to check for binary files
def isBinaryFile(File file) {
    buffer = new byte[1024]
    int bytesRead = file.withInputStream { it.read(buffer) }

    // check if buffer contains non-printable characters
    for (int i = 0; i < bytesRead; i++) {
        byte b = buffer[i]
        if (b < 0x09 || (b > 0x0D && b < 0x20) || b == 0x7F) {
            return true
        }
    }
    return false
}

// function to check if file contains "!username=" and "!password="
def containsRequiredText(File file) {
    text = file.text
    return text.contains("!username=") && text.contains("!password=")
}

// function to strip username and password from lines
// it will be used when comparing lines in the existing and the imported files
def normalizeLine(line) {
    line = line
        .replaceAll(/!password=.*/, /!password=/)
        .replaceAll(/!username=.*/, /!username=/)
    return line.trim()
}

// select a creds file to import
console.println("${scriptConsoleTag}")
fileChooser = new JFileChooser()
fileChooser.dialogTitle = scriptName
fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
boolean validFileSelected = false

// check if the user selected a supported file or closed the dialog
while (!validFileSelected) {
    int result = fileChooser.showOpenDialog()
    if (result == JFileChooser.APPROVE_OPTION) {
        // get the selected file
        selectedFile = fileChooser.getSelectedFile()
        console.println("Selected file: ${selectedFile.getAbsolutePath()}")
        // check the file extension
        if (selectedFile.name.endsWith(".done")) {
            // show an error dialog if the file has ".done" extension
            message = "The selected file has already been processed.\nSelect another file."
            showMessageDialog(null, message, "Invalid File", ERROR_MESSAGE)
            console.println(message)
        } else if (isBinaryFile(selectedFile)) {
            // show an error dialog if the file is a binary
            message = "The selected file is a binary file and cannot be used.\nSelect another file."
            showMessageDialog(null, message, "Invalid File", ERROR_MESSAGE)
            console.println(message)
        } else if (!containsRequiredText(selectedFile)) {
            // show an error dialog if the file does not contain '!username=' and '!password='
            message = "The selected file does not contain credentials.\nSelect another file."
            showMessageDialog(null, message, "Invalid File", ERROR_MESSAGE)
            console.println(message)
        } else {
            // valid file is selected
            console.println("The selected file is being imported.")
            validFileSelected = true
        }
    // dialog closed
    } else if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION) {
        console.println("No file selected")
        return
    }
}

// create timestamps
date = new Date()
bakFormat = new SimpleDateFormat("yyyyMMdd.HHmmss")
bakFormattedDate = bakFormat.format(date)
headerFormat = new SimpleDateFormat("'#'EEE MMM dd HH:mm:ss z yyyy")
headerFormat.setTimeZone(TimeZone.getTimeZone("CET"))
formattedDate = headerFormat.format(date)

// create or backup creds file
if (credsFile.exists()) {
	// make backup
	backupFilePath = credsFile.getAbsolutePath() + "." + bakFormattedDate + ".bak"
	backupFile = new File(backupFilePath)
	backupFile.text = credsFile.text
	console.println("Backing up the creds file as ${backupFilePath}.")
} else {
	console.println("Creating the creds file in OmegaT config dir.")
	credsFile.createNewFile() // empty
	credsFile.text = formattedDate + "\n"
	//credsFile.write(formattedDate)
}

// collect lines from the credentials file and the selected file into arrays
credsFileLines = credsFile.text.tokenize("\n")
selectedFileLines = selectedFile.text.tokenize("\n")

// remove comments in the selected file
selectedFileLines.removeIf { line ->
    line.trim().startsWith("#")
}

// collect URLs in the selected file; lines with these URLs will be deleted from the credentials lines before merging
linesToRemove = selectedFileLines.collect { normalizeLine(it) }

// create a new array where the lines with the collected URLs are deleted
credsLinesToKeep = []
credsFileLines.each { line ->
    normalizedLine = normalizeLine(line)
    if (!linesToRemove.contains(normalizedLine)) {
        credsLinesToKeep.add(line)
    }
}

// combine contents of OmegaT creds file and the selected file, dedupe and write
mergedCreds = (credsLinesToKeep + selectedFileLines).unique()
credsContents = new StringWriter()
credsContents << mergedCreds.join("\n")
credsFile.text = credsContents.toString()

// mark selected file as done
doneFilePath = selectedFile.getAbsolutePath() + ".done"
doneFile = new File(doneFilePath)
selectedFile.renameTo(doneFile)
console.println("Credentials imported from $selectedFile")

return
