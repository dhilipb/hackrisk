package com.mu.mothersunited.model;

public class User {

    String facebookId;
    String userType;

    public User(String facebookId, String userType)
    {
        this.facebookId = facebookId;
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
        return facebookId.equals(user.facebookId);
    }

    @Override
    public int hashCode()
    {
        return facebookId.hashCode();
    }

}
