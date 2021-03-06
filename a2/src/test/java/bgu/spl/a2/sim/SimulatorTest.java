package bgu.spl.a2.sim;

import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import org.junit.Test;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;


public class SimulatorTest {

    @Test
    public void main() {
        for (int i = 0; i <= 100000; i++) {
            Simulator.main(new String[]{"C:\\Users\\avira\\Desktop\\SPL-Assinment2\\test.json"});
            try (InputStream fin = new FileInputStream("C:\\Users\\avira\\Desktop\\SPL-Assinment2\\a2\\result.ser");
                 ObjectInputStream ois = new ObjectInputStream(fin)) {
                HashMap data = (HashMap) ois.readObject();
                data.forEach((actor, state) -> {
                    System.out.println(actor + ": ");
                    System.out.print("History: ");
                    ((PrivateState) state).getLogger().forEach((String s) -> {
                        System.out.print(s + " , ");
                    });
                    System.out.println("");
                    if (state instanceof DepartmentPrivateState) {
                        System.out.print("Courses: ");
                        ((DepartmentPrivateState) state).getCourseList().forEach((String s) -> {
                            System.out.print(s + " , ");
                        });
                        System.out.print('\n' + "Students: ");
                        ((DepartmentPrivateState) state).getStudentList().forEach((String s) -> {
                            System.out.print(s + " , ");
                        });
                        System.out.println("");
                    } else if (state instanceof StudentPrivateState) {
                        System.out.print("Grades: ");
                        ((StudentPrivateState) state).getGrades().forEach((String s, Integer grade) -> {
                            System.out.print(s + ": " + grade + " , ");
                        });
                        System.out.print('\n' + "Signature: ");
                        System.out.println(((StudentPrivateState) state).getSignature());
                    } else {
                        System.out.print("prequisites: ");
                        ((CoursePrivateState) state).getPrequisites().forEach((String s) -> {
                            System.out.print(s + " , ");
                        });
                        System.out.print('\n' + "students: ");
                        ((CoursePrivateState) state).getRegStudents().forEach((String s) -> {
                            System.out.print(s + " , ");
                        });
                        System.out.print('\n' + "Registered: ");
                        System.out.println(((CoursePrivateState) state).getRegistered());
                        System.out.print("available spaces: ");
                        System.out.println(((CoursePrivateState) state).getAvailableSpots());
                    }
                    System.out.println("----------------");
                });

                System.out.println(data.keySet());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

            }
        }
    }
}