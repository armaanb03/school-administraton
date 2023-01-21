import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class for the CSD
 */
public class CSD {
    protected static int employeeID = 99;   // starting values for employee and student IDs
    protected static int studentID = 999;

    private ChairPerson chair;  // chair person of the CSD

    private int numProgramDirectors;    // integer values for number of program directors, faculty, ugrads, and grads
    private int numFaculty;
    private int numUGrad;
    private int numGrad;

    protected List<ProgramDirector> directors;  // Lists to hold all associated program directors, faculty, ugrads, and grads in the CSD
    protected List<Faculty> employedFaculty;
    protected List<UGrad> students;
    protected List<Grad> hiredTAs;

    /**
     * This method is a constructor for the CS Department, it instantiates multiple counters and lists to hold
     * parts and roles of the CSD (directors, faculty, ugrad students, and grad students)
     * @param chair is the chair of the CSD being instantiated
     */
    public CSD(ChairPerson chair) {
        this.chair = chair; // initializing all instance variables that are initialized upon construction
        this.numProgramDirectors = 0;
        this.numUGrad = 0;
        this.numFaculty = 0;
        this.numGrad = 0;
        directors = new ArrayList<>();
        employedFaculty = new ArrayList<>();
        students = new ArrayList<>();
        hiredTAs = new ArrayList<>();
    }   // end CSD constructor

    /**
     * This method is a getter for the chair member of the CSD
     * @return it returns the chair member of the CSD
     */
    public ChairPerson getChairPerson() {
        return chair;
    }   // end getChairPerson

    /**
     * This method is a getter for the number of Faculty members in the CSD
     * @return it returns the number of Faculty members in the CSD
     */
    public int getNumOfFaculty() {
        return numFaculty;
    }   // end getNumOfFaculty

    /**
     * This method is a getter for the number of UGrad students in the CSD
     * @return it returns the number of UGrad students in the CSD
     */
    public int getNumOfUGradStudents() {
        return numUGrad;
    }   // end getNumOfGradStudents

    /**
     * This method is a getter for the number of Grad students in the CSD
     * @return it returns the number of Grad students in the CSD
     */
    public int getNumOfGradStudents() {
        return numGrad;
    }   // end getNumOfGradStudents

    /**
     * This method adds a program director to the CSD
     * @param director is the director being added to the CSD
     * @throws NoSpaceException if there is no space for the ProgramDirector
     */
    public void addProgramDirector(ProgramDirector director) throws NoSpaceException {
        boolean unique = true;  // boolean value that is changed if the added ProgramDirector is not unique

        for (int i = 0; i < this.directors.size(); i++) {
            if (directors.get(i) == director) { // check if one of the directors in the CSD is equal to the input
                unique = false;
            }
        }

        if (unique && numProgramDirectors < 3) {    // if it is unique and there is space, add the director
            directors.add(director);
        } else if (numProgramDirectors >= 3) {  // if there are already 3 or more directors, throw NoSpaceException
            throw new NoSpaceException();
        }
    }   // end addProgramDirector

    /**
     * This method hires a Faculty member into the CSD
     * @param f is the Faculty member that is being added to the CSD
     * @throws NoSpaceException is thrown if there is no space for the Faculty member being added
     */
    public void HireFaculty(Faculty f) throws NoSpaceException {
        boolean unique = true;  // boolean value that is changed if the added Faculty is not unique

        for (int i = 0; i < employedFaculty.size(); i++) {  // check if one of the Faculty in the CSD is equal to the input
            if (employedFaculty.get(i).equals(f)) {
                unique = false;
            }
        }

        if (unique && numFaculty < 70) {    // if it is unique and there is space, add it and assign it to a director in the CSD
            employedFaculty.add(f);
            numFaculty++;
            if (numProgramDirectors > 0) {
                int i = 0;
                boolean notAdded = true;

                while (notAdded && i < directors.size()) {
                    if (directors.get(i).getNumFaculty() < 25) {
                        directors.get(i).addFaculty(f);
                        f.setDirector(directors.get(i));
                        notAdded = false;
                    }
                    i++;
                }
            }
        } else if (numFaculty >= 70) {  // if there is no space, throw a NoSpaceException
            throw new NoSpaceException();
        }
    }   // end HireFaculty

