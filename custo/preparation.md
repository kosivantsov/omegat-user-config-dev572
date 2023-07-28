# How to update the hash file

git clone ORG/omegat-user-config-dev572
cd omegat-user-config-dev572
find * -type f -exec md5sum {} \; > remote.md5
