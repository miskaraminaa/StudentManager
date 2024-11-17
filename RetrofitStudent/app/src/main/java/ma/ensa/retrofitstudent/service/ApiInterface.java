package ma.ensa.retrofitstudent.service;

import java.util.List;

import ma.ensa.retrofitstudent.models.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    // Récupérer tous les étudiants
    @GET("students/all")  // Corrected path
    Call<List<Student>> getAllStudents();

    // Créer un nouvel étudiant
    @POST("students/save")  // Corrected path
    Call<Student> createStudent(@Body Student student);

    // Supprimer un étudiant par ID
    @DELETE("students/delete/{id}")  // Corrected path
    Call<Void> deleteStudent(@Path("id") int id);
}