    /**
     * This method is a getter for the Faculty members in the CSD
     * @return it returns a List of the Faculty members in the CSD
     */
    public List<Faculty> getFaculty() {
        return this.employedFaculty;
    }   // end getFaculty

    /**
     * This method admits a UGrad student to the CSD
     * @param g is the student that is being admitted
     * @throws NoSpaceException is thrown if there is no more space for the student
     */
    public void AdmitStudent(UGrad g) throws NoSpaceException {
        boolean unique = true;  // boolean value that becomes false if the UGrad being added is not unique

        for (int i = 0; i < students.size(); i++) { // check if any UGrads in the CSD are equal to the input
            if (students.get(i).equals(g)) {
                unique = false;
            }
        }

        if (unique && numUGrad < 500) { // if it is unique and there is space, add it to the CSD and assign it to a faculty
            students.add(g);
            if (numFaculty > 0) {
                int i = 0;
                boolean notAdded = true;

                while (notAdded && i < employedFaculty.size()) {
                    if (employedFaculty.get(i).getNumOfAdvisingUGrads() < 8) {
                        employedFaculty.get(i).addUGrad(g);
                        g.setAdvisor(employedFaculty.get(i));
                        notAdded = false;
                        numUGrad++;
                    }
                    i++;
                }
            } else if (numUGrad >= 500) {   // if there is no space, throw a NoSpaceException
                throw new NoSpaceException();
            }
        }
    }   // end AdmitStudent

    /**
     * This method hires a TA and adds them to a faculty with space
     * @param ta is the Grad object that gets hired as a TA
     * @throws NoSpaceException is thrown if there is no space for this grad in the CSD
     */
    public void HireTA(Grad ta) throws NoSpaceException {
        boolean unique = true;  // boolean value that becomes false if the TA being hired is not unique

        for (int i = 0; i < hiredTAs.size(); i++) { // check if the TA being hired is equal to any already hired TAs
            if (hiredTAs.get(i).equals(ta)) {
                unique = false;
            }
        }

        if (unique && numGrad < 150) {  // if it is unique and there is space, add it to the CSD and assign it to a faculty
            hiredTAs.add(ta);
            if (numFaculty > 0) {
                int i = 0;
                boolean notAdded = true;

                while (notAdded && i < employedFaculty.size()) {
                    if (employedFaculty.get(i).getNumOfTAs() < 5) {
                        employedFaculty.get(i).addGrad(ta);
                        ta.setAdvisor(employedFaculty.get(i));
                        notAdded = false;
                        numGrad++;
                    }
                    i++;
                }
            } else if (numGrad >= 150) {    // if there is no space, throw a NoSpaceException
                throw new NoSpaceException();
            }
        }
    }   // end HireTA

    /**
     * This method graduates and removes an undergrad student from the CSD
     * @param g is the UGrad student that is being removed
     */
    public void AlumnusUGrad(UGrad g) {
        students.remove(g); // remove the student from the CSD

        g.getAdvisor().getAdvisingUgrads().remove(g);   // remove the student from the advisees of its faculty and increment the number of advisees for it
        g.getAdvisor().setNumUGrads(g.getAdvisor().getNumOfAdvisingUGrads() - 1);

        numUGrad--; // increment the numUGrad in the CSD
    }   // end AlumnusUGrad

    /**
     * This method graduates and removes a grad student from the CSD
     * @param ta is the Grad/TA that is being removed
     * @throws NoTAException is thrown in the case that the faculty this TA was under no longer has any TAs under them
     * after this TA is removed
     */
    public void AlumnusGrad(Grad ta) throws NoTAException {
        hiredTAs.remove(ta);    // remove the TA from the CSD

        ta.getAdvisor().getTAs().remove(ta);    // remove the TA from its faculty and reduce the number of them in the faculty
        ta.getAdvisor().setNumTAs(ta.getAdvisor().getNumOfTAs() - 1);

        numGrad--;  // increment the numGrad in the CSD

        if (ta.getAdvisor().getNumOfTAs() == 0) {   // if there are no TAs under its previous faculty, throw a NoTAException
            throw new NoTAException();
        }
    }   // end AlumnusGrad

