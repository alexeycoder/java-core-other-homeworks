#!/bin/bash

create_files() {
	dirpath="$1"

	find $dirpath -type f -delete

	for i in {1..10}; do
		len=$((5 + $RANDOM % 10))
		filename="$(cat /dev/urandom | tr -dc '[:alnum:]' | head --bytes=$len).txt"
		len=$((1 + $RANDOM % 9))
		echo -e "$(cat /dev/urandom | tr -dc '[:alnum:]' | head --bytes=${len}K | fold)" >"${dirpath}$filename"
	done
}

create_files "./testdir/"
create_files "./testdir/innerdir/"
