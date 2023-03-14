package models;


public class Film {
    private final String id;
    private final String title;
    private final String rank;
    private final String imDbRating;


    public Film(String id, String title, String rank, String imDbRating) {
        this.id = id;
        this.title = title;
        this.rank = rank;
        this.imDbRating = imDbRating;

    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getRank() {
        return rank;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                "title='" + title + '\'' +
                "rank='" + rank + '\'' +
                "imDbRating='" + imDbRating + '\n';
    }
}