    /**
     * This method extracts all the UGrad students and their details
     * @return it returns a List of UGrad of the students in the CSD sorted by their full name alphabetically
     */
    public List<UGrad> ExtractAllUGradDetails() {
        List<UGrad> sortedList = new ArrayList<>(students); // create a new arraylist of the students in the CSD to be sorted

        Collections.sort(sortedList);   // sort and return the new list
        return sortedList;
    }   // end ExtractAllUGradDetails

    /**
     * This method extracts all the faculty in the CSD and their details
     * @return it returns a List of Faculty in the CSD sorted by their full name alphabetically
     */
    public List<Faculty> ExtractAllFacultyDetails() {
        List<Faculty> sortedList = new ArrayList<>(employedFaculty);   // create a new list of all faculty in CSD that is to be sorted

        Collections.sort(sortedList);   // sort the list and return it
        return sortedList;
    }   // end ExtractAllFacultyDetails

    /**
     * This method extracts all the Grad students in the CSD and their details
     * @return it returns a List of Grad of the graduate students in the CSD sorted by their full name alphabetically
     */
    public List<Grad> ExtractAllGradDetails() {
        List<Grad> sortedList = new ArrayList<>(hiredTAs);  // create a list of all the TAs in CSD to be sorted

        Collections.sort(sortedList);   // sort and return
        return sortedList;
    }   // end ExtractAllGradDetails

    /**
     * This method extracts all details of advisees of a Faculty
     * @param f is the Faculty whos advisees are being extracted
     * @return it returns a List of UGrad the advisees sorted alphabetically by their full name
     */
    public List<UGrad> ExtractAdviseesDetails(Faculty f) {
        List<UGrad> advisees = new ArrayList<>(f.getAdvisingUgrads());   // create a list of all the advisees of Faculty f

        Collections.sort(advisees); // sort it and return
        return advisees;
    }   // end ExtractAllAdviseesDetails

    /**
     * This method extracts all details of TAs/Grads of a Faculty
     * @param f is the Faculty whos TAs are being extracted
     * @return it returns a List of Grad of the TAs sorted alphabetically by their full name
     */
    public List<Grad> ExtractTAsDetails(Faculty f) {
        List<Grad> assignedTAs = new ArrayList<>(f.getTAs());   // create a list of all the TAs of Faculty f

        Collections.sort(assignedTAs);  // sort it and return
        return assignedTAs;
    }   // end ExtractTAsDetails

    /**
     * This method extracts the details of all Faculty objects in a specific program
     * @param program is the program which all the extracted Faculty objects are in
     * @return it returns a List of Faculty of the faculty within the input program sorted alphabetically by
     * their full name
     */
    public List<Faculty> ExtractFacultyDetails(String program) {
        List<Faculty> inProgram = new ArrayList<>();    // create a list and populate it using a loop with all faculty in the inputted program
        for (Faculty faculty : employedFaculty) {
            if (faculty.getProgram().equalsIgnoreCase(program)) {
                inProgram.add(faculty);
            }
        }

        Collections.sort(inProgram);    // sort and return
        return inProgram;
    }   // end ExtractFacultyDetails

    /**
     * This method retires a faculty member, removes them from the CSD, and all advisees and TAs are moved onto other
     * faculty if possible
     * @param faculty is the member of faculty that is retiring
     * @throws NoSpecialtyException is thrown if there are no members of faculty left that specialize in the input faculty's
     * program
     */
    public void RetireFaculty(Faculty faculty) throws NoSpecialtyException {
        employedFaculty.remove(faculty);    // remove the faculty from the CSD

        for (int i = 0; i < directors.size(); i++) {    // find the director this faculty is under and remove it from that director
            if (directors.get(i).getAssignedFaculty().contains(faculty)) {
                directors.get(i).getAssignedFaculty().remove(faculty);
                directors.get(i).setNumFaculty(directors.get(i).getNumFaculty() - 1);
            }
        }

        for (Grad ta : faculty.getTAs()) {  // reassign all TAs under this faculty
            for (Faculty fac : employedFaculty) {
                if (fac.getNumOfTAs() < 5) {
                    fac.addGrad(ta);
                    ta.setAdvisor(fac);
                    break;
                }
            }
        }

        faculty.getTAs().clear();   // clear all the TAs in this faculty

        for (UGrad student : faculty.getAdvisingUgrads()) { // reassign all advisees of this faculty
            for (Faculty fac : employedFaculty) {
                if (fac.getNumOfAdvisingUGrads() < 8) {
                    fac.addUGrad(student);
                    student.setAdvisor(fac);
                    break;
                }
            }
        }

        faculty.getAdvisingUgrads().clear();    // clear all the advisees in this faculty

        boolean have = false;   // boolean to be changed if there is a faculty with the same specialty

        for (int i = 0; i < employedFaculty.size(); i++) {  // check if the CSD contains a faculty with the same specialty
            if (employedFaculty.get(i).getProgram().equals(faculty.getProgram())) {
                have = true;
            }
        }

        if (!have) {    // if there is no faculty with the same specialty, throw a NoSpecialtyException
            throw new NoSpecialtyException();
        }

    }   // end RetireFaculty
}

