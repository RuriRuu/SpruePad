package sprue.pad.ui.projectcontents.fragments.paint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import sprue.pad.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Paint_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PREFS_NAME = "PaintPrefs";
    private static final String PAINTS_KEY = "saved_paints";

    private String mParam1;
    private String mParam2;
    private LinearLayout paintsContainer;
    private List<PaintData> savedPaints;
    private ActivityResultLauncher<Intent> createPaintLauncher;

    public Paint_Fragment() {
    }

    public static Paint_Fragment newInstance(String param1, String param2) {
        Paint_Fragment fragment = new Paint_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        savedPaints = new ArrayList<>();


        createPaintLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String brand = data.getStringExtra("brand");
                        String type = data.getStringExtra("type");
                        String notes = data.getStringExtra("notes");
                        int color = data.getIntExtra("color", Color.RED);

                        PaintData newPaint = new PaintData(name, brand, type, notes, color);
                        savedPaints.add(newPaint);
                        savePaintsToPreferences();
                        addPaintCardToView(newPaint);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paint, container, false);

        ImageButton addPaint = view.findViewById(R.id.add_paint);
        paintsContainer = view.findViewById(R.id.paint_container);

        addPaint.setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), CreateColorPickerActivity.class);
            createPaintLauncher.launch(intent);
        });

        loadPaintsFromPreferences();
        displaySavedPaints();

        return view;
    }

    private void addPaintCardToView(PaintData paint) {
        View paintCard = LayoutInflater.from(getContext()).inflate(R.layout.item_paint_card, paintsContainer, false);

        TextView nameText = paintCard.findViewById(R.id.paint_name);
        TextView brandText = paintCard.findViewById(R.id.paint_brand);
        TextView typeText = paintCard.findViewById(R.id.paint_type);
        TextView notesText = paintCard.findViewById(R.id.paint_details);
        View colorPreview = paintCard.findViewById(R.id.preview_selected_color);

        nameText.setText(paint.name);
        brandText.setText(paint.brand);
        typeText.setText(paint.type);
        notesText.setText(paint.notes);
        colorPreview.setBackgroundColor(paint.color);

        paintsContainer.addView(paintCard);
    }

    private void displaySavedPaints() {
        paintsContainer.removeAllViews();
        for (PaintData paint : savedPaints) {
            addPaintCardToView(paint);
        }
    }

    private void savePaintsToPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(savedPaints);
        editor.putString(PAINTS_KEY, json);
        editor.apply();
    }

    private void loadPaintsFromPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        String json = prefs.getString(PAINTS_KEY, "");

        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<PaintData>>() {
            }.getType();
            savedPaints = gson.fromJson(json, type);
            if (savedPaints == null) {
                savedPaints = new ArrayList<>();
            }
        }
    }

    public static class PaintData {
        public String name;
        public String brand;
        public String type;
        public String notes;
        public int color;

        public PaintData(String name, String brand, String type, String notes, int color) {
            this.name = name;
            this.brand = brand;
            this.type = type;
            this.notes = notes;
            this.color = color;
        }
    }
}