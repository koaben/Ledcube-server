#!/bin/bash


for x in {0..7}
do
	for y in {0..7}
	do
		for z in {0..7}
		do
			URL=http://localhost:8080/ledcube/rest/cube/$x/$y/$z/off
			curl -XPOST ${URL}
		done
	done
done		