/**
 * The class for Person
 */
abstract class Person {
    protected String firstName; // general information about the Person
    protected String lastName;
    protected int age;
    protected String sex;
    protected String address;

    /**
     * Constructor for the person abstract class
     * @param firstName is the first name of the person
     * @param lastName is the last name of the person
     * @param age is the age of the person
     * @param sex is the sex/gender of the person
     * @param address is the address/street of the person
     */
    public Person(String firstName, String lastName, int age, String sex, String address) { // assigning values to the instance variables
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }   // end constructor for Person

    /**
     * Turns the Person object into String format (overriden toString)
     * @return it returns a string of the Person's details
     */
    @Override
    public String toString() {
        return String.format("[%s, %s, %d, %s, %s]", firstName, lastName, age, sex, address);   // creating String of instance variables
    }   // end overridden toString
}

/**
 * The class for Academics
 */
abstract class Academics extends Person {
    protected int employeeID;   // employeeID and salary for academics
    protected double salary;

    /**
     * This method is a constructor for the Academics abstract class, it also initializes the employee ID and increments it
     * @param firstName is the first name of the Academic
     * @param lastName is the last name of the Academic
     * @param age is the age of the Academic
     * @param sex is the sex/gender of the Academic
     * @param address is the address/street of the Academic
     */
    public Academics(String firstName, String lastName, int age, String sex, String address) {  // assigning person details to superclass and incrementing ID
        super(firstName, lastName, age, sex, address);
        this.employeeID = CSD.employeeID+1;
        CSD.employeeID++;
    }   // end constructor for Academics

    /**
     * This method sets/mutates the salary of the Academic
     * @param salary is the salary that the Academic will be assigned
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }   // end setSalary

    /**
     * This method gives the employee ID
     * @return it returns the employeeID of the Academic
     */
    public int getEmployeeID() {
        return employeeID;
    }   // end getEmployeeID

    /**
     * This method turns the Academic into String format (overriden toString)
     * @return it returns the information of the Academic as a String
     */
    @Override
    public String toString() {
        return String.format("[%d, %.1f%s]", employeeID, salary, super.toString()); // returning string format with academic info and super.toString()
    }   // end overridden toString
}

/**
 * The class for Student
 */
abstract class Student extends Person implements Comparable {
    protected Faculty advisor;  // advisor and studentID for the Student class
    protected int studentID;

    /**
     * This method constructs a Student object and initializes and increments the student ID
     * @param firstName is the first name of the Student
     * @param lastName is the last name of the Student
     * @param age is the age of the Student
     * @param sex is the sex/gender of the Student
     * @param address is the address/street of the Student
     */
    public Student(String firstName, String lastName, int age, String sex, String address) {    // assigning Person details to superclass and incrementing ID
        super(firstName, lastName, age, sex, address);
        this.studentID = CSD.studentID + 1;
        CSD.studentID++;
    }   // end constructor for Student

    /**
     * This method turns the Student into String format (overriden toString)
     * @return it returns the information of the Student as a String
     */
    @Override
    public String toString() {
        return String.format("%d[%s]", studentID, super.toString());    // returning the studentID and super.toString
    }   // end overridden toString

    /**
     * This method overrides the compareTo for the object, making it compare based on alphebetical order of the full name
     * to be used later for sorting. (overriden compareTo)
     * @param object is the inputted object which will be compared to the current Student object
     * @return it returns -1 if the name of the Student precedes objects, 0 if they are alphabetically identical
     * and 1 if object's full name procedes this current Student's
     */
    @Override
    public int compareTo(Object object) {   // implementing compareTo, so it compares based on alphabetical order of full name for sorting
        Student obj = (Student) object;
        String thisStr = String.format("%s, %s", firstName, lastName);
        String compareStr = String.format("%s, %s", obj.firstName, obj.lastName);

        int compareVal = thisStr.compareToIgnoreCase(compareStr);

        return compareVal;
    }   // end overridden compareTo

