package sprue.pad.ui.mainmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sprue.pad.utility.FirebaseUtil;
import sprue.pad.R;

public class CreateProjectActivity extends AppCompatActivity {

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, CreateProjectActivity.class);
        activity.startActivity(intent);
    }

    private EditText nameInput, brandInput, scaleInput, statusInput, notesInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_project);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.projectCreation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.editProjectName);
        brandInput = findViewById(R.id.editBrand);
        scaleInput = findViewById(R.id.editScale);
        statusInput = findViewById(R.id.editStatus);
        notesInput = findViewById(R.id.editDescription);

        ImageButton cancelProjectCreation = findViewById(R.id.cancel_creation_button);
        cancelProjectCreation.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        Button saveBtn = findViewById(R.id.btnSaveProject);
        saveBtn.setOnClickListener(view -> {
            createProject();
        });
    }

    private void createProject() {
        String name = nameInput.getText().toString().trim();
        String brand = brandInput.getText().toString().trim();
        String scale = scaleInput.getText().toString().trim();
        String status = statusInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Project name required");
            nameInput.requestFocus();
            return;
        }

        FirebaseUtil.addProjectToDatabase(name, brand, scale, status, notes);

        Intent data = new Intent();
        data.putExtra("name", name);
        data.putExtra("brand", brand);
        data.putExtra("scale", scale);
        data.putExtra("status", status);
        data.putExtra("notes", notes);
        setResult(RESULT_OK, data);
        Intent intent = new Intent(CreateProjectActivity.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
