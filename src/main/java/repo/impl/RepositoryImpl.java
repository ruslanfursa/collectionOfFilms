package repo.impl;

import models.Film;
import models.Movies;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import repo.Repository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class RepositoryImpl implements Repository {
    private final static String URL = "https://imdb-api.com/en/API/Top250Movies/k_qp2fx7s6";
    private final static String PATH = "mock/response.json";
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<Film> allFilms = null;


    private String getJsonString() {
        String jsonString = null;
        Request request = new Request.Builder()
                .url(URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                System.out.println("Status is not 200");
                return null;
            } else {
                jsonString = Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            System.out.println("Wrong input");
        }
        return jsonString;
    }


    private String getMockJsonString() {
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            FileReader fr = new FileReader(PATH);
            BufferedReader bf = new BufferedReader(fr);
            String s = bf.readLine();
            while (s != null) {
                jsonBuilder.append(s);
                s = bf.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonBuilder.toString();
    }


    @Override
    public List<Film> getListOfAllFilms() {
        Movies films = null;
        if(allFilms == null) {
            String jsonString = getJsonString();
            if (jsonString == null) {
                System.out.println("Incorrect address");
                return new ArrayList<>();
            }
            try {
                Gson gson = new Gson();
                films = gson.fromJson(jsonString, Movies.class);
                allFilms = films.getItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            return allFilms;
        }
        return (films != null) ? films.getItems() : new ArrayList<>();
    }


    public ArrayList<Film> getListOfUnwatchedFilms() {
        RepositoryImpl rep = new RepositoryImpl();
        DBManager db = new DBManager();
        HashSet<String> watched = new HashSet<>(db.loadFilms());
        ArrayList<Film> unwatched = new ArrayList<>(rep.getListOfAllFilms());
        unwatched.removeIf(film -> watched.contains(film.getId()));
        return unwatched;
    }
    public ArrayList<Film> getListOfWatchedFilms() {
        RepositoryImpl rep = new RepositoryImpl();
        DBManager db = new DBManager();
        HashSet<String> watched = new HashSet<>(db.loadFilms());
        ArrayList<Film> unwatchedFilms = new ArrayList<>(rep.getListOfAllFilms());
        unwatchedFilms.removeIf(film -> !(watched.contains(film.getId())));
        return unwatchedFilms;
    }
}

