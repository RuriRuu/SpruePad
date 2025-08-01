package sprue.pad;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String username;
    public String password;

    public User() {
        // just a constructor dont mind
    }

    public User(String email, String password) {
        this.username = username;
        this.password = password;
    }
}
