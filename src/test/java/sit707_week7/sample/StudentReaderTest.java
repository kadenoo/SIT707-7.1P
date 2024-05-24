package sit707_week7.sample;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class StudentReaderTest {

    @Test
    public void testFindStudent() {
        Student sampleStudent = new Student();
        sampleStudent.setFirstName("Ahsan");
        sampleStudent.setLastName("Habib");

        StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
        Mockito.when(studentRepository.findByID(1L)).thenReturn(sampleStudent);

        StudentReader studentReader = new StudentReader();
        studentReader.setStudentRepository(studentRepository);

        String fullName = studentReader.findFullName(1L);
        Assert.assertEquals("Ahsan Habib", fullName);
    }

    @Test
    public void testStudentRepoSave() {
        Student sampleStudent = new Student();
        sampleStudent.setFirstName("Ahsan");
        sampleStudent.setLastName("Habib");

        StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
        Mockito.when(studentRepository.save(sampleStudent)).thenReturn(2L);

        StudentReader studentReader = new StudentReader();
        studentReader.setStudentRepository(studentRepository);

        Long savedID = studentReader.createNew(sampleStudent);
        Assert.assertEquals(Long.valueOf(2), savedID);
    }

    @Test
    public void testEmailSend() {
        Student sampleStudent = new Student();
        sampleStudent.setFirstName("Ahsan");
        sampleStudent.setLastName("Habib");

        EmailSender emailSender = Mockito.mock(EmailSender.class);

        StudentReader studentReader = new StudentReader();
        studentReader.setEmailSender(emailSender);
        studentReader.notifyStudent(sampleStudent);

        Mockito.verify(emailSender).sendEmail(sampleStudent);
    }

    @Test
    public void testEmailNoSend() {
        Student sampleStudent = new Student();
        sampleStudent.setFirstName("Ahsan");
        sampleStudent.setLastName("Habib");

        EmailSender emailSender = Mockito.mock(EmailSender.class);

        StudentReader studentReader = new StudentReader();
        studentReader.setEmailSender(emailSender);

        Mockito.verify(emailSender, Mockito.times(0)).sendEmail(sampleStudent);
    }
}
