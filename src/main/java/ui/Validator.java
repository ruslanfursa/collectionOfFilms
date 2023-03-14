package ui;

import models.Film;

import java.util.ArrayList;

public class Validator {
    public boolean validateInput(String input, ArrayList<Film> allFilms) {
        for (Film f : allFilms) {
            if (input.equalsIgnoreCase(f.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
