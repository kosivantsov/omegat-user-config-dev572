# OmegaT (v5.7.3) customization

## Update 127_cs0 (2024-11-28)

* Script: Updated `md2html.groovy` script to add underlining using HTML rather than CSS
* Script: Updated `pisa25cdata.groovy` script to handle math letters in any unit, not only HELP

## Update 126_cs0 (2024-11-15)

* Config: Added Mac-specfic file to have custom shortcuts
* Script: Added script to rename Mac-specific shortcuts file when running on macOS
* Script: Updated `checkOMTversion.groovy` to allow mac-specific update hash (OmegaT-5.7.3_0_e363cb094) -- released by accident

## Update 125_cs0 (2024-11-08)

* Config: Fixed word joiner and zero-width joiner shortcuts and added zero-width non-breaking space
* Config: Added `Ctrl+Alt+A` as shortcut to create alternative translations
* Config: Removed `∠`from custom tags
* Config: Flash notes and stop flashing comments (until comments and segments props can be separated)

## Update 124_cs0 (2024-11-01)

* Script: Updated `updateConfigBundle.groovy`'s name (to remove reference to 5.7.2)
* Script: Updated `team-project-sync.groovy` to remove all files in the target folder

## Update 123_cs0 (2024-10-31)

* Script: Removed `tmx-key-update.groovy` (unneeded and added by mistake)

## Update 122_cs0 (2024-10-31)

* Script: Updated `pisa25trend.groovy` to fix one typo (ℎ -> d)
* Script: Updated `pisa25trend.groovy` to prevent self-closing tag 'dummy'
* Script: Updated `checkOMTversion.groovy` to the next omegat version (OmegaT-5.7.3_0_57b1bb571) -- removed testing interim versions
* Script: New `pisa25math.groovy` to deal with Math letters in the HELP unit exclusively
* Script: Updated `pisa25trend.groovy` to remove Math letters substitutions
* Script: Deleted `pisaconv.groovy` to reduce load and time during compilation
* Script: Removed `reloadProjectOnetime` in scripts that run on compile

## Update 121_cs0 (2024-10-12)

* Script: Updated `checkOMTversion.groovy` to the next omegat version (OmegaT-5.7.3_0_3a8399a66)
* Script: Updated `set_latest_translations.groovy` to handle edits in alternative translations from tm/auto

## Update 120_cs0 (2024-10-03)

* Config: Updated flagged patterns with disallowed text in PISA 2025 MS

## Update 119_cs0 (2024-09-30)

* Scripts: Updated `pisa25trend.groovy` to replace Math upper case letters with formatted ASCII equivalent (for TAO)

## Update 118_cs0 (2024-09-24)

* Config: Update tag-only pattern to hide also segments contianing only spacing and dash
* Scripts: Added new scripts to go to next segment with low or high confidence (MTPE)
* Scritps: Added MTPE scripts shortcuts set (plus renamed the default one as verification)
* Scripts: Added cli version of some scripts (without gui dialogs)

## Update 117_cs0 (2024-09-19)

* Script: Updated `pisa25trend.groovy` to include all mathematical letters and 𝑠̌.
* Config: Updated autotext shortcuts with `\mathscaron` and `\mathš` to insert 𝑠̌ (and a few other shortcuts).

## Update 116_cs0 (2024-09-11)

* Script: Updated `utils_import_creds.groovy` to overwrite lines if the imported file contains creds for repos already existing in 'repositories.properties'

## Update 115_cs0 (2024-08-20)

* Script: Add `removeLines` function in `application_startup/delete_unneeded_lines.groovy` so as to prune any lines in any files

## Update 114_cs0 (2024-08-07)

* Script: Temporarily accepted one more OmegaT testing version.

## Update 113_c00 (2024-07-31)

* Config: Defined color for locked segments (different setting from previous enforced color, but same value)

## Update 112_cs0 (2024-07-29)

