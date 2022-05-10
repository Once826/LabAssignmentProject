package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest extends TestCase {

    static Service service;

    public void setUp() throws Exception {
        super.setUp();
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @BeforeAll
    public static void testSaveStudent() {
        String id = "1";
        String name = "Pisti";
        int group = 531;
        assertEquals(1, service.saveStudent(id, name, group));
    }

    @Test
    public void testSaveStudentWithEmptyString() {
        String id = "99";
        String name = "Jani";
        int group = 532;
        Assertions.assertAll(
                () -> assertEquals(1, service.saveStudent(id, name, 10)),
                () -> assertEquals(1, service.saveStudent(id, "", group)),
                () -> assertEquals(1, service.saveStudent("", name, group))
        );
    }

    @Test
    public void testSaveHomework() {
        assertNotNull(service.saveHomework("1", "dve", 12, 1));
    }

    @AfterAll
    public static void testDeleteStudent() {
        Assertions.assertNotEquals(0, service.deleteStudent("1"));
    }

    public void testUpdateStudent() {
        Assertions.assertAll(
                () -> assertEquals(1, service.updateStudent("0", "Snyi", 543)),
                () -> assertEquals(0, service.updateStudent("", "", 10))
        );
    }
}