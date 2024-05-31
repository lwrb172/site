package org.example.oop.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int seconds) {
        int secondsSong = 0;
        if (seconds < 0) {
            throw new IndexOutOfBoundsException("Seconds less than 0: " + seconds);
        }
        for(Song song : this) {
            if(seconds >= secondsSong && seconds < (secondsSong + song.getDuration())) {
                return song;
            }
            secondsSong += song.getDuration();
        }
        throw new IndexOutOfBoundsException("Seconds greater than playlist time: " + seconds);
    }
}
