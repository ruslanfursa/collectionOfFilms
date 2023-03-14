package repo.impl;

import models.Film;

import java.sql.*;
import java.util.HashSet;


public class DBManager implements StorageManager {
    private Connection con = null;

    private final static String URL = "jdbc:sqlite:./test.db";

    public DBManager() {
        connect();
    }

    @Override
    public HashSet<String> loadFilms() {
        HashSet<String> movies = new HashSet<>();
        try {
            if (con == null) {
                System.out.println("There is no connection to DB");
                return movies;
            }
            String sql = "SELECT * FROM watched_films";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                String identifier = rs.getString("identifier");
                movies.add(identifier);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("sql error");
            for (StackTraceElement s : e.getStackTrace()) {
                System.out.println(s);
            }
        }
        return movies;
    }

    @Override
    public void saveFilm(Film film) {
        try {
            if (con == null) {
                System.out.println("There is no connection to DB");
                return;
            }
            String sql = String.format("""
                    INSERT INTO watched_films (identifier)
                    VALUES('%s')
                    """, film.getId());
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            System.out.println("sql error " + e.getMessage() + " " + e.getClass());
            for (StackTraceElement s : e.getStackTrace()) {
                System.out.println(s);
            }
        }
    }

    @Override
    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("sql error");
                for (StackTraceElement s : e.getStackTrace()) {
                    System.out.println(s);
                }
            }
        }
    }

    private void connect() {
        try {
            con = DriverManager.getConnection(URL);
            String sql = """
                    CREATE TABLE IF NOT EXISTS watched_films (
                      id integer PRIMARY KEY AUTOINCREMENT,
                      identifier text NOT NULL UNIQUE                    
                    );""";
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("sql error");
            for (StackTraceElement s : e.getStackTrace()) {
                System.out.println(s);
            }
        }
    }

}