* Script: Updated underline class in `md2html.groovy` based on ACER's feedback in ticket PISATAO-296

## Update 111_cs0 (2024-07-25)

* Config: Option `tagValidateOnLeave` enabled to have tag issues flagged when leaving the segment
* Script: Remove script `goto_next_nonunique.groovy` (which was a copy of `goto_next_rep_inprj.groovy` using old jargon
* Script: Added `utils_import_creds.groovy` to import credentials files.
* Config: Updated script shortcuts: 
	- `Ctrl+Shift+F9` launches the import credentials dialog
	- `Ctrl+Shift+F10` goes to the next repetition in the project
	- `Ctrl+Shift+F11` goes to the next repetition for the current segment

## Update 110_cs0 (2024-07-01)

* Config: Customized (shortened) last modification info

## Update 109_cs0 (2024-06-27)

* Config: New option in preferences to enable locked segments
* Config: New option in preferences to always create target files when closing the project
* Config: New option in preferences to always commit target files (in team projects) when creating them
* Script: Updated `checkOMTversion.groovy` to accept any of several revisions (current, testing)

## Update 108_cs0 (2024-06-19)

* Script: Updated script `md2html.groovy` to produce underlining as `<span class="txt-underline">` (TAO-specific) instead of `<u>` (QTI incompatible)

## Update 107_cs0 (2024-05-10)

* Script: Added new script `md2html.groovy` to have markdown-to-html replacements in inline formatting markup in all HTM/XML files except LDW units
* Script: Removed markdown-to-html replacements from `pisa25trend.groovy` to allow this script run also on LDW units

## Update 106_cs0 (2024-04-26)

* Script: Updated/fixed `pisa25trend.groovy` to apply replacements also in HTML files (for coding guides)
* Config: Added autotext shortcut `\br` to insert HTML break tag (`<br/>`)
* Script: Updated `writeTMX4batch.groovy` to include OmegaT version/hash in the header
* Script: Updated on-demand `updateConfigBundle.groovy` to run regardless of what the local version is

in the linked update it removes the local_version_notes.txt so it's run each time the user invokes it 

## Update 105_cs0 (2024-04-25)

* Config: Raised `ext_tmx_fuzzy_match_threshold` to 60%.
* Config: Raised `penalty_foreign_matches` to 45 (to avoid source language matches, see https://sourceforge.net/p/omegat/feature-requests/1578/)
* Script: Updated `pisa25trend.groovy` to apply inline formatting in XML/HTML (see https://jsfiddle.net/msoutopico/f82rbL1u/3/)

## Update 104_cs0 (2024-04-24)

* Config: Removed `###` from flagged text patterns
* Script: Updated `pisa25trend.groovy` to remove line separators in target files (or to be precise, replace them with a zero-width space)
* Script: Updated `project_changed/checkOMTversion.groovy` to run regardless of whether a project is open or what project that is.

## Update 103_cs0 (2024-04-17)

* Config: Increased auto-save interval (from 2 to 10 min) to avoid too frequent commits in team projects
* Script: Updated `checkOMTversion.groovy` to include SEA-PLM projects.
* Config: Updated list of files to be deleted to remove `project_changed/utils_rename_project.groovy` (for now)

## Update 102_cs0 (2024-03-29)

* Script: Updated `pisa25cdata.groovy` to only add CDATA wrappers in LDW files if not found
* Script: Added `utils_rename_project.groovy` to prevent users from renaming projects

## Update 101_cs0 (2024-03-26)

* Script: Updated `pisa25trend.groovy` to restrict the scope of the replacement that unescaped break tags

## Update 100_cs0 (2024-03-22)

* Script: Updated `writeTMX4batch.groovy` to include HTML files as well as XML, and ignore project name

## Update 99_cs0 (2024-03-21)

* Config: Added autotext shortcuts for zero-width space and word joiner
* Config: Added custom color (light grey) for segment marker
* Script: Added `utils_report_auto.groovy` to report matches coming from /tm/auto and /tm/enforce
* Script: Added `utils_prune_recent.groovy` (run at startup) to clean up the list of recent projects
* Script: Added `trim_git_token.groovy` (run at startup) trim trailing spaces in aws passwords
* Script: Added `delete_unneeded_lines.groovy` (run at startup) to remove all bold and bold/static math autotext entries
* Script: Updated `pisa25trend.groovy` (experimentally) to add a replacement rule that restores `&gt;`
* Script: Updated `pisa25trend.groovy` to remove empty `<sup>`, `<sub>` tag pairs (remove = replace with \zwsp)

## Update 98_cs0 (2024-02-28)

* Script: Updated `checkOMTversion.groovy` to include YSC projects and close the project before informing the user.
* Scritp: Updated `check_rules.groovy` to work without localization properties.
* Script: Updated `pisa25trend.groovy` to fix the replacement pattern of duplicated CR in EOL

## Update 97_cs0 (2024-02-27)

* Config: Updated tag validation pattern to avoid locking `[A]`, `[B]` etc (with one letter only) but keep matching `[AB]`, `[ABC]`, etc.
* Config: Added zero-width non-joiner (U+200C) and zero-width space (U+200B) to the autotext list
* Script: Updated `pisa25trend.groovy` to replace duplicated CR in EOL
* Scritp: Updated `pisa25trend.groovy` to expand self-closing superscript and subscript tags

## Update 96_cs0 (2024-02-12)

* Script: Updated `pisa25trend.groovy` to add more self-closing tags to the rule added in update 92 (i.e. span|div|p|li|a|qh5:rt|sharedstimuli|strong|em|td|text|textarea|th).
* Script: Updated `checkOMTversion.groovy` to improve handling the creation of batch TMs when batch folders have no files.
* Script: Added `checkOMTversion.groovy` to check for the correct OmegaT version/hash in cApStAn projects (e.g. PISA, FLASH, etc.)

## Update 95_cs0 (2024-02-07)

* Script: Updated `pisa25trend.groovy` to add more self-closing tags to the rule added in update 92.

## Update 94_cs0 (2024-01-26)

* Script: Fixed typo in in rule that expands self-closing span tags in script `pisa25trend.groovy`
* Script: Added script "Prune batch TMs" `prune_tmx_content_per_batch.groovy` to optimize batch transitions in PISA 2025

## Update 93_cs0 (2024-01-23)

* Script: Added script `writeTMX4batch.groovy` to optimize batch transitions in PISA 2025

## Update 92_cs0 (2024-01-23)

* Script: Updated `pisa25trend.groovy` to replace self-closing span tags with paired tags.

## Update 91_cs0 (2024-01-09)

* Config: Expanded maximum number of recent projects to 100
* Script: Updated `pisa25trend.groovy` to replace mathematical alphanumerics 𝑦 with the italicised regular letter.

## Update 90_cs0 (2024-01-08)

* Script: Updated `pisa25trend.groovy` to unescape break elements also if they have attributes/classes
* Script: Updated `pisa25trend.groovy` to replace mathematical alphanumerics 𝑎 𝑏 𝑐 𝘩 𝑙 𝑟 𝑤 𝑥 with the italicised regular letter.

## Update 89_csp (2023-12-15)

* Config: Fixed auto-save internal, set now for 120 seconds (2 min) instead of 2 seconds
* Config: Delete `scripts/application_startup/updateConfigBundle-572.groovy` included in last customization of 5.7.1
* Script: Added /tm/auto/next as update tm folder in `set_latest_translations.groovy` (together with /tm/auto/prev) in pisa25 team projects
* Script: Show dummy file missing error only if the project is not empty (it has batches) in `set_latest_translations.groovy`

## Update 88_csp (2023-12-10)

* Config: Updated flagged text pattern to allow segment-wide forced adaptations
* Config: restored auto-save interval of 2 minutes
* Config: updated tag-only expression
* Config: added italic and bold mathematical Latin letters
 script set latest
* Config: translated tags now allowed to be in a different order
* Config: updated modification info template (more concise)
* Script: avoid dupicate auto-text shortcuts when updating config files
* Script: go to next segment with translation from /tm/auto
* Script: go to next segment with translation from /tm/enforce
* Script: remove extraneous tags
* Script: set latest translation (auto-populate based on recency)

## Update 87_csp (2023-10-26)

* Config: Updated OMT plugin configuration so as not to exclude zipped TMs when packing 
* Enabled option to prevent committing target files if there are tag issues -- fingers crossed!

## Update 86_csp (2023-10-06)

* Config: Include new config for updates from 5.7.1
* Script: Include all scripts for updates from 5.7.1
* Plugin: Include all plugins for updates from 5.7.1

## Update 85_c00 (2023-10-05)

* Config: Updated auto-save interval (from 6000 to 120)
* Config: Re-enabled autotext suggestions
* Config: Enabled property notifications for comments

## Update 84_csp (2023-07-31)

* Plugin: Updated OMT package plugin version to 1.7.1: `plugin-omt-package-1.7.1.jar`
* Plugin: Updated Okapi filters plugin (patched version to include all new features up to version 1.13 without XLIFF tag bug in Okapi 1.45): `okapiFiltersForOmegaT-1.12-1.44.0-b6bf048-jre11.jar`
* Script: Temporary customUrl set to v572 URL in both versions of `updateConfigBundle.groovy`
* Config: Temporary customUrl set to v572 URL in `customisation.properties`
* Config: Updated OMT plugin configuration to pack as offline project

## Update 83_c00 (2023-07-27)

* Config: Added custom tags for interpolated variables in YSC text files

## Update 81_c0p (2023-05-15)

* Plugin: Added `TmxCommentsProvider-1.1.jar` to enable T&A notes in the Comments pane

## Update 80_csp (2023-03-09)

* Config: Added Comments pane to the top right.
* Plugin: Updated Okapi plugin 1.12-1.44.0 from 24a23ea-jre8 to 6e59379-jre8

## Update 79_cs0 (2023-03-09)

* Script: Disable prompt in offline projects.

## Update 78_cs0 (2023-03-08)

* Config: Added all subscript and superscript digits and a few other characters used in maths
* Config: Removed modification info from top of segment
* Config: Inverted order of “” in character table
* Config: Notify user when a segment has comments
* Script: Updated `pisaconv.groovy` so that it only matches XLIFF files from ETS up to PISA 2022
* Script: Added `team-project-sync.groovy`, which removes local files not found in repo
* Script: Added `goto_next_alternative.groovy`, which goes to the next segment with an alternative translation (duh!)

## Update 77_cs0 (2022-12-19)

* Config: Added default color definition explicit to fix the black-box issue in the View menu

## Update 76_csp (2022-12-15)

* Config: Added autotext entries: superscript + and -
* Script: Added `write_project2tsv.groovy`
* Plugin: Removed `omegat-bidimarkers-0.2.0-all.jar`

## Update 75_cs0 (2022-09-25)

* Scripts: Renamed script `pseudoxlate_latn2arab.groovy` ("Arabic" added in title)
* Scripts: Updated `pisaconv.groovy` so that it only runs on PISA XLIFF files.

## Update 74_cs0 (2022-03-28)

* Scripts: Added `write_project2excel.groovy` again (which was gone for some reason).
* Scripts: Added `write_xliff.groovy` script, which exports the project as XLIFF.
* Config: Updated OMT packing/unpacking settings to prevent including Word files in the project package.
* Config: Updated match template: more compact and more informative (includes ID and filename now)
* Config: Updated autotext entries (Mathematical italic small x and some ordinal number superscripts)

## Update 73_cs0 (2021-11-29)

* Scripts: Added `goto_next_nonunique.groovy` script to go to the next repeated segment in the project.
* Config: Added `goto_next_nonunique.groovy` script to slow 10.

## Update 72_cs0 (2021-11-23)

* Scripts: Added `delete_unneeded_files.groovy` script to remove custom files that should not be there (anymore)

## Update 70_cs0 (2021-11-05)

* Scripts: Fixed bug with project not reloading after modifying XLIFF files (this reverts change in `pisaconv.groovy` made in 26_csp).

## Update 69_c0p (2021-08-18)

* Plugins: Updated OMT plugin for compatibility with OmegaT 5.x

## Update 68_c00 (2021-07-12)

* Config: Updated custom tags to protect '^inserts$'.

## Update 67_c00 (2021-04-12)

* Config: Enabled option to replace glossary matches in insert
* Config: Enabled option to notify glossary hits

## Update 66_c00 (2021-03-29)

* Config: Added filter settings to disable the standard OpenXML filter by default.

## Update 65_c00 (2021-03-23)

* Config: Updated custom pattern for tag validation (which were too agressive and locked all text in square or curly brackets)

## Update 64_c00 (2021-03-22)

* Config: Updated custom pattern for tag validation (to avoid locking the text inside the "a" tag)

## Update 63_c00 (2021-03-15)

* Config: Updated remove pattern for tag validation (to avoid matching standard tags for HTML with the remove pattern)

## Update 62_c00 (2021-02-18)

* Config: Updated remove pattern for tag validation

## Update 61_c00 (2021-02-16)

* Config: Updated custom tag and remove tag definitions: {COUNTRY} and [COUNTRY] now locked

## Update 60_cs0 (2020-01-04)

* Scripts: Added script 'Remove Redundant IDs in TMs' [remove_redundant_ids_in_tm.groovy]

## Update 59_cs0 (2020-11-30)

* Scripts: Customization script fix, v. 0.5.3 (remove folder `file.jar`)

## Update 58_csp (2020-11-30)

* Scripts: Customization script fix, v. 0.5.2
* Plugin: OMT package plugin, harmonized messages to user
* Plugin: OMT package plugin version 1.6.3, fixed bug not including log
* Plugin: Okapi filters updated to version 1.8-1.40.0-capstan
* Config: Added notification for alternative translations in Segment Properties
* Config: Added notification for repeated segments in Segment Properties

## Update 57_cs0 (2020-11-25)

* Scripts: Updated `pisaconv.groovy` to turn ETS's XLIFF files compatible with OmegaT to remove DOCTYPE
* Scripts: Fixed customization script (more of Lev's fixes), v. 0.5.1

## Update 56_cs0 (2020-11-24)

* Updated customization URL (removed dev/ from team branch)

## Update 55_cs0 (2020-09-18)

* Fixed customization script (Lev's fixes), v. 0.5.0

## Update 54_cs0 (skipped)

## Update 53_cs0 (2020-07-06)

* Script: Container assets manager reads credentials from config files

## Update 52_c0p (2020-07-03)

* OMT packages: Updated names: "Unpack/Pack project" instead of "Export/Import  OMT file" to avoid confusion with "Import source/Export target files".

## Update 51_c0p (2020-07-01)

* OMT packages: Improved logs to the OMT plugin project for every packing action: including user ID, plugin version, project and package name, excluded content and packed content.

## Update 50_cs0 (2020-06-24)

* Config: Credentials for authenticated access to cApps added to config files
* Script: Container assets manager includes URL for cApps glossaries

## Update 49_cs0 (2020-06-23)

* Script "Container assets management (manual)": load event type check disabled on manual run
* Script "Container assets management (auto)": Automated execution from `scripts\project_changed\container_assets.groovy` disabled (temporarily)

## Update 48_cs0 (2020-06-23)

* Container assets management script now runs only on project load
* Script `container_assets.groovy` moved (hopefully temporarily) to manual mode, due to conflict in the `project_changed` folder (bug 860)

## Update 47_cs0 (2020-06-17)

* Updated version_notes.txt

## Update 46_0s0 (2020-06-17)

* New script for fetching container-specific language assets [`container_assets.groovy`]

## Update 45_cs0 (2020-06-11)

* Customization script: Updated to account for mk headers in the version_notes.txt file.

## Update 44_c0p (2020-06-11_dev)

* OMT packages: Added progress message for unpacking projects in the status bar (OMT plugin v. 1.6.0)
* OMT packages: Added logs to the OMT plugin project for every packing action: `${timestamp}: Packed with OMT plugin version X.X.X.`
* OMT packages: Re-enabled prompt to remove OMT after unpacking project
* OMT packages: Updated configuration to avoid including Mac's hidden files (starting with `._`) when packing the project
* Autotext: Added new `\minus` autotext shortcut for n-dash (U+2013)
* View: Temporarily disabled default bidi marks (overlapping the new bidimarkers plugin)
* Search: Include orphan segments in searches
* Tags: Disallowed tag definitions updated to account for `<i0>`-like Okapi tags

## Update 43_c00 (2020-05-04_dev)

* Autotext: Added Arabic percentage symbol to the autotext table

## Update 42_csp (2020-04-27_dev)

* Updated custom tag to avoid matching Okapi Open XML filter's tags as disallowed text.
* Added plugin `omegat-bidimarkers-0.2.0-all.jar` to improve painting of bidirectionality embeddings
* Updated Okapi plugin to `okapiFiltersForOmegaT-1.6-m40-custom.jar` to include Okapi's Open XML filter
* Updated OMT plugin `plugin-omt-package-1.4.1.jar` to add option "Pack and delete project".
* Added script "Restore files order" [`restore_files_order.groovy`] that restores original file order on project load to keep segment numbers unchanged

## Update 41_0s0 (2020-04-14_dev)

* Added customization script [`updateConfigBundle.groovy`] v0.4.8 with autoLaunch to install_dir/plugins/application_startup

## Update 40_0s0 (2020-04-14_dev)

* Updated customization script [`updateConfigBundle.groovy`] to version 0.4.8 to include `autoLaunch` and removing `install_dir/plugins`

## Update 39_csp (2020-04-30)

* Updated script **Xliterate Serbian Project (Latin to Cyrillic)**  [`xliter8_latn2cyrl.groovy`] to accomodate Serbian spelling specificities

## Update 38_c00 (2020-03-11)

* Updated OMT plugin settings to exclude target files but not target folder
* Added script Copy Source to slot #8 in the list of scripts

## Update 37_c00 (2020-03-10)

* Updated custom tags to remove some incorrect forbidden patterns (e.g. `<<t0/>O'zbekiston<t1/>>`, `48 < 57 so less than half`, etc.).
* Updated OMT plugin settings to exclude .7z and .rar packages inside the project when packing

## Update 36_c00 (2020-03-09)

* Updated OMT plugin settings to exclude .empty files from the source folder

## Update 35_csp (2020-03-04)

* Fixed bug in transliteration script (it used source, not target)
* Fixed bug in transliteration script (segments containing only tags were left untranslated)
* Updated OMT plugin to version 1.4.1 to include logs
* Updated OMT plugin settings to exclude target files and keep active TM's backups
* Added script (copy_source.groovy) to populate the target segment with the source text, if untranslated.

## Update 34_c00 (2020-01-29)

* Fixed non-breaking space autotext entry

## Update 33_csp (2020-01-24)

* OMT plugin 0.4.0: new version to have relative paths in exclude patterns
* OMT plugin settings: final project TMs at project root excluded from package

## Update 32_c00 (2020-01-24)

* OMT plugin settings: all final project TMs NOT excluded from package

## Update 31_cs0 (2020-01-22)

* Added script to pseudo-translate (pseudoxlate.groovy)
* OMT plugin settings: all final project TMs excluded from package

## Update 30_csp (2020-01-09)

* Updated script updateConfigBundle.groovy (to version 0.4.6) to update portable installations.

## Update 29_csp (2019-12-23)

* Disabled prompt asking to remove OMT file after import
* Update updateConfigBundle.groovy script to:
  - allow custom update in config dir for participants.
  - include URL rather than ask the user.

## Update 28_csp (2019-12-19)

* Added script to transliterate Latin to Cyrillic (xliter8_latn2cyrl.groovy)
* Updated Okapi plugin to force approval of all segments on project load (to version 1.6-m36-capstan.jar)

## Update 27_csp (2019-11-18)

* Updated OMT plugin to v. 1.3.0, now compatible with new versions of OmegaT. Users should not update or download OmegaT from links other than the one in the UG, but if they do that shouldn't break the plugin.

## Update 26_csp (2019-10-04)

* Added autotext shortcut to insert non-breaking hyphen
* Tweaked Okapi plugin to approve all segments on load
* Tweaked pisaconv.groovy script does not force reload any more
* Added FlushUneditedEntries.groovy script to restore alternative translations in bilingual XLIFF files

## Update 25_cs0 (2019-09-17)

* Updated URL to download hunspell dictionaries
* Added language kk-MN / kaz-MNG to conversion script
* Added language bs-RS / bos-SRB to conversion script
* Updated language az-GE / azj-GEO to conversion script

## Update 24_c00 (2019-08-08)

* Fixed bug with Word files getting removed from the source folder

## Update 23_c00 (2019-08-01)

* Added some new international languages (-ZZZ) for PIAAC C2 Doorstep Interview

## Update 22_csp (2019-07-03)

* Teak custom tags to avoid protecting some `<forced adaptations>`
* Korean currency symbol added to the character map (₩)

## Update 21_csp (2019-06-21)

* Updated validation patterns (custom tags, fragments to be removed)
* New version (1.1.0) of OMT plugin, can now be run on the command line
* Updated path to spell checking files (to fix bug: button not active)
* Updated script to export in Excel (now called write_project2excel.groovy) which now includes tu's ID

## Update 20_csp (2019-05-31)

* Protection added for numeric character entities (&#x203A)
* Protection added for localization placeholders (%s)
* Orphan segments not shown anymore in concordance searches
* Tagwipe script added
* New version of OMT plugin, fixes .empty issue

## Update 10_csp (2019-05-07)

* New shortcut to insert apostrophes
* `<strong>` now matched as tag and not as forced adaptation
* Added script to convert Western to Arabic-Indic numbers globally
* Added Eastern Arabic numbers to Autotext and Character Map
* Customized colors for x-auto (pink) and x-enforced (orange)

## Update 9_csp (2019-04-15)

* URL to spellchecking files tweaked
* OMT package plugin updated to v. 1.0.5 (errors on CLI fixed)
* Deletes file config/MainMenuShortcuts.properties
* Added script write_project2TMX.groovy to export the whole project as TMX (including repetitions)

## Update 8_cs0 (2019-04-02)

* Translated tags out of order not allowed

## Update 7_csp (2019-04-01)

* Path in the zipped entries contains "/" instead of "\"
* OMT package plugin saves project before packing
* Customization script added

## Update 6_csp (2019-03-25)

* MT auto fetch disabled
* Issue with spaces in username fixed
* Custom tags updated (added: sub, span, var)
* Color of remove pattern changed to purple (e.g. forced adaptations)
* OMT plugin will ask if imported OMT package is to be deleted

## Update 5_c00 (2019-03-20)

* Custom chartable fixed
* Search dialog sync options disabled
* Tags not counted in statistics
* Shortcut issues fixed
* Custom tags updated

https://cat.capstan.be/OmegaT/v572/index.php
