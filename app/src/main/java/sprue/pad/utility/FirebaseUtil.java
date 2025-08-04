package sprue.pad.utility;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import sprue.pad.model.Project;
import sprue.pad.model.User;

public class FirebaseUtil {
    public static void addUserToDatabase(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        long createdAt = System.currentTimeMillis();

        User user = new User(email, createdAt);

        DatabaseReference spruePadDatabaseReference = FirebaseDatabase.getInstance().getReference();
        spruePadDatabaseReference.child("users").child(uid).setValue(user).addOnSuccessListener(aVoid -> {
            Log.d("Firebase", "User added to database.");
        }).addOnFailureListener(e -> {
            Log.e("Firebase", "Failed to add user: " + e.getMessage());
        });
    }

    public static void getProjectsOfUser(String uid, OnProjectsLoadedListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(uid).child("projects").get()
                .addOnCompleteListener(task -> {
                    List<Project> projects = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (DataSnapshot projectSnapshot : task.getResult().getChildren()) {
                            Project project = projectSnapshot.getValue(Project.class);
                            projects.add(project);
                        }
                        listener.onProjectsLoaded(projects);
                    } else {
                        Log.e("Firebase", "Failed to get projects: " + task.getException());
                        listener.onProjectsLoaded(projects); // return empty list
                    }
                });
    }


    public static void addProjectToDatabase(String projectName, String brand, String scale, String status, String notes) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Project project = new Project(projectName, brand, scale, status, notes);
        databaseReference.child("users").child(uid).child("projects").child(projectName).setValue(project).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Project added to database.");
            } else {
                Log.e("Firebase", "Failed to add project: " + task.getException());
            }
        });
    }
}
