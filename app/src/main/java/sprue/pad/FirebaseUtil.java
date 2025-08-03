package sprue.pad;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class FirebaseUtil {
    public static void addUserToDatabase(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        long createdAt = System.currentTimeMillis();

        User user = new User(uid, email, createdAt);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("users").child(uid).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "User added to database.");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to add user: " + e.getMessage());
                });
    }
}
