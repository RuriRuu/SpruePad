package sprue.pad.ui.projectcontents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import sprue.pad.R;
import sprue.pad.model.Project;
import sprue.pad.ui.mainmenu.MainMenuActivity;

public class ProjectContentsActivity extends AppCompatActivity {

    private TextView projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.project_contents);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.projectContent), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Project project = (Project) getIntent().getSerializableExtra("project");
        if (project != null) {
            projectName = findViewById(R.id.project_content_name);
            projectName.setText(project.getProjectName());
        }

        ImageButton returnToMenu = findViewById(R.id.project_content_btn_back);
        returnToMenu.setOnClickListener(view -> {
            Intent intent = new Intent(ProjectContentsActivity.this, MainMenuActivity.class);
            finish();
            startActivity(intent);
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.project_content_container);

        ProjectPagerAdapter adapter = new ProjectPagerAdapter(this, project);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Tasks"); break;
                case 1: tab.setText("Paint"); break;
                case 2: tab.setText("Notes"); break;
            }
        }).attach();

    }
}