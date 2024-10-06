package com.hemnath.todo.controller;

import com.hemnath.todo.exception.UserNotFoundException;
import com.hemnath.todo.model.Student;
import com.hemnath.todo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@CrossOrigin("http://localhost:3000/")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/user")
    public Student saveStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    @GetMapping("/users")
    public List<Student> saveStudent(){
        return studentRepository.findAll();
    }

    @PostMapping("/allUser")
    public List<Student> saveStudent(@RequestBody List<Student> student){
        return studentRepository.saveAll(student);
    }

    @GetMapping("/user/{id}")
    public Student getStudentById(@PathVariable Long id){
        return studentRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    public Student updateStudent(@RequestBody Student newStudent,@PathVariable Long id){
        return studentRepository.findById(id)
                .map(student -> {
                    student.setId(newStudent.getId());
                    student.setName(newStudent.getName());
                    student.setStandard(newStudent.getStandard());
                    student.setEmail(newStudent.getEmail());
                    student.setAge(newStudent.getAge());
                    return studentRepository.save(student);
                }) .orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteStudent(@PathVariable Long id){
        if(!studentRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        studentRepository.deleteById(id);
        return "Student id " + id + " is deleted";
    }
}
