package vn.edu.hcmut.cse.adsoftweng.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Luu y: su dung @Controller, KHONG dung @RestController
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.hcmut.cse.adsoftweng.lab.service.StudentService;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {
    @Autowired
    private StudentService service;

    // Route: GET http://localhost:8080/students
    @GetMapping
    public String getAllStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.isEmpty()) {
            students = service.searchByName(keyword);
        } else {
            students = service.getAll();
        }
        model.addAttribute("dsSinhVien", students);
        return "students";
    }

    // Route: GET http://localhost:8080/students/{id}
    @GetMapping("/{id}")
    public String getStudentDetail(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student);
        return "student-detail";
    }

    // Route: POST http://localhost:8080/students/{id}/delete
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/students";
    }

    // Route: GET http://localhost:8080/students/new
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    // Route: POST http://localhost:8080/students/new
    @PostMapping("/new")
    public String createStudent(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students";
    }

    // Route: GET http://localhost:8080/students/{id}/edit
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student);
        return "student-form";
    }

    // Route: POST http://localhost:8080/students/{id}/edit
    @PostMapping("/{id}/edit")
    public String updateStudent(@PathVariable String id, @ModelAttribute Student student) {
        student.setId(id); // Ensure the ID is set correctly
        service.update(student);
        return "redirect:/students";
    }
}
