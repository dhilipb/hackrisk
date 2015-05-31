package com.mu.mothersunited.model;

public class User {

    String id;
    String userType;

    public User(String id, String userType)
    {
        this.id = id;
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

}
