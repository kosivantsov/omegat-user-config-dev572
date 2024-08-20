/* :name = Delete Unneeded Lines :description =
 *
 * @author:  Manuel Souto Pico
 * @date:    2024-08-20
 * @version: 0.0.2
 *
 */

/*
 * CHANGES:
 *     0.0.2: Add removeLines function and use it to delete old passwords
 */


def removeLines(file_path, pattern) {

	if (file_path.exists()) {
		def lines = file_path.text.readLines()
		if (lines.join() =~ /${pattern}/) {
			console.println(">> File ${file_path} will be pruned")
			lines.removeAll {
				it ==~ /.*${pattern}.*/
			}
			file_path.write(lines.join('\n'), "UTF-8")
		}
	}
}


import org.omegat.util.StaticUtils

config_dir = StaticUtils.getConfigDir()
autotext_config_file = new File(config_dir + File.separator + "omegat.autotext")

if (autotext_config_file.exists()) {

	def autotext_list = autotext_config_file.text.readLines()

	if (autotext_list.join() =~ /[\x{1D400}-\x{1D433}\x{1D468}-\x{1D49B}]/) {

		console.println(">> autotext config file will be pruned")

		autotext_list.removeAll {
			// it.contains("math bold")
			// https://regex101.com/r/N6XhNP/1
			it ==~ /\\math.\t[\x{1D400}-\x{1D433}\x{1D468}-\x{1D49B}].+/
		}

		autotext_config_file.write(autotext_list.join('\n'), "UTF-8")
	}
}


// ---------

config_dir = StaticUtils.getConfigDir()
creds_fpath = new File(config_dir + File.separator + "repositories.properties")
old_creds_pattern = "(Ym00NWJwNHl1M3Q2cWN2cXdqYm4zcGhucm01bGh0YXRzNGZkenVjMm5mZGNhYjczaW5tcQ)"

removeLines(creds_fpath, old_creds_pattern)


return