    /**
     * This method sets/mutates the advisor of the Student
     * @param advisor is the advisor that is being assigned to the Student
     */
    public void setAdvisor(Faculty advisor) {
        this.advisor = advisor;
    }   // end setAdvisor

    /**
     * This method gets the advisor of the Student
     * @return it returns the advisor of the Student
     */
    public Faculty getAdvisor() {
        return advisor;
    }   // end getAdvisor
}

/**
 * The class for Administrator
 */
abstract class Administrator extends Academics {

    /**
     * This method constructs an instance of the abstract Administrator class
     * @param firstName is the first name of the Administrator
     * @param lastName is the last name of the Administrator
     * @param age is the age of the Administrator
     * @param sex is the sex/gender of the Administrator
     * @param address is the address/street of the Administrator
     */
    public Administrator(String firstName, String lastName, int age, String sex, String address) {  // constructor using super class to add personal info
        super(firstName, lastName, age, sex, address);
    }   // end constructor for Administrator

    /**
     * This method turns the Administrator into String format (overriden toString)
     * @return it returns the information of the Administrator as a String
     */
    @Override
    public String toString() {
        return String.format("[%s]", super.toString()); // returning the information about this administrator using super.toString()
    }   // end overridden toString
}

/**
 * The class for Faculty
 */
class Faculty extends Academics implements Comparable {
    private String program; // program of this faculty

    private List<UGrad> advisingUGrads; // list of UGrads and the number of them
    private int numUGrads;

    private List<Grad> taList;  // list of TAs and the num of them
    private int numTAs;

    private ProgramDirector director;   // the director this faculty is assigned to

    /**
     * This method is the constructor for an object of Faculty
     * @param firstName is the first name of the Faculty member
     * @param lastName is the last name of the Faculty member
     * @param age is the age of the Faculty member
     * @param sex is the sex/gender of the Faculty member
     * @param address is the address/street of the Faculty member
     */
    public Faculty(String firstName, String lastName, int age, String sex, String address) {    // constructor using superclass for personal info and creating list of tas and advisees
        super(firstName, lastName, age, sex, address);
        advisingUGrads = new ArrayList<>();
        taList = new ArrayList<>();
    }   // end constructor for Faculty

    /**
     * This method sets the program of the Faculty
     * @param program is the program that the Faculty member is in
     */
    public void setProgram (String program) {
        this.program = program;
    }   // end setProgram

    /**
     * This method is a getter for the program of this Faculty
     * @return it returns the program this Faculty is in
     */
    public String getProgram() {
        return program;
    }   // end getProgram

    /**
     * This method turns the Faculty into String format (overriden toString)
     * @return it returns the information of the Faculty as a String
     */
    @Override
    public String toString() {
        return String.format("Faculty %s[%s]", program, super.toString());  // toString with the program and super.toString
    }   // end overridden toString

    /**
     * This method overrides the compareTo for the object, making it compare based on alphebetical order of the full name
     * to be used later for sorting. (overriden compareTo)
     * @param object is the inputted object which will be compared to the current Faculty object
     * @return it returns -1 if the name of the Faculty precedes objects, 0 if they are alphabetically identical
     * and 1 if object's full name procedes this current Faculty's
     */
    @Override
    public int compareTo(Object object) {   // overriding compareTo, so it is comparing alphabetically based on full name for sorting
        Faculty obj = (Faculty) object;

        String thisStr = String.format("%s, %s", firstName, lastName);
        String compareStr = String.format("%s, %s", obj.firstName, obj.lastName);

        return thisStr.compareToIgnoreCase(compareStr);
    }   // end overridden compareTo

    /**
     * This method adds a UGrad to the advisees of this Faculty
     * @param u is the UGrad that is being added to the advisees of this Faculty
     */
    public void addUGrad(UGrad u) { // adding a UGrad and incrementing the numUGrads
        advisingUGrads.add(u);
        numUGrads++;
    }   // end addUGrad

