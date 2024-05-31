package org.example.oop.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int seconds) {
        int sum = 0;
        for (Song song : this) {
            sum += song.getDuration();
            if (sum >= seconds) return song;
        }
        return null;
    }
}
