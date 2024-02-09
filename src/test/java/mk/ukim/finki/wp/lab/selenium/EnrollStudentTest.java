package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EnrollStudentTest {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    private HtmlUnitDriver driver;

    private static String user = "admin";
    private static String pass = "admin";

    private static boolean dataInitialized = false;


    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();

        String url = System.getProperty("geb.build.baseUrl", "http://localhost:999/login");
        driver.get(url);
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.className("btn")).click();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    private void initData() {
        if (!dataInitialized) {
            teacherService.save("bbb", "ff");
            studentService.save("student","st1","student","ststs");
            courseService.save("name1", "desc1", 1L, Type.ELECTIVE);
            courseService.save("name2", "desc2", 1L, Type.ELECTIVE);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario1() throws Exception {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:999/courses");
        driver.get(url);
        driver.findElement(By.className("choose")).click();
        driver.findElement(By.className("student")).click();
        //driver.findElement(By.id("description")).sendKeys("desc");
        driver.findElement(By.id("submit")).click();
    }

    @Test
    public void testScenario2() throws Exception {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:999/courses");
        driver.get(url);
        driver.findElement(By.className("choose")).click();
        driver.findElement(By.id("create-student")).click();
        driver.findElement(By.id("username")).sendKeys("st");
        driver.findElement(By.id("password")).sendKeys("stpass");
        driver.findElement(By.id("name")).sendKeys("st");
        driver.findElement(By.id("surname")).sendKeys("stst");
        driver.findElement(By.id("submit")).click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "http://localhost:999/addStudent");

        driver.findElement(By.id("submit")).click();
    }

    @Test
    public void testScenario3() throws Exception {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:999/courses");
        driver.get(url);
        driver.findElement(By.className("choose")).click();
        driver.findElement(By.id("create-student")).click();
        driver.findElement(By.id("submit")).click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "http://localhost:999/createStudent");

        driver.findElement(By.id("username")).sendKeys("st");
        driver.findElement(By.id("password")).sendKeys("stpass");
        driver.findElement(By.id("name")).sendKeys("st");
        driver.findElement(By.id("surname")).sendKeys("stst");
        driver.findElement(By.id("submit")).click();

        currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "http://localhost:999/addStudent");

        driver.findElement(By.id("submit")).click();

    }



}
