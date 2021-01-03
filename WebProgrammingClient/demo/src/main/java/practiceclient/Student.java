package practiceclient;

import java.io.Serializable;

import lombok.Data;

@Data
public class Student implements Serializable{
    private int id;
    private String name;
    private String studentId;
    private String DOB;
    private String address;
}
