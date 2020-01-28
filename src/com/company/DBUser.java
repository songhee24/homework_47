package com.company;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.sql.*;
import java.util.ArrayList;

public class DBUser {
    public boolean addUser(User user) {
        String SQL = "insert into users(login,password,email,date_of_registration) values(?,?,?,now())";
        try (Connection connection = Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.reversePassword(user.getPassword()));
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            System.out.println("success added user");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    public  String reversePassword(String password){
//
//        byte [] strAsByteArray = password.getBytes();
//
//        byte [] result = new byte [strAsByteArray.length];
//        for (int i = 0; i<strAsByteArray.length;i++){
//            result[i] = strAsByteArray[strAsByteArray.length-i-1];
//        }
//        String str = new String(result);
//        ArrayList<Character> num = new ArrayList<>();
//        for (char c:str.toCharArray()){
//            num.add(c);
//        }
//        String b = "";
//        for (int i = 0;i < num.size();i++){
//            b =(num.toString().replaceAll("\\[|\\]|\\,\\ ", ""));
//        }
//        String answer = b + num.get(num.size()-1);
//        return answer;
//    }

    public String takePassById(int id){
        String SQL = "select * from users  where id = ?;";
        String remotePassword = "";
        try(Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()) {
               if (resultSet.next()){
                  remotePassword  = resultSet.getString("password");
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return remotePassword;
    }


public User getUserBylogin(String login) {
    String SQL = "select * from users where login = ?";
    try (Connection connection = Database.connect();
         PreparedStatement statement = connection.prepareStatement(SQL);) {
        statement.setString(1, login);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setId(resultSet.getInt("id"));
                return user;
            }
            return null;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}

    public boolean loginUser(int id,String status){
        String SQL = "insert into usersLogs(user_id,date_Of_in,status) values(?,now(),?)";
        try(Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1,id);
            statement.setString(2,status);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int countIncorrectAttempts(int id){
        String SQL = "select count(*) from userslogs where user_id = ? and status = 'fail' ";
        try(Connection connection = Database.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                int count = 0;
                while (resultSet.next()){
                    count = resultSet.getInt("count");
                }
                return count;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public void resetIncorrectAttemptsByUser(int id,String status){
        String SQL = "delete from usersLogs where user_id = ? and status = ?";
        try(Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(SQL);) {
            statement.setInt(1,id);
            statement.setString(2,status);
            statement.executeUpdate();
            System.out.println("User's attempts updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserToBlackList(int id,String status){
        String SQL = "insert into blockedusers(user_id)\n" +
                "select u.user_id from userslogs u where user_id = ? and  status = ?;";
        try(Connection connection = Database.connect();
            PreparedStatement statement = connection.prepareStatement(SQL);) {
            statement.setInt(1,id);
            statement.setString(2,status);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public LoggedUser getLoggedInUser(User user){
        String SQL = "select * from users \n" +
                "join userslogs u on users.id = u.user_id\n" +
                "where u.status ='ok'";
        try(Connection connection = Database.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    LoggedUser loggedUser = new LoggedUser();
                    user = getUserBylogin(user.getLogin());
                    loggedUser.setUser(user);
                return loggedUser;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<UserLogs> getAllAttempts(){
        ArrayList<UserLogs> userLogs = new ArrayList<>();
        String SQL = "select * from usersLogs";
        try(Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()){
                   UserLogs userLog =new UserLogs();
                   userLog.setUserId(resultSet.getInt("USER_ID"));
                   userLog.setStatus(resultSet.getString("STATUS"));
                   userLogs.add(userLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLogs;
    }



}

