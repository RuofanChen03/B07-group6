package com.example.myapplication;

public class User{
        private String username;
        private String password;
//        private String type;

        public User(String username, String password){
            this.username = username;
            this.password = password;
//            this.type = type;
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
            return username.equals(other.getUsername()) && password.equals(other.getPassword());
//                    type.equals(other.getType());
        }

        @Override
        public int hashCode(){
            return username.hashCode();
        }

        @Override
        public String toString(){
            return "User: " + username;
        }

        public String getUsername(){
            return username;
        }

        public String getPassword(){
            return password;
        }

//        public String getType() {
//            return type;
//        }
}
