package org.example.oop.music;

import org.example.oop.database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DbTest {
    @BeforeAll
    static void connectDatabase() {
        DatabaseConnection.connect("src/main/resources/org/example/oop/music/songs.db", "songs.db");
    }

    @Test
    void songRead() {
        try {
            Optional<Song> song = Song.Persistence.read(1);
            assertTrue(song.isPresent());
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongs")
    public void testReadParameterized(int index, Song expectedSong) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(expectedSong, song.orElse(null));
    }

    static Stream<Arguments> provideIndexesAndExpectedSongs() {
        return Stream.of(
                arguments(3, new Song("Led Zeppelin", "Stairway to Heaven", 482)),
                arguments(47, new Song("The Doors", "Riders on the Storm", 434)),
                arguments(-1, null),
                arguments(100, null)
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "songs.csv", numLinesToSkip = 1)
    public void testReadParameterized(int index, String artist, String title, int duration) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        Song expectedSong = new Song(artist, title, duration);
        assertEquals(expectedSong, song.orElse(null));
    }

    @AfterAll
    static void disconnectDatabase() {
        DatabaseConnection.disconnect("songs.db");
    }

}
