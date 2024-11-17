package com.example.student_manager.repositories;


import com.example.student_manager.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    //recherche d'un étudiant par son identifiant
    Student findById(int id);

    //Requete personnalisée pour compter les étudiants par année de naissance
    @Query("SELECT YEAR(s.dateNaissance), COUNT(s) FROM Student s GROUP BY YEAR(s.dateNaissance)")
    Collection<Object[]> findNbrStudentByYear();
}
