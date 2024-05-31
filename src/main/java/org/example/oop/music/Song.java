package org.example.oop.music;

//public record Song(String artist, String title, int duration) {}

import org.example.oop.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class Song {
    public String artist;
    public String title;
    public int duration;

    public Song(String artist, String title, int duration) {
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration && Objects.equals(artist, song.artist) && Objects.equals(title, song.title);
    }

    public static class Persistence {
        public static Optional<Song> read(int id) throws SQLException {
            String sql = "SELECT artist, title, length FROM song WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getConnection("songs.db").prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new Song(
                        resultSet.getString("artist"),
                        resultSet.getString("title"),
                        resultSet.getInt("length")
                ));
            }
            return Optional.empty();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, duration);
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
