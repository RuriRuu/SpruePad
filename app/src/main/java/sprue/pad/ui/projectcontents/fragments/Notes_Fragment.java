package sprue.pad.ui.projectcontents.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import sprue.pad.model.Project;
import sprue.pad.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notes_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notes_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Project project;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Notes_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notes_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Notes_Fragment newInstance(String param1, String param2) {
        Notes_Fragment fragment = new Notes_Fragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        Bundle args = getArguments();
        if (args != null) {
            project = (Project) args.getSerializable("project");

            if (project != null) {
                TextView brandName = view.findViewById(R.id.notes_project_brand);
                brandName.setText(project.getBrand());
            }
        }

        return view;
    }
}