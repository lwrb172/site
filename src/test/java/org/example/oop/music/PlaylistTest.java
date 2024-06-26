package org.example.oop.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void atSecondTest() {
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);

        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        assertEquals(song1, playlist.atSecond(200));
        assertEquals(song2, playlist.atSecond(350));
        assertEquals(song3, playlist.atSecond(600));
    }

    @Test
    public void IndexOutOfBoundsExceptionTest() {
        Playlist playlist = new Playlist();

        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);

        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-6));
        assertEquals("Seconds less than 0: -6", exception.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(900));
        assertEquals("Seconds greater than playlist time: 900", exception2.getMessage());
    }
}
