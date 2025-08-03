package sprue.pad;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class FirebaseUtil {
    public static void addUserToDatabase(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        long createdAt = System.currentTimeMillis();

        User user = new User(email, createdAt);

        DatabaseReference spruePadDatabaseReference = FirebaseDatabase.getInstance().getReference();
        spruePadDatabaseReference.child("users").child(uid).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "User added to database.");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to add user: " + e.getMessage());
                });
    }

    public static void addProjectToDatabase(String projectName, String brand, String scale, String status, String notes) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Project project = new Project(projectName, brand, scale, status, notes);
        databaseReference.child("users").child(uid).child("projects").child(projectName).setValue(project).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Log.d("Firebase", "Project added to database.");
                    } else {
                        Log.e("Firebase", "Failed to add project: " + task.getException());
                    }
                }
        );
    }
}
