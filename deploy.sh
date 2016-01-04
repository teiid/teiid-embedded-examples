#!/bin/bash
echo "--------------------------------------------------------------------"
echo "Deploy Gitbook static website to gh-pages(gitbook is a prerequisite)"
echo "--------------------------------------------------------------------"
gitbook build
git checkout gh-pages
cp -r ./_book/* ./
rm -fr _book
git add --all
git commit -m "dodeploy"
git push origin gh-pages
git checkout master
