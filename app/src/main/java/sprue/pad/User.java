package sprue.pad;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String email;
    public String password;

    public User() {
        // just a constructor dont mind
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