    /**
     * This method is a getter for number of UGrads
     * @return it returns the number of UGrads that are advisees of this Faculty
     */
    public int getNumOfAdvisingUGrads() {
        return numUGrads;
    }   // end getNumOfAdvisingUGrads

    /**
     * This method is a setter for the number of UGrads
     * @param numUGrads is the number of UGrads that the numUGrads is being set to
     */
    public void setNumUGrads(int numUGrads) {
        this.numUGrads = numUGrads;
    }   // end setNumUGrads

    /**
     * This method is a getter for the advisees
     * @return it returns a list of the advisees under this Faculty object
     */
    public List<UGrad> getAdvisingUgrads() {
        return advisingUGrads;
    }   // end getAdvisingUgrads

    /**
     * This method adds a Grad student as a TA under this faculty
     * @param g is the grad student being added as a TA under this faculty
     */
    public void addGrad(Grad g) {   // adding a Grad student and incrementing numTAs
        taList.add(g);
        numTAs++;
    }   // end addGrad

    /**
     * This method is a getter for the TAs
     * @return it returns a list of the TAs under this Faculty
     */
    public List<Grad> getTAs() {
        return taList;
    }   // end getTAs

    /**
     * This method is a getter for the number of TAs
     * @return it returns the number of TAs under this faculty
     */
    public int getNumOfTAs() {
        return numTAs;
    }   // end getNumOfTAs

    /**
     * This method is a setter for the number of TAs
     * @param numTAs is the number of TAs that it is being set to
     */
    public void setNumTAs(int numTAs) {
        this.numTAs = numTAs;
    }   // end setNumTAs

    /**
     * This method is a setter for the director of this Faculty member
     * @param director is the director that is being set as this Faculty member's director
     */
    public void setDirector(ProgramDirector director) {
        this.director = director;
    }   // end setDirector
} // end Faculty

/**
 * The class for UGrad
 */
class UGrad extends Student {
    /**
     * Constructor for UGrad class
     * @param firstName is the first name of the UGrad
     * @param lastName is the last name of the UGrad
     * @param age is the age of the UGrad
     * @param sex is the sex/gender of the UGrad
     * @param address is the address/street of the UGrad
     */
    public UGrad(String firstName, String lastName, int age, String sex, String address) {
        super(firstName, lastName, age, sex, address);
    }   // end constructor for UGrad

    /**
     * This method is the overridden toString method for UGrad
     * @return it returns the string format of this objects relevant instance variables
     */
    @Override
    public String toString() {
        return String.format("Undergraduate [%s]", super.toString());   // toString with student type and super.toString with additional info
    }   // end overridden toString
} // end UGrad

/**
 * The class for Grad
 */
class Grad extends Student {
    /**
     * Constructor for Grad class
     * @param firstName is the first name of the Grad
     * @param lastName is the last name of the Grad
     * @param age is the age of the Grad
     * @param sex is the sex/gender of the Grad
     * @param address is the address/street of the Grad
     */
    public Grad(String firstName, String lastName, int age, String sex, String address) {
        super(firstName, lastName, age, sex, address);
    }   // end constructor for Grad

    /**
     * This method is the overridden toString method for Grad
     * @return it returns the string format of this objects relevant instance variables
     */
    @Override
    public String toString() {
        return String.format("Graduate [%s]", super.toString());    // toString with student type and super.toString with additional info
    }   // end overridden toString
} // end Grad

/**
 * The class for ProgramDirector
 */
class ProgramDirector extends Administrator {
    private String program; // the program of the ProgramDirector

    private List<Faculty> assignedFaculty;  // list of the faculty assigned to it and the number of them
    private int numFaculty;

    /**
     * Constructor for the ProgramDirector class
     * @param firstName is the first name of the ProgramDirector
     * @param lastName is the last name of the ProgramDirector
     * @param age is the age of the ProgramDirector
     * @param sex is the sex/gender of the ProgramDirector
     * @param address is the address/street of the ProgramDirector
     */
    public ProgramDirector(String firstName, String lastName, int age, String sex, String address) {
        super(firstName, lastName, age, sex, address);  // constructing the ProgramDirector using superclass for personal info and initializing assignedFaculty list
        this.assignedFaculty = new ArrayList<>();
    }   // end constructor for ProgramDirector

    /**
     * Setter for the program
     * @param program is the program this ProgramDirector is being set to
     */
    public void setProgram (String program) {
        this.program = program;
    }   // end setProgram

