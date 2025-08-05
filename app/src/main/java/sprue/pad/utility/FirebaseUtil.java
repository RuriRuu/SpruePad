package sprue.pad.utility;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    public static void addProjectToDatabase(String projectName, String brand, String scale, String status, String notes) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Project project = new Project(projectName, brand, scale, status, notes);
//        databaseReference.child("users").child(uid).child("projects").child(projectName).setValue(project).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Log.d("Firebase", "Project added to database.");
//            } else {
//                Log.e("Firebase", "Failed to add project: " + task.getException());
//            }
//        });
//    }

    public static void addProjectToDatabase(Project project) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child(uid).child("projects").child(project.getProjectName()).setValue(project).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Project added to database.");
            } else {
                Log.e("Firebase", "Failed to add project: " + task.getException());
            }
        });
    }

    public static void getProjectsOfUser(String uid, OnProjectsLoadedListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(uid).child("projects").get().addOnCompleteListener(task -> {
            List<Project> projects = new ArrayList<>();
            if (task.isSuccessful()) {
                for (DataSnapshot projectSnapshot : task.getResult().getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    projects.add(project);
                }
                listener.onProjectsLoaded(projects);
            } else {
                Log.e("Firebase", "Failed to get projects: " + task.getException());
                listener.onProjectsLoaded(projects);
            }
        });
    }

    public static void deleteProject(String projectName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("users").child(firebaseUser.getUid()).child("projects").child(projectName).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Project deleted from database.");
            } else {
                Log.e("Firebase", "Failed to delete project: " + task.getException());
            }
        });
    }

    public static void updateProject(Project project) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void addTasktoProject(String projectName, String task) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("users").child(firebaseUser.getUid()).child("projects").child(projectName).child("Tasks").push().setValue(task).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Log.d("Firebase", "Task added to database.");
            } else {
                Log.e("Firebase", "Failed to add task: " + task1.getException());
            }
        });
    }

    public static Map<String, String> getTasks(String projectName, Taskscallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure(new IllegalStateException("No authenticated user"));
            return null;
        }

        databaseReference.child("users").child(firebaseUser.getUid()).child("projects").child(projectName).child("Tasks").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, String> tasks = new HashMap<>();
                DataSnapshot result = task.getResult();
                if (result != null) {
                    for (DataSnapshot taskSnapshot : result.getChildren()) {
                        String taskName = taskSnapshot.getValue(String.class);
                        tasks.put(taskSnapshot.getKey(), taskName);
                    }
                }
                Log.d("FirebaseData", "ProjectTasks (inside callback): " + tasks.keySet());
                callback.onSuccess(tasks);
            } else {
                Exception e = task.getException() != null ? task.getException() : new RuntimeException("Unknown error fetching tasks");
                Log.e("Firebase", "Failed to get tasks: ", e);
                callback.onFailure(e);
            }
        });
        return null;
    }


    public static void deleteTaskFromProject(String projectName, String task) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("users").child(firebaseUser.getUid()).child("projects").child(projectName).child("Tasks").child(task).removeValue().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Log.d("Firebase", "Task deleted from database.");
            } else {
                Log.e("Firebase", "Failed to delete task: " + task1.getException());
            }
        });
    }
}