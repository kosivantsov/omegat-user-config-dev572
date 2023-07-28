/* :name = Custom User Configuration DEV2 (for 6.1.0) :description =Customize OmegaT including optimized configuration, scripts and plugins
 *
 *  @version: 0.5.8
 *  @authors: Manuel Souto Pico, Briac Pilpré, Gergely Zayzon, Kos Ivantsov
 */
/* groovylint-disable CompileStatic, DuplicateNumberLiteral, DuplicateStringLiteral, ExplicitCallToMinusMethod, FactoryMethodName, ImplicitClosureParameter, JavaIoPackageAccess, LineLength, MethodParameterTypeRequired, MethodReturnTypeRequired, NoDef, ParameterName, UnnecessaryGString, UnnecessaryGetter, VariableName, VariableTypeRequired */

/*
 * Preconditions (readme)
 * =============
 * Repository that contains the contents of the us1er configuration folder (e.g. ~/.omegat)
 * OmegaT version: ^6.1.0

*/

/*
 * @changes:
 * @0.5.2:
 * - preferences saved on the fly, no need to kill OmegaT
 * - update in OmegaT [RFE#1159]: no need to kill OmegaT to remove older jar files
 * @0.5.3:
 * - update scripts_dir in preferences with value of path to user config dir
 * @0.5.4:
 * - create parent directory of new custom files
 * ... 
 * @0.5.7:
 * - send file rather than filename to the update_uilayout() function
*/

// groovy.xml.DomBuilder (instead of slurper)

// installDir = new File(System.getProperty("java.class.path")).getAbsoluteFile().getParent()
// console.println("installDir: " + installDir)

// @todo: user-defined parameter // @todo: input dialog
repo_url = "https://github.com/capstanlqc/omegat-user-config-dev572.git"
hash_filename = "remote.md5"

if (!repo_url) {
    console.println("No URL for the remote location defined.")
    return
}

console.println(repo_url)

 /* Changes:
  *
  *  0.1.0: added hash comparison for all config files
  *  0.2.0: remote and local plugins are compared based on filename
  *  0.3.0: remote and local scripts and config files based on hash
  *  0.4.0: domain entered by user or read from properties
  *  0.5.0: copy scripts from install_dir/scripts
  *  0.6.0: edit
  *  0.6.0: delete plugins from install_dir/scripts <=== ¿¿¿¿?????
  *  0.6.0: add prompt to user to delete old plugins manually (for Windows)
  *  0.7.0: get %APPDATA% for execution by OmegaT.
  *  0.8.0: prompt the user to restart OmegaT to use the latest files
  *  0.9.0: checks the user ID and installs additional stuff if matches pattern (e.g. startswith("VER_"))
  */

// first check: is the project being opened?
// import org.omegat.core.events.IProjectEventListener.PROJECT_CHANGE_TYPE
title = "Customization"

import groovy.xml.XmlParser
import java.security.MessageDigest
import org.apache.commons.io.FilenameUtils
import org.omegat.util.Preferences
import org.omegat.util.StaticUtils

import static groovy.io.FileType.FILES

// the script starts here if a project is open
console.println("=" * 40 + "\n" + " " * 5 + title + "\n" + "=" * 40)

// def tgt_code =             project.projectProperties.targetLanguage
// def src_code =             project.projectProperties.sourceLanguage
// def tgt_lang_subtag =     project.projectProperties.targetLanguage.languageCode
// def src_lang_subtag =     project.projectProperties.sourceLanguage.languageCode
// def tgt_region_subtag = project.projectProperties.targetLanguage.countryCode
// def src_region_subtag = project.projectProperties.sourceLanguage.countryCode

def get_local_hash_list(location, remote_file_list) {
    // assert location == config_dir
    def local_config_dir = new File(location)
    def local_file_hash_map = [:]

    local_config_dir.traverse(type: FILES, maxDepth: 2) { it ->
        // @todo: should this be done asynchronously perhaps?

        def win_rel_path = local_config_dir.toPath().relativize(it.toPath()).toFile()
        def unix_rel_path = FilenameUtils.separatorsToUnix(win_rel_path.toString())

        // String asset_name = abs_path.getFileName()
        // call getFileName() and get FileName as a string

        if (remote_file_list.contains(unix_rel_path)) {
            message.add(unix_rel_path + ": found remotely")




            // https://128bit.io/2011/02/17/md5-hashing-in-python-ruby-and-groovy/
            def asset_hash = new BigInteger(1, digest.digest(it.getBytes())).toString(16).padLeft(32, "0")
            console.println(asset_hash + " <= " + unix_rel_path)
            local_file_hash_map.put(unix_rel_path, asset_hash)
        }
    }
    // String newString = str.replace("\\","/");
    return local_file_hash_map
}

