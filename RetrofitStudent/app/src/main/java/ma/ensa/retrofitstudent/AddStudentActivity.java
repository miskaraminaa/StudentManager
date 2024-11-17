package ma.ensa.retrofitstudent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ma.ensa.retrofitstudent.models.Student;
import ma.ensa.retrofitstudent.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddStudentActivity extends AppCompatActivity {

    Button btnAdd;
    EditText nom, prenom, naissance;
    private String Tag = "Student";
    private String Tag2 = "Affichage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        btnAdd = findViewById(R.id.btnAdd);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        naissance = findViewById(R.id.date);

        btnAdd.setOnClickListener(v -> createStudent());
    }

    private void createStudent() {
        Student student = new Student();
        student.setNom(nom.getText().toString());
        student.setPrenom(prenom.getText().toString());
        student.setDateNaissance(naissance.getText().toString());

        Call<Student> call = RetrofitInstance.getApi().createStudent(student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    // Affichage du Toast de succès
                    Toast.makeText(AddStudentActivity.this, "Étudiant ajouté avec succès", Toast.LENGTH_SHORT).show();
                    Log.d(Tag, response.toString());
                    getAllStudent();  // Actualiser la liste des étudiants
                } else {
                    // Si l'ajout échoue, afficher un Toast d'erreur
                    Toast.makeText(AddStudentActivity.this, "Erreur lors de l'ajout de l'étudiant.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(AddStudentActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllStudent() {
        Call<List<Student>> call = RetrofitInstance.getApi().getAllStudents();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    List<Student> students = response.body();
                    for (Student s : students) {
                        Log.d(Tag2, s.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(AddStudentActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
