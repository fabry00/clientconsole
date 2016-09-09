#!/bin/bash
PRJFOLDER=clientconsole
SRC_PRJ=javafx_example
TARGET_PRJ=clientconsole
SRC_PKG='javafxexample'
TARGET_PKG=console

echo "find $PRJFOLDER -type f -print0 | xargs -0 sed -i 's/$SRC_PRJ/$TARGET_PRJ/g'"
read -n1 -r -p "Press space to continue..." key

if [ "$key" = '' ]; then
    find $PRJFOLDER -type f -print0 | xargs -0 sed -i "s/$SRC_PRJ/$TARGET_PRJ/g"
else
    exit
fi


echo "find $PRJFOLDER -type f -print0 | xargs -0 sed -i 's/com\.$SRC_PKG/com\.$TARGET_PKG/g'"
read -n1 -r -p "Press space to continue..." key
if [ "$key" = '' ]; then
    find $PRJFOLDER -type f -print0 | xargs -0 sed -i "s/com\.$SRC_PKG/com\.$TARGET_PKG/g"
else
    exit
fi


echo "mv $PRJFOLDER/src/main/java/com/$SRC_PKG $PRJFOLDER/src/main/java/com/$TARGET_PKG"
read -n1 -r -p "Press space to continue..." key
if [ "$key" = '' ]; then
    mv $PRJFOLDER/src/main/java/com/$SRC_PKG $PRJFOLDER/src/main/java/com/$TARGET_PKG
else
    exit
fi

echo "mv $PRJFOLDER/src/main/resources/com/$SRC_PKG $PRJFOLDER/src/main/resources/com/$TARGET_PKG"
read -n1 -r -p "Press space to continue..." key
if [ "$key" = '' ]; then
    mv $PRJFOLDER/src/main/resources/com/$SRC_PKG $PRJFOLDER/src/main/resources/com/$TARGET_PKG
else
    exit
fi



echo "mv $PRJFOLDER/src/test/java/com/$SRC_PKG $PRJFOLDER/src/test/java/com/$TARGET_PKG"
read -n1 -r -p "Press space to continue..." key
if [ "$key" = '' ]; then
    mv $PRJFOLDER/src/test/java/com/$SRC_PKG $PRJFOLDER/src/test/java/com/$TARGET_PKG
else
    exit
fi