    /**
     * This method adds a Faculty to work under this ProgramDirector
     * @param faculty is the Faculty member being added to the Faculty's under this ProgramDirector
     */
    public void addFaculty(Faculty faculty) {   // adding a faculty to the ProgramDirector and incrementing the numFaculty
        assignedFaculty.add(faculty);
        numFaculty++;
    }   // end addFaculty

    /**
     * The getter method for a list of the assigned faculty
     * @return it returns List of the faculty assigned to this ProgramDirector
     */
    public List<Faculty> getAssignedFaculty() {
        return this.assignedFaculty;
    }   // end getAssignedFaculty

    /**
     * The getter method for the number of Faculty
     * @return it returns the number of faculty under this ProgramDirector
     */
    public int getNumFaculty() {
        return this.numFaculty;
    }   // end getNumFaculty

    /**
     * This is the setter for the number of Faculty
     * @param numFaculty is the amount of Faculty that the numFaculty is being set to
     */
    public void setNumFaculty(int numFaculty) {
        this.numFaculty = numFaculty;
    }   // end setNumFaculty

    /**
     * The overridden toString method for ProgramDirector
     * @return it returns the relevant instance variables of the ProgramDirector object as a String
     */
    @Override
    public String toString() {
        return String.format("Program Director %s[%s]", program, super.toString()); // toString with the name of position and super.toString() with personal info
    }   // end overridden toString
} // end ProgramDirector

/**
 * The class for ChairPerson
 */
class ChairPerson extends Administrator {
    /**
     * The constructor for the ChairPerson class
     * @param firstName is the first name of the ChairPerson
     * @param lastName is the last name of the ChairPerson
     * @param age is the age of the ChairPerson
     * @param sex is the sex/gender of the ChairPerson
     * @param address is the address/street of the ChairPerson
     */
    public ChairPerson(String firstName, String lastName, int age, String sex, String address) {
        super(firstName, lastName, age, sex, address);  // initializing using superclass for personal info
    }   // end constructor for ChairPerson

    /**
     * This is the setter method for the salary of the ChairPerson
     * @param salary is the salary that the ChairPerson will be assigned
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }   // end setSalary

    /**
     * This is the getter method for the ChairPerson's ID
     * @return it returns the ChairPerson's ID
     */
    public int getEmployeeID() {
        return employeeID;
    }   // end getEmployeeID

    /**
     * The overridden toString method for ChairPerson
     * @return it returns the relevant attributes of the ChairPerson object as a String
     */
    @Override
    public String toString() {
        return String.format("Chair Person [%s]", super.toString());    // toString with name of position and super.toString for personal info
    }   // end overridden toString
} // end ChairPerson

/**
 * The class for NoSpaceException
 */
class NoSpaceException extends Exception {
    /**
     * Constructor for the NoSpaceException
     */
    public NoSpaceException() { // calling super constructor to construct this exception
        super();
    }   // end constructor for NoSpaceException

    /**
     * Constructor for the NoSpaceException
     * @param message is the message the exception sends
     */
    public NoSpaceException(String message) {   // calling super constructor to construct this exception with a message
        super(message);
    }   // end overloaded constructor for NoSpaceException
} // end NoSpaceException

/**
 * The class for NoSpecialtyException
 */
class NoSpecialtyException extends Exception {
    /**
     * Constructor for the NoSpecialtyException
     */
    public NoSpecialtyException() { // calling super constructor to construct this exception
        super();
    }   // end constructor for NoSpecialtyException

    /**
     * Constructor for the NoSpecialtyException
     * @param message is the message this Exception sends
     */
    public NoSpecialtyException(String message) {   // calling super constructor to construct this exception with a message
        super(message);
    }   // end overloaded constructor for NoSpecialtyException
} // end NoSpecialtyException

class NoTAException extends Exception {
    /**
     * Constructor for the NoTAException
     */
    public NoTAException() {    // calling super constructor to construct this exception
        super();
    }   // end constructor for NoTAException

    /**
     * Constructor for the NoTAException
     * @param message is the message this Exception sends
     */
    public NoTAException(String message) {  // calling super constructor to construct this exception with a message
        super(message);
    }   // end overloaded constructor for NoTAException
} // end NoSpecialtyException