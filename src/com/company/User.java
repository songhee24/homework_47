package com.company;


import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.ArrayList;

public class User {
    private int id;
    private String login;
    private String password;
    private String email;
    private Date dateOfRegistration;



    public User() {
    }

    public User( String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }


    @Override
    public String toString() {
        return  login;
    }
    public  String reversePassword(String password){

        byte [] strAsByteArray = password.getBytes();

        byte [] result = new byte [strAsByteArray.length];
        for (int i = 0; i<strAsByteArray.length;i++){
            result[i] = strAsByteArray[strAsByteArray.length-i-1];
        }
        String str = new String(result);
        ArrayList<Character> num = new ArrayList<>();
        for (char c:str.toCharArray()){
            num.add(c);
        }
        String b = "";
        for (int i = 0;i < num.size();i++){
            b =(num.toString().replaceAll("\\[|\\]|\\,\\ ", ""));
        }
        String answer = b + num.get(num.size()-1);
        return answer;
    }
/*    public User getPasswordForCheck(String password) {
        String SQL = "select * from users where password = ?";
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(SQL);) {
            statement.setString(1, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();;
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
