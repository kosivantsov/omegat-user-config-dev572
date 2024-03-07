# OmegaT setup

OmegaT's default factory settings (with regards to user interface and some behaviour) are pretty old and not really suitable for all kinds os tasks. Tweaking the default preferences by hand one by one is tedious and error-prone, especially for large teams where preferences are expected to be homogeneous across the team. This automated customization removes the burden of doing that manually from translators working in a team (or any OmegaT user).

## What is this setup?

Default options in OmegaT user preferences are not always the most convenient ones (or not in line with modern CAT tool best practices) but they can be tweaked. This setup tweaks them for you and chooses the most convenient value for every option. On the other hand, OmegaT is extensible by means of scripts and plugins, and this setup adds those for you as well.

In a nutshell, this setup consists of tweaked preferences and configuration files as well as extensions in functionality by means of scripts and plugins.

## Installing and setting up OmegaT

### Windows

Please follow [this guide](https://capstanlqc.github.io/omegat-guides/translation/install-and-setup/).

<!-- @TODO: write instructions for chocolatey in PowerShell -->

### Linux (both desktop and servers)

TO BE UPDATED
<!-- 
```
bash -c "$(curl -fsSL https://raw.githubusercontent.com/capstanlqc/omegat-setup/master/custo/omtlinux_custom_installer.sh)"
```
-->

### macOS

Please follow [this guide](https://capstanlqc.github.io/omegat-guides/translation/install-and-setup-macos/).

## How to update this repository \[INTERNAL\]

Between releases: 

1. Add/update/remove whatever config/script/plugin files as needed
2. Update the `changes.md` file with a description of the updates, under the next release header

When a new release is ready: 

3. Make sure the next update header has:

	- a number that is correlatively higher in one unit to the previous update
	- a correct `csp` indication of what exactly is being updated: 
		- the first letter is mandatorily `c` (config)
		- the second letter is `s` if there are updates in scripts, otherwise `0`
		- the third letter is `p` if there are updates in plugins, otherwise `0`
	- the date of the expected release

Example: `## Update 98_cs0 (2024-02-28)`