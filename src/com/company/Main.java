package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static DBUser DB = new DBUser();
    static Scanner scanner = new Scanner(System.in);
    static String pass = scanner.nextLine();
    public static void main(String[] args) throws SQLException {

        User user = new User("Muhtar",pass,"eemalkkk@gmail.com");
        User user1 = new User("azamat",pass,"emalk@gmail.com");
        UserLogs userLogs = new UserLogs();
//        register(user);
         login(user1.getLogin(),user1.getPassword());
        System.out.println(DB.getAllAttempts());
    }


    static void register(User user){
        if (DB.addUser(user)) System.out.println("Register success");
        else System.out.println("Register failed");
    }

    static void login(String login,String password){
        User user = DB.getUserBylogin(login);
        if (user == null){
            System.out.println(Status.FAIL + " Login or password is incorrect ");
            return;
        }
        String userRemotePassword = DB.takePassById(user.getId());
        System.out.println(pass + " locale");
        System.out.println(userRemotePassword + " remote");
        if (userRemotePassword.equals(pass)) {
            System.out.println(Status.FAIL + " Password is incorrect");
            int count = DB.countIncorrectAttempts(user.getId());
            System.out.println("incorrect attempts "+ count);
            if (count > 3){
                DB.addUserToBlackList(user.getId(),"fail");
                System.out.println("you added to black list");
            }
            return;
        }
        String reversePass = user.reversePassword(pass);
        if (userRemotePassword.equals(reversePass)){
            DB.loginUser(user.getId(),"ok");
            System.out.println(reversePass + " locale");
            System.out.println(userRemotePassword + " remote");
            DB.resetIncorrectAttemptsByUser(user.getId(),"fail");
            System.out.println(Status.OK + " Authorization successful");
            System.out.println("привет " + DB.getLoggedInUser(user));
            return;
        } else if (!userRemotePassword.equals(reversePass)){
            DB.loginUser(user.getId(),"fail");
            int count = DB.countIncorrectAttempts(user.getId());
            System.out.println("incorrect attempts "+ count);
            System.out.println(Status.FAIL + " Password is incorrect");
            if (count > 3){
                DB.addUserToBlackList(user.getId(),"fail");
                System.out.println("you added to black list");
            }
            return;
        }
//        if (pass.equals(password)){
//            DB.loginUser(user.getId(),"ok");
//            DB.resetIncorrectAttemptsByUser(user.getId(),"fail");
//            System.out.println(Status.OK + " Authorization successful");
//            return;
//        }
}

}

enum Status {
    FAIL,
    OK;
   }
