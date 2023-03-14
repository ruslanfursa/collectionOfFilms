package repo.impl;

import models.Film;

import java.util.HashSet;

public interface StorageManager {
    HashSet<String> loadFilms();
    void saveFilm(Film film);
    void close();
}
