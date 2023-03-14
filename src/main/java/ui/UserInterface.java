package ui;

import models.Film;
import repo.Repository;
import repo.impl.DBManager;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    Repository repository;
    Validator validator;
    DBManager dbm;

    public UserInterface(Repository rep) {
        repository = rep;
        this.validator = new Validator();
        this.dbm = new DBManager();
    }

    public void communication() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    Press 1 to see list of all films
                    Press 2 to see list of watched films
                    Press 3 to see list of unwatched films
                    Press 4 to move film to watched list
                    Press 5 to exit""");
            int inputValue;
            try {
                inputValue = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input");
                scan.nextLine();
                continue;
            }
            switch (inputValue) {
                case 1 -> printListOfAllFilms();
                case 2 -> printListOfWatchedFilms();
                case 3 -> printListOfUnwatchedFilms();
                case 4 -> moveFilmToWatchedList();
                case 5 -> {
                    scan.close();
                    System.exit(0);
                }
                default -> System.out.println("Incorrect input");
            }
        }
    }

    public void printListOfAllFilms() {
        System.out.println(repository.getListOfAllFilms());
    }

    public void printListOfWatchedFilms() {
        System.out.println(repository.getListOfWatchedFilms());
    }

    public void printListOfUnwatchedFilms() {
        System.out.println(repository.getListOfUnwatchedFilms());
    }

    public void moveFilmToWatchedList() {
        ArrayList<Film> allFilms = new ArrayList<>(repository.getListOfAllFilms());
        System.out.println(allFilms);
        String input;
        System.out.println("Choose the title of film you want to move");
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
        if (validator.validateInput(input, allFilms)) {
            dbm.saveFilm(findFilm(input, allFilms));
            System.out.println("Film was successfully added");
        } else {
            System.out.println("Incorrect input try again--------------------------------------------------------");
            moveFilmToWatchedList();
        }
    }

    public Film findFilm(String input, ArrayList<Film> allFilms) {
        for (Film f : allFilms) {
            if (input.equalsIgnoreCase(f.getTitle())) {
                return f;
            }
        }
        return null;
    }
}

