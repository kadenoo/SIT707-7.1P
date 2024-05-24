package sit707_week7.sample;

public class StudentReader {

    // Database service for Student info
    private StudentRepository studentRepository;

    // Email service, external communication
    private EmailSender emailSender;

    public String findFullName(Long studentID) {
        Student user = studentRepository.findByID(studentID);
        return user.getFirstName() + " " + user.getLastName();
    }

    public Long createNew(Student student) {
        return studentRepository.save(student);
    }

    public void notifyStudent(Student student) {
        emailSender.sendEmail(student);
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
}
