package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    List<Student> students;

    InMemoryStudentRepository(){}
    public List<Student> findAllStudents(){
        return DataHolder.students;
    }
    public List<Student> findAllByNameOrSurname(String text){
      return DataHolder.students.stream().filter(st -> st.getName().contains(text) || st.getSurname().contains(text))
               .collect(Collectors.toList());
    }

    public Optional<Student> findByUsernameAndPassword(String username, String password){
        return DataHolder.students.stream().filter(st -> st.getUsername().equals(username) && st.getPassword().equals(password)).findFirst();
    }
    public Optional<Student> findByUsername(String username){
        return DataHolder.students.stream().filter(st -> st.getUsername().equals(username)).findFirst();
    }
    public Student create(String username, String password, String name, String surname){
        DataHolder.students.removeIf(r->r.getUsername().equals(username));
        Student s = new Student(username,password,name,surname);
        DataHolder.students.add(s);
        return s;
    }

    public void delete(String username) {
        DataHolder.students.removeIf(r->r.getUsername().equals(username));
    }

    public List<Student> deepSearch(String search) {
        List<Student> ss = DataHolder.students.stream()
                .filter(s -> s.getUsername().contains(search) || s.getName().contains(search) || s.getSurname().contains(search))
                .collect(Collectors.toList());
        return ss;
    }
}
