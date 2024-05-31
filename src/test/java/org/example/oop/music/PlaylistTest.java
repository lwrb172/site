package org.example.oop.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaylistTest {
    @Test
    void isEmpty() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    void hasOne() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Heavy Young Heathens", "Being Evil has a Price", 194));
        assertEquals(1, playlist.size());
    }

    @Test
    void correctlyAdded1() {
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        Playlist playlist = new Playlist();
        playlist.add(song);
        assertEquals(song,playlist.getFirst());
    }

    @Test
    void correctlyAdded2() {
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        Playlist playlist = new Playlist();
        playlist.add(new Song("Heavy Young Heathens", "Being Evil Has a Price", 194));
        assertEquals(song,playlist.getFirst());
    }
}
