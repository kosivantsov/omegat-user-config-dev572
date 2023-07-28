#!/usr/bin/env python3

# from user config folder root, run as 
# python docs/mkdocs.py

import sys, os
import pandas as pd
from tomark import Tomark

# file exists in cwd
rel_fpath = "omegat.autotext"

if not os.path.isfile(rel_fpath):
    print("File not found.")
    sys.exit(0)

df = pd.read_csv(rel_fpath, sep="\t", names=["Hotkey", "Character", "Description"])
entries = df.to_dict("records")

md_tbl = "# OmegaT autotext entries\n\n" + Tomark.table(entries)

with open('docs/omegat-autotext.md', 'w') as f:
    f.write(md_tbl)