package com.example.myapplication;

public class User{
    private String password;
    private String username;

        public User(){
            username = null;
            password = null;
        }

        public User(String username, String password){
            this.username = username;
            this.password = password;
        }

        @Override
        public boolean equals(Object o){
            if (this == o)
                return true;
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            User other = (User) o;
            return username.equals(other.toString()) && password.equals(other.getPassword());
        }

        @Override
        public int hashCode(){
            return username.hashCode();
        }

        @Override
        public String toString(){
            return username;
        }

        public String getPassword(){
            return password;
        }

        public String getUsername() {
            return username;
        }

        public static boolean equalUsername(User user1, User user2){
            return user1.getUsername().equals(user2.getUsername());
        }
}