List<String> fetch_hash_list(URL hash_list_url) {
    try {
        // def local_path = new File(config_dir.toString() + File.separator + "asset_hashlist.txt")
        return hash_list_url.openConnection().inputStream.readLines()
    }  catch (IOException e) {
        // last_modif will stay an empty array
        console.println("!! Unable to download hash list: " + e.message) // @debug
        message.add("!! Unable to download hash list: " + e.message)
        // stop script if list of hashes not available?? or just download everything found?
    }
    return null
}

void download_asset(URL url) {

    // @ŧodo: check if the file exists online, if it does not, return
    console.println("-----------------------") // @debug
    console.println("download_asset: ${url}")

    def remote_file_name = FilenameUtils.getName(url.getPath()) // -> file.xml
    console.println("remote_file_name: " + remote_file_name)

    def file_location_rel_path_str = url.toString().takeAfter('/master/').toString()
    console.println("file_location_rel_path_str: " + file_location_rel_path_str)

    def location = config_dir + file_location_rel_path_str
    def local_path_to_location = (os_is_windows ? FilenameUtils.separatorsToWindows(location) : location)

    console.println("local_path_to_location: " + local_path_to_location)

    message.add(">> DOWNLOAD " + remote_file_name + " to " + file_location_rel_path_str)
    console.println(">> DOWNLOAD " + remote_file_name + " to " + file_location_rel_path_str) // @debug

    try {
        // def url = repo_url + "files/" + remote_file_name
        def local_file = new File(local_path_to_location.toString())
        
        // create parent folder
        local_file.getParentFile().mkdirs()
        
        console.println("%% local_file: " + local_file)
        local_file.withOutputStream { output_stream  ->
            def conn = url.openConnection()
            output_stream << conn.inputStream
        }
    } catch (IOException e) {
        message.add("!! Unable to download file: " + e.message)
        console.println("!! Unable to download file: " + e.message)
    }
}

void delete_old_plugins(new_jar_relpath, local_plugins_dpath) {

    // check org.apache.commons.io.FileUtils.forceDeleteOnExit
    // https://github.com/omegat-org/omegat/blob/47c9177ac2a653746f2aacc3aa48745c6182d062/src/org/omegat/util/PluginInstaller.java#L138

    // This function must delete other versions of the new plugin
    console.println("hello1")

    console.println(new_jar_relpath)
    console.println(local_plugins_dpath)

    // URL url = new URL(remote_file_urlstr)
    new_jar_name = FilenameUtils.getName(new_jar_relpath)
    console.println(new_jar_name)
    // caveat: the following is based on the assumption that jar files always have the same structure: cAmElNaMe-1.12.123-2.23.234.jar
    jar_files_pattern = new_jar_name.minus(~/-\d+.*\.jar$/) + ".*\\.jar" // or + ".*[.]jar"

    console.println("\nI will delete files matched by pattern: " + jar_files_pattern)
    // def files_to_delete = new FileNameFinder().getFileNames(local_plugins_dpath.toString(), jar_files_pattern /* includes */, new_jar_name /* excludes */)
    def files_to_delete = new FileNameByRegexFinder().getFileNames(local_plugins_dpath.toString(), jar_files_pattern /* includes */, new_jar_name /* excludes */)

    // files_to_delete.each { console.println("- file to delete: ${it}") } // @debug
    console.println("")

    console.println("hello2")
    files_to_delete.each { it -> // it is a path
        def f = new File(it)
        console.println("- I will delete file ${f}")
        // file.delete() // on Windows this might not work if OmegaT is running> @todo: kill omegat first

        if (os_is_windows) {
            // @todo: do some ugly shitty black magic to byass the OS's lock and delete the file on Windows
            // tip: kill omegat first, see kos' script: https://github.com/capstanlqc/omegat-customization/blob/89a62fc25ba8f359b9538b992f7de1c12fbc2332/scripts/updateConfigBundle.groovy#L304

            // Hopefully, with RFE#1159 in, there's no need to delete obsolete jar as they
            // won't be loaded by the application.
            console.println('Windows has issues with deleting files, sorry!')
        } else {
            console.println("Congratulations on your OS choice! :P")
            // def file = new File(it)
            boolean fileSuccessfullyDeleted = f.delete()
            if (fileSuccessfullyDeleted) {
                console.println("Success deleting file ${f}!")
                message.add("--- DELETE (at least try) $f")
            }
        }
    }
}

