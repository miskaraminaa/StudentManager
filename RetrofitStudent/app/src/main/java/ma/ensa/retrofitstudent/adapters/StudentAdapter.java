package ma.ensa.retrofitstudent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ensa.retrofitstudent.R;
import ma.ensa.retrofitstudent.models.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students;
    private Context context;

    public StudentAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflater pour gonfler le layout de l'élément de la liste
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        // Récupérer l'étudiant à la position donnée et afficher ses informations
        Student student = students.get(position);
        holder.nom.setText(student.getNom());
        holder.prenom.setText(student.getPrenom());
        holder.dateNaissance.setText(student.getDateNaissance());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    // Méthode pour supprimer un étudiant de la liste
    public void removeStudent(int position) {
        students.remove(position);
        notifyItemRemoved(position);  // Utilisation de la méthode correcte pour la suppression
    }

    // ViewHolder qui maintient la référence des vues pour chaque élément
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nom, prenom, dateNaissance;

        public StudentViewHolder(View itemView) {
            super(itemView);
            // Initialisation des vues
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            dateNaissance = itemView.findViewById(R.id.date);
        }
    }
}
