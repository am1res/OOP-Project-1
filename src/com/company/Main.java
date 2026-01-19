package src.com.company;

import src.com.company.controllers.UserController;
import src.com.company.controllers.interfaces.IUserController;
import src.com.company.data.PostgresDB;
import src.com.company.data.interfaces.IDB;
import src.com.company.repositories.UserRepository;
import src.com.company.repositories.interfaces.IUserRepository;

public class Main {

    public static void main(String[] args) {
        // Here you specify which DB and UserRepository to use
        // And changing DB should not affect to whole code
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "postgres");
        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);

        MyApplication app = new MyApplication(controller);

        app.start();

        db.close();
    }
}
