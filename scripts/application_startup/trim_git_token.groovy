/* :name = Trim Git Token :description =
 *
 * @author:  Manuel Souto Pico
 * @date:    2024-03-11
 * @version: 0.0.1
 *
 */

import org.omegat.util.StaticUtils

config_dir = StaticUtils.getConfigDir()
repo_props_file = new File(config_dir + File.separator + "repositories.properties")

if (repo_props_file.exists()) {
	def creds_list = repo_props_file.text.readLines()
	repo_props_file.write(creds_list.collect { it.contains("\\!password=") && it.endsWith('g') ? it[0..-2] + '\\=' : it }.join('\n'), "UTF-8")
}
return