package com.company;

import java.sql.Date;

public class UserLogs {
   private int userId;
   private int id;
   private Date dateOfIn;
   private String status;

   public UserLogs() {
   }

   public UserLogs(int userId, Date dateOfIn, String status) {
      this.userId = userId;
      this.dateOfIn = dateOfIn;
      this.status = status;
   }

   public UserLogs(int userId, int id, Date dateOfIn, String status) {
      this.userId = userId;
      this.id = id;
      this.dateOfIn = dateOfIn;
      this.status = status;
   }

   public UserLogs(int userId) {
      this.userId = userId;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Date getDateOfIn() {
      return dateOfIn;
   }

   public void setDateOfIn(Date dateOfIn) {
      this.dateOfIn = dateOfIn;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "UserLogs{" +
              "userId=" + userId +
              "status='" + status + '\'' +
              '}'  +"\n";
   }
}