void fetch_plugins_by_name(Map local_file_hash_map, remote_plugins) {
    /*
        While it's possible to know whether there's a new version of the rest of config files by
        comparing the hash of the local and remote versions, in the case of plugins (jar files)
        that's not possible. For some reason, it seems the jar file's hash value is not inmutable
        and might vary. Therefore the jar's hash is not reliable for assessing whether there's a
        new version remotely and the download is necessary.

        On the other hand, this relies on the assumption that every new version of a plugin is
        named accordingly (new version number according to https://semver.org/)
    */

    // create plugins folder if it doesn't exist
    console.println("local_plugins_dpath: ${local_plugins_dpath}")

    if (!local_plugins_dpath.exists()) {
        local_plugins_dpath.mkdir()
    }

    console.println("=======================")
    // fetch plugins' jar files (comparing remote to local filenames)
    remote_plugins.each { plugin ->
        console.println("remote plugin: " + plugin)
        def downloaded = local_file_hash_map.containsKey(plugin)
        if (downloaded) {
            // strange output if this line is not commented...
            console.println("== Config file " + plugin + " was already up to date") // @debug
            message.add("== Config file " + plugin + " was already up to date")
            return
        }

        console.println("Will delete older versions of plugin ${plugin}")
        delete_old_plugins(plugin, local_plugins_dpath)
        // make path to github raw file
        console.println("I will now download plugin ${plugin} from ${remote_config_dir}")

        // function to create github_raw_file_url (remote_config_dir, file_relpath)
        URL github_raw_file_url = get_remote_file_url(repo_url, plugin)

        console.println("github_raw_file_url: " + github_raw_file_url)

        download_asset(github_raw_file_url)
        console.println("++ Remote config file " + plugin + " downloaded to " + local_plugins_dpath) // @debug
        message.add("++ Remote config file " + plugin + " downloaded to " + local_plugins_dpath)

        console.println("------------------------")
    }
}

/** Read the omegat.prefs and return a map with the preferences. */
Map<String, String> get_omegat_prefs(File omegatPrefs) {
    return new XmlParser().parse(omegatPrefs).preference[0].children().collectEntries { node ->
        [(node.name()): (node.text())]
    }
}

void fetch_files_by_hash(local_file_hash_map, remote_file_hash_map) {
    // get other custom files (compare remote to local hashes)
    remote_file_hash_map.each {
        remote_file_name = it.key
        remote_file_hash = it.value

        // if the remote asset is found in the assets folder
        def downloaded = local_file_hash_map.containsKey(remote_file_name)
        URL remote_file_url = get_remote_file_url(remote_config_dir, remote_file_name)

        if (downloaded) {
            def local_asset_hash = local_file_hash_map.find { it.key == remote_file_name }?.value

            if (!local_asset_hash) {
                console.println("!! Remote file ${remote_file_url} has no associated asset hash")
                return
            }

            console.println("/////////////////////////////////////////")
            console.println(">> remote_file_name: " + remote_file_name)
            console.println(">> local_asset_hash: " + local_asset_hash)
            console.println(">> remote_file_hash: " + remote_file_hash)
            if (local_asset_hash == remote_file_hash) {
                message.add("== Remote custom file ${remote_file_name} hasn't changed, the local copy is up to date.")
                // console.println("== Remote custom file ${remote_file_name} hasn't changed, the local copy is up to date.")
            } else {
                // ++ Remote custom file SHA1SUM has been updated, downloading now. @debug message
                message.add("++ Remote custom file " + remote_file_name + " has been updated, downloading now.")
                console.println("++ Remote custom file " + remote_file_name + " has been updated, downloading now.")
                // download
                console.println("remote_config_dir + remote_file_name: " + remote_config_dir + File.separator + remote_file_name)

                download_asset(remote_file_url)

                if (remote_file_name == "omegat.prefs") {
                    update_omegat_prefs()
                } else if (remote_file_name == "uiLayout.xml") {
                    update_ui_layout(new File(config_dir, "uiLayout.xml"))
                }
                console.println("????????????????????????????????????")
            }
        } else {
            // just download
            // check if it exists locally, if it does, then msg:
            message.add("++ Remote custom file " + remote_file_name + " is new or had not been downloaded, downloading now.")
            console.println("++ Remote custom file " + remote_file_name + " is new or had not been downloaded, downloading now.")
            // download
            download_asset(remote_file_url)
            // download_asset(remote_config_dir + File.separator + remote_file_name)
        }
    }
}

