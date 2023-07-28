# OmegaT setup

OmegaT's default factory settings (with regards to user interface and some behaviour) are pretty old and not really suitable for all kinds os tasks. Tweaking the default preferences by hand one by one is tedious and error-prone, especially for large teams where preferences are expected to be homogeneous across the team. This automated customization removes the burden of doing that manually from translators working in a team (or any OmegaT user).

## What is this setup

Default options in OmegaT user preferences are not always the most convenient ones (or not in line with modern CAT tool best practices) but they can be tweaked. This setup tweaks them for you and chooses the most convenient value for every option. On the other hand, OmegaT is extensible by means of scripts and plugins, and this setup adds those for you as well.

In a nutshell, this setup consists of tweaked preferences and configuration files as well as extensions in functionality by means of scripts and plugins.

## Installing and setting up OmegaT

### Windows

Please follow [this guide](https://slides.com/capstan/omegat-v5-install-and-setup-guide/fullscreen).

Alternatively, these are the basic steps (a summary of the guide above):

1. Make sure file extensions are displayed in your computer, you can follow tips [here](https://www.howtogeek.com/205086/beginner-how-to-make-windows-show-file-extensions/).
2. Install OmegaT 5.7.1, you can find it [here](https://sourceforge.net/projects/omegat/files/OmegaT%20-%20Latest/OmegaT%205.7.1/OmegaT_5.7.1_Beta_Windows_64_Signed.exe/download).
3. Download the setup script from [here](https://cat.capstan.be/OmegaT/installer/scripts/updateConfigBundle.groovy). When the page shows the script, you can do **Ctrl+S** (or right click and choose Save). Please make sure your browser doesn't add a `.txt` extension or any other (the file extension should be `.groovy`).
4. In OmegaT, go to **Tools** > **Scripting** > **File** > **Open Script** and open the setup script from the location where your browser saved it (probably in your Downloads folder).
5. When the script is open in the scripting window, press the **Run** button.

The script will run and set up your installation. You might need to restart OmegaT at the end.

<!-- @TODO: write instructions for chocolatey in PowerShell -->

### Linux (both desktop and servers)

```
bash -c "$(curl -fsSL https://raw.githubusercontent.com/capstanlqc/omegat-setup/master/custo/omtlinux_custom_installer.sh)"
```

### macOS

Please follow [this guide](https://slides.com/capstan/omegat-v5-install-and-setup-guide-macos/fullscreen).
