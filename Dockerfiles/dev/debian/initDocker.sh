#!/bin/bash
docker build -t clientconsole_dev .
xhost +
docker run -it -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=unix$DISPLAY clientconsole_dev
