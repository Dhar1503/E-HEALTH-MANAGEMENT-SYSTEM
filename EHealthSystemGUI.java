import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

// 1. Patient Class
class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String medicalHistory;

    public Patient(int id, String name, int age, String gender, String medicalHistory) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender +
                ", Medical History: " + medicalHistory;
    }
}

// 2. Appointment Class
class Appointment {
    private int id;
    private int patientId;
    private String patientName;
    private String doctorName;
    private Date appointmentDate;

    public Appointment(int id, int patientId, String patientName, String doctorName, Date appointmentDate) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + id + ", Patient ID: " + patientId + ", Patient Name: " + patientName +
                ", Doctor: " + doctorName + ", Date: " + appointmentDate;
    }
}

// 3. Appointment Manager
class AppointmentManager {
    List<Appointment> appointments = new ArrayList<>();
    private int nextId = 1;

    public void bookAppointment(Patient patient, String doctorName, Date date) {
        appointments.add(new Appointment(nextId++, patient.getId(), patient.getName(), doctorName, date));
        System.out.println("Appointment booked successfully!");
    }

    public void listAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            appointments.forEach(System.out::println);
        }
    }
}

// 4. Patient Manager Class
class PatientManager {
    private List<Patient> patients = new ArrayList<>();
    private int nextId = 1;

    public Patient addPatient(String name, int age, String gender, String medicalHistory) {
        Patient newPatient = new Patient(nextId++, name, age, gender, medicalHistory);
        patients.add(newPatient);
        return newPatient;
    }

    public void listPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            patients.forEach(System.out::println);
        }
    }

    public Patient findPatientByName(String name) {
        return patients.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

// 5. User Authentication Class
class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public String getRole() {
        return role;
    }
}

class UserManager {
    private List<User> users = new ArrayList<>();

    public UserManager() {
        // Adding default users
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("staff", "staff123", "staff"));
        users.add(new User("patient1", "patient123", "patient"));
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.authenticate(password))
                .findFirst()
                .orElse(null);
    }

    public void addUser(String username, String password, String role) {
        users.add(new User(username, password, role));
    }
}

// 6. Main Class with GUI
public class EHealthSystemGUI {
    private static UserManager userManager = new UserManager();
    private static PatientManager patientManager = new PatientManager();
    private static AppointmentManager appointmentManager = new AppointmentManager();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("E-Health Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Initial panel for login or account creation
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Welcome to the E-Health Management System", SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create New Account");

        panel.add(loginButton);
        panel.add(createAccountButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Add action listeners
        loginButton.addActionListener(e -> showLoginDialog(frame));
        createAccountButton.addActionListener(e -> showCreateAccountDialog(frame));
    }

    private static void showLoginDialog(JFrame frame) {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(frame, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loggedInUser = userManager.login(username, password);

            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(frame, "Login Successful! Role: " + loggedInUser.getRole());
                startEHealthSystem(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials. Try again or create a new user.");
            }
        }
    }

    private static void showCreateAccountDialog(JFrame frame) {
        JPanel createAccountPanel = new JPanel(new GridLayout(6, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role (admin/staff/patient):");
        JTextField roleField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();

        createAccountPanel.add(usernameLabel);
        createAccountPanel.add(usernameField);
        createAccountPanel.add(passwordLabel);
        createAccountPanel.add(passwordField);
        createAccountPanel.add(roleLabel);
        createAccountPanel.add(roleField);
        createAccountPanel.add(ageLabel);
        createAccountPanel.add(ageField);
        createAccountPanel.add(genderLabel);
        createAccountPanel.add(genderField);

        int option = JOptionPane.showConfirmDialog(frame, createAccountPanel, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();
            String ageText = ageField.getText();
            String gender = genderField.getText();

            try {
                if (role.equalsIgnoreCase("patient")) {
                    int age = Integer.parseInt(ageText);
                    String medicalHistory = JOptionPane.showInputDialog(frame, "Enter your medical history:");
                    patientManager.addPatient(username, age, gender, medicalHistory);
                }
                userManager.addUser(username, password, role);
                JOptionPane.showMessageDialog(frame, "Account created successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid details provided.");
            }
        }
    }

    private static void startEHealthSystem(JFrame frame) {
        // Define options for role-specific menu
        String[] options = "patient".equals(loggedInUser.getRole())
                ? new String[]{"View Patient Details", "Book Appointment", "View Appointments", "Exit"}
                : new String[]{"View All Patients", "View All Appointments", "Exit"};

        int choice = JOptionPane.showOptionDialog(frame, "Choose an action:", "Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            if ("patient".equals(loggedInUser.getRole())) {
                Patient loggedInPatient = patientManager.findPatientByName(loggedInUser.getUsername());
                JOptionPane.showMessageDialog(frame, loggedInPatient != null ? loggedInPatient.toString() : "Patient not found");
            } else {
                patientManager.listPatients();
            }
        } else if (choice == 1) {
            if ("patient".equals(loggedInUser.getRole())) {
                // Book an appointment
                String doctorName = JOptionPane.showInputDialog(frame, "Enter doctor's name:");
                String dateStr = JOptionPane.showInputDialog(frame, "Enter appointment date (yyyy-MM-dd):");
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    Patient loggedInPatient = patientManager.findPatientByName(loggedInUser.getUsername());
                    appointmentManager.bookAppointment(loggedInPatient, doctorName, date);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format.");
                }
            } else {
                appointmentManager.listAppointments();
            }
        } else if (choice == 2) {
            // View appointments based on user role
            if ("patient".equals(loggedInUser.getRole())) {
                Patient loggedInPatient = patientManager.findPatientByName(loggedInUser.getUsername());
                if (loggedInPatient != null) {
                    // Filter and show appointments for this patient
                    appointmentManager.appointments.stream()
                            .filter(a -> a.getPatientId() == loggedInPatient.getId())
                            .forEach(a -> JOptionPane.showMessageDialog(frame, a.toString()));
                } else {
                    JOptionPane.showMessageDialog(frame, "No appointments found for this patient.");
                }
            } else {
                appointmentManager.listAppointments(); // Staff/admin can see all appointments
            }
        } else {
            System.exit(0);
        }
    }
}