/** Update the UI Layout with the newly download layout. */
// XXX This effectively prevents users to make adjustments on the UI layout as it will always be
// reset with the remote layout at each application restart.
void update_ui_layout(File local_uilayout) {
    console.println("<<<< HANDLING UI LAYOUT >>>>>")
    local_uilayout.withInputStream(is -> {
        Core.getMainWindow().getDesktop().readXML(is)
    })
}

// Update current preferences from a file and save them.
void update_omegat_prefs() {
    console.println("<<<< HANDLING OMEGAT PREFS >>>>>")

    // parse local prefs file
    File local_prefs_path = new File(config_dir, 'omegat.prefs') // @debug
    //console.println('localPrefsPath  : ' + localPrefsPath) // @debug
    //get_omegat_prefs(localPrefsPath).each { prop -> console.println(":::   local: ${prop.key} => ${prop.value}") } // @debug

    // The local omegat.prefs file has been overwritten with the remote one, but the actual
    // preferences are not yet updated!

    Map<String, String> remote_prefs = get_omegat_prefs(local_prefs_path)
    remote_prefs.each { prop -> console.println("::: remote: ${prop.key} => ${prop.value}") } // @debug

    console.println(" I will set scripts_dir to '${local_scripts_dpath}'")
    remote_prefs.put('scripts_dir', local_scripts_dpath.getAbsolutePath())
    remote_prefs.each { prop -> console.println("::: remote: ${prop.key} => ${prop.value}") } // @debug

    // Try to guess the property type, Boolean, Integer or String
    remote_prefs.each { prop ->
        if (prop.value == "true" || prop.value == "false") {
            Preferences.setPreference(prop.key, prop.value.toBoolean())
        } else if (prop.value.isInteger()) {
            Preferences.setPreference(prop.key, prop.value.toInteger())
        } else {
            Preferences.setPreference(prop.key, prop.value)
        }
    }

    Preferences.save()
}

URL get_remote_file_url(repo_url, file) {
    // @todo: some checks to ensure the input repo_url looks good.
    // what if it starts with git?
    // what if it's for azure or aws but not github?

    // steps: (extraction rules for github repos:)
    // 1. extract organization/user and repo name
    // if starts with https://github.com
    def org  = repo_url.minus("https://github.com/").minus(".git").split('/')[0]
    def repo = repo_url.minus("https://github.com/").minus(".git").split('/')[1]

    return new URL("https://raw.githubusercontent.com/${org}/${repo}/master/${file}")
}

void make_directory(directory_path) {
    if (!directory_path.exists()) {
        directory_path.mkdirs()
    }
}

def main() {
    URL hash_list_url = get_remote_file_url(repo_url, hash_filename)
    // expected, e.g. https://raw.githubusercontent.com/capstanlqc/omegat-user-config-dev572/master/SHA1SUM

    console.println("hash_filename: " + hash_filename)
    console.println(hash_list_url)

    def hash_list = fetch_hash_list(hash_list_url) // class: java.util.ArrayList

    if (!hash_list) {
        message.add("!!! No hash list found, unable to continue.")
        return
    }

    // the hash list is expected to have this shape:
    // [
    // bfaec67f273a5e5efb7cb1c686a9ef99  changes.md,
    // 9452aceed6ba372f04660d78a18db700  custo/preparation.md,
    // 7c45b209dc57307b8aab5e551028bd5c  custo/custom_version.txt,
    // etc. ... // ]

    // move the list to a map
    def remote_file_hash_map = hash_list.collectEntries {
        def hash = it.split('\s+')[0]
        def file = it.split('\s+')[1]
        [(file): hash]
    }

    // get local assets and their hashes
    def local_file_hash_map = get_local_hash_list(config_dir, remote_file_hash_map.keySet())

    // local_file_hash_map.each { console.println("::: file: ${it.key} => hash: ${it.value}") } // @debug

    // get list of plugins
    def remote_plugins_list = (remote_file_hash_map.findAll { it.key.startsWith("plugins/") }).keySet()
    console.println("remote_plugins: " + remote_plugins_list)
    fetch_plugins_by_name(local_file_hash_map, remote_plugins_list)

    // get map of the rest of config files (config and scripts), excluding plugins
    remote_file_hash_map = remote_file_hash_map.findAll { !it.key.startsWith("plugins/") }
    fetch_files_by_hash(local_file_hash_map, remote_file_hash_map)

    // @todo:
    // omegat.prefs -> merge after killing omegat
}

