package sprue.pad;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadAndWriteSnippets {

    public static final String TAG = "ReadAndWriteSnippets";
    private DatabaseReference spruePadDatabase;
    String spruePadDatabaseURL = "https://spruepad-default-rtdb.asia-southeast1.firebasedatabase.app/";

    public ReadAndWriteSnippets(DatabaseReference database) {
        database = FirebaseDatabase.getInstance().getReference(spruePadDatabaseURL);
    }

    public void writeNewUser(String userID, String name, String email) {
        User user = new User(name, email);
        spruePadDatabase.child("users").child(userID).setValue(user);
    }

    public void writeNewUserWithTaskListeners(String userId, String name, String email) {
        User user = new User(name, email);

        spruePadDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void nothing) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

}
