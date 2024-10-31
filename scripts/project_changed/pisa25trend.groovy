/**
 * Usage : Put this script in <ScriptsDir>/project_changed folder. Create a folder if it doesn't exists.
 *
 * @authors     Manuel Souto Pico (based on a wonderful script written by Yu Tang)
 * @version     0.1.2
 * @date        2024.10.31
 */

import static org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE.*

/*
// check that this is PISA
prop = project.getProjectProperties()
proj_name = prop.projectName
// The container is the first entity in the project name (before the first underscore)
container = (proj_name =~ /^[^_]+/)[0]

/* == rather than contains? */
/*
if (container.contains("pisa")) { 
  console.println("This script runs on PISA XLIFF files, let's continue!")
} else {
  console.println("This is not PISA, let's stop here. Good bye!")
  return
}
*/

// prepare
String dir
def replacePair

def skipTraverse(eventType) {
    if (!eventType.metaClass.hasProperty(eventType, 'skipTraverse')) {
        eventType.metaClass.skipTraverse = false
    }
    eventType.skipTraverse
}

switch (eventType) {
    case LOAD:
        // Skip traverse
        if (skipTraverse(LOAD)) {
            LOAD.skipTraverse = false // reset the flag
            return
        }

        dir = project.projectProperties.sourceRoot
        replacePair = []
        break
    case COMPILE:
        dir = project.projectProperties.targetRoot
        replacePair = [
            [find: /&lt;([^&\n<>]+)>/, replacement: /&lt;$1&gt;/],  // escapes closing >
            [find: /([\r])[\r]+/, replacement: /$1/],               // remove multiple CR
            // [find: /&lt;br([^&]+)&gt;/, replacement: /<br$1>/],  // matched things like &lt;broad science&gt;, hence removed
            [find: /&lt;br(\s?\/)&gt;/,   replacement: /<br$1>/],   // unescape break tags
            [find: /&lt;br(\s+class="[^"]+"\/)&gt;/, replacement: /<br$1>/],    // unescape break tags with a class
            [find: /<(span|div|p|li|a|strong|em|td|textarea|th|dummy)([^>]*)\/>/, replacement: /<$1$2><\/$1>/], // split self-closing tags
            [find: /<(sup|sub)\/>/, replacement: /​/],              // replace empty sub/sup self-closing tags
            [find: /<(sup|sub)>\s*<\/\1>/, replacement: /​/],       // replace empty sub/sup paired tags
            [find: / /, replacement: /​/]                   // replace line separator with zero-width space
        ]
        break
    default:
        return null // No output
}

String ENCODING = 'UTF-8'
File rootDir = new File(dir)
int modifiedFiles = 0

// options as map
def options = [
    type       : groovy.io.FileType.FILES,
    // nameFilter : ~/.*\.xlf/
    // nameFilter : ~/PISA_[a-z]{3}-[A-Z]{3}.+?(MS|FT)(20)?(2[12]|1[58]).*?\.xlf/
    nameFilter : ~/.*PISA_2025FT_.*\.(xml|html)$/
    // see https://regex101.com/r/Eo1HiI/1 for piaac
    // see https://regex101.com/r/Eo1HiI/2 for pisa+piaac
]

// replacer as closure
def replacer = {file ->
    console.println("Trend checked in file: ${file}")
    String text = file.getText ENCODING
    // String replaced = text.replaceAll('\r\r+', '\r') // test well!
    String replaced = text
    replacePair.each {replaced = replaced.replaceAll it.find, it.replacement}
    if (text != replaced) {
        file.setText replaced, ENCODING
        console.println "modified: $file"
        modifiedFiles++
    }
}


def reloadProjectOnetime = {
    LOAD.skipTraverse = true    // avoid potentially infinity reloading loop
    javax.swing.SwingUtilities.invokeLater({
        org.omegat.gui.main.ProjectUICommands.projectReload()
    } as Runnable)
}


// do replace
rootDir.traverse options, replacer

if (modifiedFiles > 0) {
}

