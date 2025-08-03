package sprue.pad;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String uid;
    public String email;
    public long createdAt;

    public User() {
        // just a constructor dont mind
    }

    public User(String uid, String email, long createdAt) {
        this.uid = uid;
        this.email = email;
        this.createdAt = createdAt;
    }
}
