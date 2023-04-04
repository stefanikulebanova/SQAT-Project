package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="search-students", urlPatterns = "/searchStudents")
//@Profile("servlet")
public class SearchStudentsServlet extends HttpServlet {
    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public SearchStudentsServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession()!=null)
            req.getSession().invalidate();
        WebContext context = new WebContext(req,resp,req.getServletContext());
        resp.setContentType("text/html");
        context.setVariable("students",new ArrayList<Student>());
        this.springTemplateEngine.process("searchStudents.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("search");
        List<Student> sl = studentService.deepSearch(text);
        WebContext context = new WebContext(req,resp,req.getServletContext());
        context.setVariable("students",sl);
        context.setVariable("courseId",req.getSession().getAttribute("chosenCourseId"));
        this.springTemplateEngine.process("listStudents.html",context,resp.getWriter());
    }
}
