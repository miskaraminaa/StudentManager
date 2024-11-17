package ma.ensa.retrofitstudent;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import java.util.List;

import ma.ensa.retrofitstudent.adapters.StudentAdapter;
import ma.ensa.retrofitstudent.models.Student;
import ma.ensa.retrofitstudent.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
public class ListStudentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> students = new ArrayList<>();  // Assurez-vous que cette liste est mutable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);

        recyclerView = findViewById(R.id.list);

        // Initialiser le RecyclerView avec un LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Récupérer la liste des étudiants et la mettre à jour
        getAllStudent();
    }

    private void getAllStudent() {
        // Appel à l'API pour récupérer tous les étudiants
        Call<List<Student>> call = RetrofitInstance.getApi().getAllStudents();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    List<Student> fetchedStudents = response.body();
                    if (fetchedStudents != null && !fetchedStudents.isEmpty()) {
                        students.clear();  // Effacer la liste actuelle
                        students.addAll(fetchedStudents);  // Ajouter les étudiants récupérés
                        adapter = new StudentAdapter(students, ListStudentActivity.this);
                        recyclerView.setAdapter(adapter);

                        // Ajouter un ItemTouchHelper pour gérer le swipe
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                            @Override
                            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                return false; // Pas de mouvement
                            }

                            @Override
                            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                int position = viewHolder.getAdapterPosition();
                                Student student = students.get(position);

                                // Appeler l'API pour supprimer l'étudiant
                                deleteStudent(student.getId(), position);
                            }
                        });

                        itemTouchHelper.attachToRecyclerView(recyclerView);
                    } else {
                        // Si la liste des étudiants est vide
                        Toast.makeText(ListStudentActivity.this, "Aucun étudiant trouvé.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la réponse de l'API échoue
                    Toast.makeText(ListStudentActivity.this, "Erreur lors de la récupération des étudiants.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                // Si la requête échoue
                Toast.makeText(ListStudentActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteStudent(int studentId, int position) {
        // Appel à l'API pour supprimer l'étudiant
        Call<Void> call = RetrofitInstance.getApi().deleteStudent(studentId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la suppression a réussi, supprimer l'étudiant de la liste et mettre à jour l'adaptateur
                    students.remove(position);  // Supprimer l'étudiant de la liste
                    adapter.notifyItemRemoved(position);  // Notifier l'adaptateur de la suppression
                    Toast.makeText(ListStudentActivity.this, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListStudentActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ListStudentActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
