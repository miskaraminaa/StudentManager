package ma.ensa.retrofitstudent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShowList, btnRefresh;
    EditText nom, prenom, dateNaissance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnShowList = findViewById(R.id.btnShowList);


        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        btnShowList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListStudentActivity.class);
            startActivity(intent);
        });


    }

}
