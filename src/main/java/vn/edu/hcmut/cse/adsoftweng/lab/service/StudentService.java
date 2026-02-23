package vn.edu.hcmut.cse.adsoftweng.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import vn.edu.hcmut.cse.adsoftweng.lab.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Student> searchByName(String name) {
        // Lay tat ca sinh vien va loc theo substring cuoi cung cua ten
        return repository.findAll().stream()
            .filter(student -> {
                String fullName = student.getName();
                if (fullName == null) return false;
                // Tach cac substring theo khoang trang
                String[] parts = fullName.trim().split("\\s+");
                // Lay substring cuoi cung (ho)
                String lastName = parts[parts.length - 1];
                // Kiem tra co chua searchTerm khong (khong phan biet hoa/thuong)
                return lastName.toLowerCase().contains(name.toLowerCase());
            })
            .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Student save(Student student) {
        return repository.save(student);
    }

    public Student update(Student student) {
        return repository.save(student);
    }
}
