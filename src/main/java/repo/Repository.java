package repo;

import models.Film;

import java.util.ArrayList;
import java.util.List;

public interface Repository {


    List<Film> getListOfAllFilms();

    ArrayList<Film> getListOfUnwatchedFilms();

    ArrayList<Film> getListOfWatchedFilms();






}
