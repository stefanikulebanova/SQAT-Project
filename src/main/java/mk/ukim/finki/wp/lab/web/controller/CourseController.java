package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameExistsException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GradeService gradeService;

    public CourseController(CourseService courseService, StudentService studentService, TeacherService teacherService, GradeService gradeService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = this.courseService.listCourses().stream()
                .sorted(Comparator.comparing(Course::getName))
                .collect(Collectors.toList());
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long teacherId,
                             @RequestParam(required = false) Long courseId,
                             @RequestParam Type type) {
        if(courseId == null){
            try {
                this.courseService.save(name, description, teacherId, type);
            } catch (CourseNameExistsException e) {
                return "redirect:/courses/add-form?error="+e.getMessage();
            }
        }
        else{
            this.courseService.editCourse(name, description, teacherId, courseId, type);
        }
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }

    @PostMapping("/chooseCourse")
    public String chosenCourse(HttpServletRequest request, Model model){
        String courseId = request.getParameter("courseId");
        request.getSession().setAttribute("chosenCourseId",courseId);
        model.addAttribute("students",this.studentService.listAll());
        return "redirect:/addStudent";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model){
        if(this.courseService.getCourseById(String.valueOf(id)).isPresent()) {
            Course c = this.courseService.getCourseById(String.valueOf(id)).get();
            model.addAttribute("course", c);
            model.addAttribute("teachers",this.teacherService.findAll());
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model, @RequestParam(required = false) String error){
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("error", error);

        return "add-course";
    }

    @GetMapping("/search-all")
    public String getSearchPage(Model model){
        List<Course> c = courseService.listCourses();
        List<Student> s = studentService.listAll();
        model.addAttribute("courses",c);
        model.addAttribute("students",s);
        return "search-all";
    }

    @PostMapping("/search-all")
    public String search(@RequestParam(required=false) String search, Model model){
        List<Course> c = courseService.deepSearch(search);
        List<Student> s = studentService.deepSearch(search);
        model.addAttribute("courses",c);
        model.addAttribute("students",s);
        return "search-all";
    }

    @PostMapping("/addGrade/{id}")
    public String addGradePage(@RequestParam String name,
                               @RequestParam String id,
                               @RequestParam Character grade,
                               @RequestParam String datetime,
                               HttpServletRequest request){
        String courseId = (String) request.getSession().getAttribute("chosenCourseId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
        gradeService.addGrade(courseId, id, grade, dateTime);
           return "redirect:/list";
    }

}
