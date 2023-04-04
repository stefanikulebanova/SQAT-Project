package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="summary-servlet", urlPatterns = "/list")
//@Profile("servlet")
public class StudentEnrollmentSummary extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final GradeService gradeService;

    public StudentEnrollmentSummary(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeService gradeService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String courseId = req.getSession().getAttribute("chosenCourseId").toString();
        resp.setContentType("text/html");
        Course c=null;
        if(courseService.getCourseById(courseId).isPresent())
           c = courseService.getCourseById(courseId).get();
        String name = courseService.getCourseById(courseId).get().getName();
        context.setVariable("course", name);
        List<Student> s = courseService.listStudentsByCourse(Long.valueOf(courseId));
        context.setVariable("students", s);
        List<Grade> gs = gradeService.getAllByCourseId(Long.valueOf(courseId));
        context.setVariable("grades", gs);
        this.springTemplateEngine.process("studentsInCourse.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}