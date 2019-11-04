package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
//import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.classroom.UniqueClassroomList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
//import seedu.address.model.scheduler.Reminder;
//import seedu.address.model.scheduler.UniqueReminderList;
import seedu.address.model.lesson.UniqueLessonWeekList;
import seedu.address.model.student.Student;
//import seedu.address.model.student.UniqueStudentList;

/**
 * Represents the in-memory model of the classroom data.
 */
public class Notebook implements ReadOnlyNotebook {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);


    private Classroom currentClassroom;
    //private Caretaker caretaker;
    private final UniqueClassroomList classrooms;
    private final UniqueLessonList lessons;
    //private final UniqueReminderList reminders;
    //private final UniqueStudentList students;
    //private final UniqueAssignmentList assignments;
    private final UniqueLessonWeekList lessonLists;

    {
        //this.caretaker = new Caretaker(new Memento(currentClassroom()), this.currentClassroom());
        lessons = new UniqueLessonList();
        //reminders = new UniqueReminderList();
        classrooms = new UniqueClassroomList();
        //students = new UniqueStudentList();
        //assignments = new UniqueAssignmentList();
        lessonLists = new UniqueLessonWeekList();
        for (int i = 0; i < 7; i++) {
            lessonLists.add(new UniqueLessonList());
        }
    }

    public Notebook() {
    }

    public Notebook(ReadOnlyNotebook toBeCopied) {
        this();
        resetData(toBeCopied);
        if (classrooms.isEmpty()) {
            Classroom newClassroom = new Classroom();
            setCurrentClassroom(newClassroom);
        } else {
            //Classroom firstClassroom = getClassroomList().get(0);
            setCurrentClassroom(getFirstClassroom());
        }

    }


    public Classroom currentClassroom() {
        return currentClassroom;
    }

    /**
     * Resets the data of the notebook to the newData.
     * @param newData notebook to reset data to.
     */
    public void resetData(ReadOnlyNotebook newData) {
        requireNonNull(newData);
        //System.out.println("PLSCHECK: " + newData.getClassroomList());
        setClassrooms(newData.getClassroomList());
        /*
        if (currentClassroom != null) {
            currentClassroom = resetData(newData.getCurrentClassroom());
        } else if (classrooms.isEmpty()) {
            Classroom newClassroom = new Classroom();
            setCurrentClassroom(newClassroom);
        } else {
            //Classroom firstClassroom = getClassroomList().get(0);
            setCurrentClassroom(getFirstClassroom());
        }
        */
        
        for (int i = 0; i < 7; i++) {
            lessonLists.asUnmodifiableObservableList().get(i).setLessons(newData.getLessonWeekList().get(i));
        }
        //setAllLessons(newData.getLessonWeekList());
        //setLessons(newData.getLessonList());
        //setCurrentReadOnlyClassroom(newData.getCurrentReadOnlyClassroom());
        //setAssignments(newData.getClassroomList());
        //setStudents(newData.getClassroomList());
    }

    //=========== Notebook ================================================================================
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms.setClassrooms(classrooms);
    }

    public void setCurrentClassroom(Classroom classroom) {
        requireNonNull(classroom);
        if (hasClassroom(classroom)) {
            this.currentClassroom = classrooms.get(classroom);
        }
    }

    public void setCurrentReadOnlyClassroom(ReadOnlyClassroom classroom) {
        requireNonNull(classroom);
        if (currentClassroom() != null) {
            currentClassroom().resetData(classroom);
        }
    }

    public ReadOnlyClassroom getCurrentReadOnlyClassroom() {
        if (this.currentClassroom() != null) {
            return this.currentClassroom();
        } else {
            return getFirstReadOnlyClassroom();
        }
    }

    public ReadOnlyClassroom getFirstReadOnlyClassroom() {
        List<Classroom> classroomList = classrooms.asUnmodifiableObservableList();
        return classroomList.get(0);
    }

    public Classroom getFirstClassroom() {
        List<Classroom> classroomList = classrooms.asUnmodifiableObservableList();
        return classroomList.get(0);
    }

    public void setClassroom(ReadOnlyClassroom classroom) {
        this.currentClassroom().resetData(classroom);
    }


    /**
     * Returns true if the notebook has the given classroom.
     * @param classroom check whether this classroom is in the notebook.
     * @return true if the notebook has the given classroom.
     */
    public boolean hasClassroom(Classroom classroom) {
        requireNonNull(classroom);
        return classrooms.contains(classroom);
    }

    /**
     * Returns true if the notebook has the given classroom name.
     * @param classroomName check whether this classroomName is in the notebook.
     * @return true if the notebook has the given classroomName.
     */
    public boolean hasClassroom(String classroomName) {
        requireNonNull(classroomName);
        for (Classroom classroom : classrooms) {
            if (classroom.getClassroomName().equals(classroom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the classroom to the notebook.
     * @param classroom classroom to add to notebook.
     */
    public void addClassroom(Classroom classroom) {
        if (classrooms.isEmpty()) {
            classrooms.add(classroom);
            setCurrentClassroom(classroom);
        } else {
            classrooms.add(classroom);
        }
    }

    public ReadOnlyClassroom getCurrentClassroom() {
        List<Classroom> classroomList = getClassroomList();
        for (Classroom classroom : classroomList) {
            if (classroom.getClassroomName().equals(currentClassroom.getClassroomName())) {
                return classroom;
            }
        }
        return null;
    }

    public Classroom getClassroom(Classroom classroom) {
        List<Classroom> classroomList = getClassroomList();
        for (Classroom toCheck : classroomList) {
            if (toCheck.getClassroomName().equals(classroom.getClassroomName())) {
                return toCheck;
            }
        }
        return null;
    }

    /**
     * Returns true if the current classroom has the given student.
     * @param student checks to see if this student is in the classroom.
     * @return true if the classroom has the student.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return currentClassroom().hasStudent(student);
    }

    /**
     * Returns true if the current classroom has the given assignment.
     * @param assignment checks to see if this assignment is in the classroom.
     * @return true if the classroom has the assignment.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return currentClassroom().hasAssignment(assignment);
    }


    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    /*
    public void setStudents(List<Classroom> classrooms) {
        for (Classroom classroom : classrooms) {
            for (Student student : classroom.getStudentList()) {
                if (!this.students.contains(student)) {
                    this.students.add(student);
                }
            }
        }
    }
     */

    /*
    public void setAssignments(List<Classroom> classrooms) {
        for (Classroom classroom : classrooms) {
            for (Assignment assignment : classroom.getAssignmentList()) {
                if (!this.assignments.contains(assignment)) {
                    this.assignments.add(assignment);
                }
            }
        }
    }
     */


    public void deleteStudent(Student target) {
        //students.remove(target);
        currentClassroom().removeStudent(target);
    }


    public void deleteAssignment(Assignment target) {
        //assignments.remove(target);
        currentClassroom().removeAssignment(target);
    }


    public void addStudent(Student student) {
        //students.add(student);
        currentClassroom().addStudent(student);
    }


    public void addAssignment(Assignment assignment) {
        //assignments.add(assignment);
        currentClassroom().addAssignment(assignment);
    }


    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        //students.setStudent(target, editedStudent);
        currentClassroom().setStudent(target, editedStudent);
    }


    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);
        //assignments.setAssignment(target, editedAssignment);
        currentClassroom().setAssignment(target, editedAssignment);
    }

    public boolean isDisplayStudents() {
        return currentClassroom().isDisplayStudents();
    }

    public void displayStudents() {
        currentClassroom().displayStudents();
    }

    public void displayAssignments() {
        currentClassroom().displayAssignments();
    }



    /**
     * Adds a lessons to the classroom.
     * The lesson must not already exist in the classroom.
     */
    public void addLesson(Lesson p) {
        int day = p.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        dayList.add(p);
        lessons.add(p);
    }

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the classroom.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        int day = lesson.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        return dayList.contains(lesson);
    }

    /**
     * Removes {@code key} from this {@code Classroom}.
     * {@code key} must exist in the classroom.
     */
    public void removeLesson(Lesson key) {
        int day = key.getDayIndex();
        UniqueLessonList dayList = lessonLists.getDayList(day);
        dayList.remove(key);    }


    /**
     * Replaces the given lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the classroom.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the address
     * book.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireNonNull(editedLesson);
        if (target.isSameDay(editedLesson)) {
            int day = target.getDayIndex();
            UniqueLessonList dayList = lessonLists.getDayList(day);
            dayList.setLesson(target, editedLesson);
        } else {
            removeLesson(target);
            addLesson(editedLesson);
        }
    }


    /**
     * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setAllLessons(List<UniqueLessonList> lessons) {
        this.lessonLists.setLessons(lessons);
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Classroom} backed by the internal list of
     * {@code Caretaker}
     */
    public ObservableList<Classroom> getClassroomList() {
        return classrooms.asUnmodifiableObservableList();
    }

    /*
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }
    */

    /*
    public ObservableList<Student> getStudentList() {
        return currentClassroom().getStudentList();
    }

    public ObservableList<Assignment> getAssignmentList() {
        return currentClassroom().getAssignmentList();
    }
     */

    /*
    public List<Classroom> getClassroomList() {
        ArrayList<Classroom> classroomList = new ArrayList<Classroom>(classrooms.values());
        ObservableList<Classroom>
        return classroomList;
    }
     */

    /*
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }
     */

    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    public ObservableList<UniqueLessonList> getLessonWeekList() {
        return lessonLists.asUnmodifiableObservableList();
    }


    //=========== Undo and Redo Operations =============================================================

    /*
    public ReadOnlyNotebook undo() {
        return caretaker.undo();
    }

    public boolean canUndo() {
        return caretaker.canUndo();
    }

    public ReadOnlyClassroom redo() {
        return caretaker.redo();
    }

    public boolean canRedo() {
        return caretaker.canRedo();
    }

    public void saveState() {
        caretaker.saveState();
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }
    */

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Notebook)) {
            return false;
        }

        // state check
        Notebook other = (Notebook) obj;
        return classrooms.equals(other.classrooms)
                && lessons.equals(other.lessons);
                //&& students.equals(other.students)
                //&& assignments.equals(other.assignments);
    }

}

