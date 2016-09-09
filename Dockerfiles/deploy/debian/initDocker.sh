#!/bin/bash
docker build -t clientconsole .
xhost +
docker run -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=unix$DISPLAY clientconsole
