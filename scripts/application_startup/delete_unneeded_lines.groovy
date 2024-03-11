/* :name = Delete Unneeded Lines :description =
 *
 * @author:  Manuel Souto Pico
 * @date:    2024-03-11
 * @version: 0.0.1
 *
 */

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

return