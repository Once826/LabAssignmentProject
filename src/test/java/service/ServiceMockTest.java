package service;

import domain.Student;
import org.junit.jupiter.api.*;
import org.mockito.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class ServiceMockTest {
    Service service;


    @Mock
    StudentXMLRepository fileRepository1;
    @Mock
    HomeworkXMLRepository fileRepository2;
    @Mock
    GradeXMLRepository fileRepository3;

    @Captor
    ArgumentCaptor argumentCaptor;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testSaveStudent() {
        when(fileRepository1.save(any())).thenReturn(null);
        assertEquals(1, service.saveStudent("18", "Fre", 467));
    }

    @Test
    public void testDeleteStudent() {
        Service mockService = mock(Service.class);

        mockService.deleteStudent("4");
        verify(mockService).deleteStudent((String) argumentCaptor.capture());
        assertEquals("4", argumentCaptor.getValue());
    }

    @Test
    public void testUpdateStudent() {
        Service mockService = mock(Service.class);
        Assertions.assertAll(
                () -> assertEquals(0, service.updateStudent("0", "Snyi", 543)),
                () -> assertEquals(0, service.updateStudent("", "", 10))
        );
        verify(service, atLeast(2)).updateStudent(anyString(), anyString(), anyInt());
    }
}