// ================================= main =================================

// OS
os_is_windows = System.properties['os.name'].toLowerCase().contains('windows')

if (os_is_windows) {
    console.println("OS is Windows")
} else {
    console.println("OS is not Windows")
}

// init
message = []
assert message.empty

// constants
digest = MessageDigest.getInstance("MD5")
// timestamp = new Date().format("YYYYMMddHHmm")

// if omegat internals are available
config_dir = StaticUtils.getConfigDir()
console.println("config_dir" + config_dir)
// @q&a: could omegat internals not be avaiable?

// otherwise:
// if OmegaT is not running
omegat_appdata = System.getenv("APPDATA") + File.separator + "OmegaT"
appdata = (System.properties['os.name'].toLowerCase().contains('windows') ?  omegat_appdata : "~/.omegat")
// e.g. C:\Users\souto\AppData\Roaming\OmegaT
// @todo: get userConfigDir by from OmegaT internals

console.println("omegat_appdata:" + omegat_appdata)
console.println("appdata:" + appdata)

local_plugins_dpath = new File(config_dir.toString() + File.separator + "plugins")
local_scripts_dpath = new File(config_dir.toString() + File.separator + "scripts")

make_directory(local_plugins_dpath)
make_directory(local_scripts_dpath)
new File(local_scripts_dpath.toString() + File.separator + "application_shutdown").mkdir()
new File(local_scripts_dpath.toString() + File.separator + "application_startup").mkdir()
new File(local_scripts_dpath.toString() + File.separator + "project_changed").mkdir()

console.println("local_plugins_dpath:" + local_plugins_dpath)
console.println("local_scripts_dpath:" + local_scripts_dpath)

def url_to_path = {
    it.endsWith('.git') ? it.minus('.git') : it
}

// def list_url =             tb_domain + "list_contents.php"
// remote_config_dir = repo_url.takeBefore('.git').toString() // remove .git, remove trailing slash
remote_config_dir = url_to_path(repo_url)

main()

no_updates_msg = "All custom files were already up to date, nothing downloaded."
// message.each { line -> console.println(line) }
console.println(message.empty ? no_updates_msg : message.join('\n'))
console.println("Done!")

return

// additions:
// @add: input dialog to paste URL if domain is undefined

/*
 * Developed with:
 *
 * Version: OmegaT-5.8.0_0_8fb70b9d2
 * Platform: Linux 5.15.78-1-lts
 * Java: 11.0.17 amd64
 * Memory: 248MiB total / 183MiB free / 3960MiB max
 *
 * to be tested on:
 *
 * Version: OmegaT-5.7.1_0_c3206253
 * Platform: Linux 5.15.78-1-lts
 * Java: 1.8.0_312 amd64
 * Memory: 253MiB total / 179MiB free / 3520MiB max
 *
 * How to create sha1sum file:
 * 1. Clone repo
 * 2. Make updates
 * 3. Delete file SHA1SUM
 * 4. find * -type f -exec md5sum {} >> SHA1SUM \;
 * 5. git add . && git commit -m "Updated foo and bar" && git push


man md5sum:
Print or check MD5 (128-bit) checksums.
The sums are computed as described in RFC 1321.  



 */

/*
@TODO:
- kill OmegaT and delete old plugins (get from Kos' script)
- kill OmegaT to update omegat.prefs (mv omegat.prefs.xml omegat.prefs) (get from Kos' script)
- write absolute path to scripts folder inside the user config folder <-- DONE!
- copy all scripts from install_dir/scripts to config_dir/scripts (or download them)
- add dialog to insert repo URL
- add linebreak after xml declaration
- go through all dictories in repo, mkdir() locally for each
- download uiLayout.xml only the first time
- delete files in files_to_delete.txt from this script
- add dialog after execution with buttons: Quit, Restart, Cancel
- omegat.prefs: compare hash value in remote.md5 with hash value in local.md5
*/
