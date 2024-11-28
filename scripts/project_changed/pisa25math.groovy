/**
 * Usage : Put this script in <ScriptsDir>/project_changed folder. Create a folder if it doesn't exists.
 *
 * @authors     Manuel Souto Pico (based on a wonderful script written by Yu Tang)
 * @version     0.1.0
 * @date        2023.08.31
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
            [find: /𝐴/, replacement: /<i>A<\/i>/],
            [find: /𝐵/, replacement: /<i>B<\/i>/],
            [find: /𝐶/, replacement: /<i>C<\/i>/],
            [find: /𝐷/, replacement: /<i>D<\/i>/],
            [find: /𝐸/, replacement: /<i>E<\/i>/],
            [find: /𝐹/, replacement: /<i>F<\/i>/],
            [find: /𝐺/, replacement: /<i>G<\/i>/],
            [find: /𝐻/, replacement: /<i>H<\/i>/],
            [find: /𝐼/, replacement: /<i>I<\/i>/],
            [find: /𝐽/, replacement: /<i>J<\/i>/],
            [find: /𝐾/, replacement: /<i>K<\/i>/],
            [find: /𝐿/, replacement: /<i>L<\/i>/],
            [find: /𝑀/, replacement: /<i>M<\/i>/],
            [find: /𝑁/, replacement: /<i>N<\/i>/],
            [find: /𝑂/, replacement: /<i>O<\/i>/],
            [find: /𝑃/, replacement: /<i>P<\/i>/],
            [find: /𝑄/, replacement: /<i>Q<\/i>/],
            [find: /𝑅/, replacement: /<i>R<\/i>/],
            [find: /𝑆/, replacement: /<i>S<\/i>/],
            [find: /𝑇/, replacement: /<i>T<\/i>/],
            [find: /𝑈/, replacement: /<i>U<\/i>/],
            [find: /𝑉/, replacement: /<i>V<\/i>/],
            [find: /𝑊/, replacement: /<i>W<\/i>/],
            [find: /𝑋/, replacement: /<i>X<\/i>/],
            [find: /𝑌/, replacement: /<i>Y<\/i>/],
            [find: /𝑍/, replacement: /<i>Z<\/i>/],            
            [find: /𝑎/, replacement: /<i>a<\/i>/],
            [find: /𝑏/, replacement: /<i>b<\/i>/],
            [find: /𝑐/, replacement: /<i>c<\/i>/],
            [find: /𝑑/, replacement: /<i>d<\/i>/],
            [find: /𝑒/, replacement: /<i>e<\/i>/],
            [find: /𝑓/, replacement: /<i>f<\/i>/],
            [find: /𝑔/, replacement: /<i>g<\/i>/],
            [find: /[ℎ𝘩]/, replacement: /<i>h<\/i>/],
            [find: /𝑖/, replacement: /<i>i<\/i>/],
            [find: /𝑗/, replacement: /<i>j<\/i>/],
            [find: /𝑘/, replacement: /<i>k<\/i>/],
            [find: /𝑙/, replacement: /<i>l<\/i>/],
            [find: /𝑚/, replacement: /<i>m<\/i>/],
            [find: /𝑛/, replacement: /<i>n<\/i>/],
            [find: /𝑜/, replacement: /<i>o<\/i>/],
            [find: /𝑝/, replacement: /<i>p<\/i>/],
            [find: /𝑞/, replacement: /<i>q<\/i>/],
            [find: /𝑟/, replacement: /<i>r<\/i>/],
            [find: /𝑠/, replacement: /<i>s<\/i>/],
            [find: /𝑠̌/, replacement: /<i>š<\/i>/],
            [find: /𝑡/, replacement: /<i>t<\/i>/],
            [find: /𝑢/, replacement: /<i>u<\/i>/],
            [find: /𝑣/, replacement: /<i>v<\/i>/],
            [find: /𝑤/, replacement: /<i>w<\/i>/],
            [find: /𝑥/, replacement: /<i>x<\/i>/],
            [find: /𝑦/, replacement: /<i>y<\/i>/],
            [find: /𝑦/, replacement: /<i>y<\/i>/],
            [find: /𝑧/, replacement: /<i>z<\/i>/],
            // [find: /([=×]) π (×)/, replacement: /$1 <m:math xmlns:m="http:\/\/www.w3.org\/1998\/Math\/MathML"><m:semantics><m:mstyle displaystyle="true" scriptlevel="0"><m:mrow class="MJX-TeXAtom-ORD"><m:mi>π<\/m:mi><\/m:mrow><\/m:mstyle><m:annotation encoding="latex">\pi<\/m:annotation><\/m:semantics><\/m:math> $2/]
            // [find: / π/, replacement: / <m:math xmlns:m="http:\/\/www.w3.org\/1998\/Math\/MathML"><m:semantics><m:mstyle displaystyle="true" scriptlevel="0"><m:mrow class="MJX-TeXAtom-ORD"><m:mi>π<\/m:mi><\/m:mrow><\/m:mstyle><m:annotation encoding="latex">\pi<\/m:annotation><\/m:semantics><\/m:math>/]
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
    nameFilter : ~/.*PISA_2025FT_.+\.xml$/
    // see https://regex101.com/r/Eo1HiI/1 for piaac
    // see https://regex101.com/r/Eo1HiI/2 for pisa+piaac
]

// replacer as closure
def replacer = {file ->
    console.println("Math checked in file: ${file}")
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
    console.println "$modifiedFiles file(s) modified."
}

