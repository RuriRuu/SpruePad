package sprue.pad.ui.mainmenu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import sprue.pad.R;
import sprue.pad.ui.LoginActivity;
import sprue.pad.utility.FirebaseUtil;


public class MainMenuActivity extends AppCompatActivity {
    private ImageButton SignOutButton;
    private MainMenuRVAdapter MainMenuRVAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enableSignOutButton();
        enableAddProjectButton();

        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        FirebaseUtil.getProjectsOfUser(currentUserID, projects -> {
            MainMenuRVAdapter = new MainMenuRVAdapter(projects, this);
            RecyclerView projectContainer = findViewById(R.id.project_container);
            projectContainer.setAdapter(MainMenuRVAdapter);
            projectContainer.setLayoutManager(new GridLayoutManager(this
                    , 2, LinearLayoutManager.VERTICAL, false));
        });
    }

    public void enableAddProjectButton() {
        ExtendedFloatingActionButton addProject = findViewById(R.id.fab_add_project);

        addProject.setOnClickListener(view -> {
            CreateProjectActivity.launch(MainMenuActivity.this);
            finish();
        });
    }

    public void enableSignOutButton() {
        SignOutButton = findViewById(R.id.signOutButton);
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutConfirmation();
            }
        });
    }

    public void signOutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Action");
        builder.setMessage("Are you sure you want to proceed?");
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        builder.setPositiveButton("Yes", (dialog, which) -> {
            mAuth.signOut();
            Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
