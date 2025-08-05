package sprue.pad.ui.projectcontents.fragments.paint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import sprue.pad.R;


public class CreateColorPickerActivity extends AppCompatActivity {

    private EditText nameInput, brandInput, typeInput, notesInput;
    private int mDefaultColor;
    private View mColorPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_paint_picker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createPaintTracker), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.createPaintName);
        brandInput = findViewById(R.id.createBrandName);
        typeInput= findViewById(R.id.createPaintType);
        notesInput = findViewById(R.id.createPaintNote);

        Button setPaint = findViewById(R.id.createPaintSet);
        CardView colorPicker = findViewById(R.id.createColorPicker);
        mColorPreview = findViewById(R.id.create_preview_selected_color);
        mDefaultColor = 0;

        colorPicker.setOnClickListener(view ->
                ColorPickerDialogBuilder
                        .with(CreateColorPickerActivity.this)
                        .setTitle("Choose color")
                        .initialColor(Color.RED)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> {
                        })
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                            mDefaultColor = selectedColor;
                            mColorPreview.setBackgroundColor(mDefaultColor);
                        })
                        .setNegativeButton("cancel", (dialog, which) -> {})
                        .build()
                        .show()
        );
        setPaint.setOnClickListener(view -> createPaintCard());
    }

    private void createPaintCard() {
        String name = nameInput.getText().toString().trim();
        String brand = brandInput.getText().toString().trim();
        String type = typeInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Paint name required");
            nameInput.requestFocus();
            return;
        }

        if (mDefaultColor == 0) {
            mDefaultColor = Color.RED;
        }

        Intent data = new Intent();
        data.putExtra("name", name);
        data.putExtra("brand", brand);
        data.putExtra("type", type);
        data.putExtra("notes", notes);
        data.putExtra("color", mDefaultColor);
        setResult(RESULT_OK, data);
        finish();
    }
}