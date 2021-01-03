package practiceclient.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import practiceclient.Student;

@Controller
@RequestMapping("/student")
public class StudentController {
    private RestTemplate rest = new RestTemplate();
    @GetMapping
    public String showStudentManaging(Model model){
        model.addAttribute("page", "Quản lý sinh viên");
        return "student.html";
    }

    @GetMapping("/findStudent")
    public String showFindStudent(Model model, ServletRequest request){
        List<Student> listStudent = Arrays.asList(rest.getForObject("http://localhost:8080/student/findStudent", Student[].class));
        model.addAttribute("student", new Student());
        model.addAttribute("students", listStudent);
        return "findStudent";
    }
    
    @PostMapping("/findStudent")
    public String findStudentProcess(Model model, ServletRequest request, @RequestParam(name="name") String name){
        List<Student> listStudent = Arrays.asList(rest.getForObject("http://localhost:8080/student/findStudent/{name}", Student[].class,name));
        model.addAttribute("student", new Student());
        model.addAttribute("students", listStudent);
        return "findStudent";
    }

    @GetMapping("/addStudent")
    public String showAddStudent(Model model, ServletRequest request){
        model.addAttribute("page", "Thêm sinh viên");
        return "addStudent";
    }

    @PostMapping("/addStudent")
    public String addStudentProcess(Model model, ServletRequest request, Student student){
        try{
//            System.out.println(student.getName());
//            System.out.println(student.getStudentId());
//            System.out.println(student.getDOB());
//            System.out.println(student.getAddress());
            rest.postForObject("http://localhost:8080/student/addStudent", student,Student.class);
        }
        catch(Exception e){
            return "redirect:/student/addStudent?error";
        }
        return "redirect:/student/findStudent?add";
    }

    @GetMapping("/editStudent")
    public String showEditStudent(Model model, @RequestParam(name = "id") int id){
        try{
            Student student = rest.getForObject("http://localhost:8080/student/editStudent/{id}", Student.class, id);
            model.addAttribute("student", student);
            model.addAttribute("page", "Sửa thông tin sinh viên");
            return "editStudent";
        }
        catch(Exception e){
            return "redirect:/student/findStudent?error";
        }
    }

    @PostMapping("/editStudent")
    public String editStudentProcess(Model model, Student student){
        try{
            rest.postForObject("http://localhost:8080/student/editStudent", student, Student.class);
            return "redirect:/student/findStudent?edit";
        }
        catch(Exception e){
            return "redirect:/student/findStudent?errorEdit";
        }
    }

    @GetMapping("/deleteStudent")
    public String deleteStudentProcess(Model model, @RequestParam("id") int id){
        try{
            System.out.println(id);
            rest.delete("http://localhost:8080/student/deleteStudent/{id}", id);
            // rest.postForObject("http://localhost:8080/student/deleteStudent/{id}", id, Integer.class);
            return "redirect:/student/findStudent?delete";
        }
        catch(Exception e){
            return "redirect:/student/findStudent?errorDelete";
        }
    }
}
