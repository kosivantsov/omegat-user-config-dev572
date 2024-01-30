/* :name=Prune batch TMs :description=Remove from TMX file entrie not found in a certain folder in the project
 *
 * @author	Manuel Souto Pico, Thomas Cordonnier
 * @version	1.2.0
*/

import org.omegat.util.TMXWriter2
import java.util.regex.Pattern
import static org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE.*

if (eventType != COMPILE) {
	return
}

def cleanUpText(text) {
	/* Strips tags and normalizes spacing */
	// def tags = ~"&lt;(?:/?g[0-9]|[bex][0-9]/)&gt;"
	def tags = ~"<(?:/?g[0-9]|[bex][0-9]/)>"
	def nbsp = ~"[\u00A0]"
	def zwnj = ~"[\u200C]"
	return text.replaceAll(tags, "").replaceAll(nbsp, " ").replaceAll(zwnj, "")
}

props = project.projectProperties

/*
files = project.projectFiles

// an alternative way to collect entries per batch or file
sourceSegments = []
files.each { file -> 

	// get parentFolder of file (=batch)
	// use SrcText as key and parentFolder as value in sourceSegments (map)

	def sourceSegmentsInFiles = file.entries.collect {
		it.getSrcText()
	}
	sourceSegments.addAll(sourceSegmentsInFiles)
}
*/


// collect batch and source text for all segments in all released batches
console.println(":: Collecting entries in project, per batch")

entriesPerBatch = [:]
project.allEntries.each { ste ->

	// def source = ste.getProperties().toString()
	def sourceText = ste.getSrcText()
	def batch = ste.key.file.split("/")[0]

	if (!entriesPerBatch[batch]) { entriesPerBatch[batch] = [] }

	entriesPerBatch[batch].add(sourceText)
}

def batches = entriesPerBatch.keySet() as List

console.println(":: Collecting source texts in every batch TM")
// collect all source texts in every batch TM
project.transMemories.each { filepath, tmx -> 

	// tmx.entries is a list (more precisely: class java.util.Collections$UnmodifiableRandomAccessList)

	if (filepath.endsWith(".tmx") && (filepath.contains("tm/auto/prev") || filepath.contains("tm/auto/next"))) {
		
		tmxFileName = tmx.getName().toString() // or
		// tmxFileName = new File(filepath).name
		tmxBaseName = tmxFileName.replace(".tmx", "")

		console.println(":: Processing TM ${tmxBaseName}.tmx only if it's in batches: ${batches}")
		if (!batches.contains(tmxBaseName)) return;
				
		console.println(":: Pruning entries found in ${tmxBaseName}...")
		def prunedEntries = tmx.entries.findAll{ 
			if (entriesPerBatch[tmxBaseName]) {
				entriesPerBatch[tmxBaseName].unique().collect{cleanUpText(it)}.contains(cleanUpText(it.source))
				// entriesPerBatch[tmxBaseName].contains(it.source)
			}
		}

		console.println(":: Write pruned entries back to ${tmxFileName}...")
		if (prunedEntries) {
			
			def writer = new TMXWriter2(new java.io.File(filepath + ".tmp"),
				props.sourceLanguage, props.targetLanguage,
				props.isSentenceSegmentingEnabled(), false, false)
			
			prunedEntries.each { entry ->
				def propValues = new java.util.ArrayList<String>()
				for (org.omegat.util.TMXProp p: entry.otherProperties) {
					if (p.getType().contains("Language")) continue;
					propValues.add(p.getType()); propValues.add(p.getValue());
				}

				// method with lot of parameters is the only one common between OmegaT 5.7 and later versions
				writer.writeEntry(entry.source, entry.translation, entry.note, entry.creator, entry.creationDate,
					entry.changer, entry.changeDate, propValues)
			}
			writer.close()
			
			def destFinal = new File(filepath)
			def destTmp = new File(filepath).path + ".tmp"
			destFinal.delete()
			new File(destTmp).renameTo(destFinal)
		}
	}
}

return