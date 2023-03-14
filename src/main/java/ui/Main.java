package ui;

import repo.Repository;
import repo.impl.RepositoryImpl;

public class Main {
    public static void main(String[] args) {
        Repository repository = new RepositoryImpl();
        UserInterface ui = new UserInterface(repository);
        ui.communication();
    }
}
