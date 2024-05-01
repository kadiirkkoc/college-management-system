package system.collegemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_hours")
    private int courseHours;

    @Column(name = "course_subject")
    private String courseSubject;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "course_level")
    private String courseLevel;

    @Column(name = "course_credits")
    private int courseCredits;

    @ElementCollection
    @Column(name = "course_book")
    private List<String> courseBooks;

    @Column(name = "course_language")
    private String courseLanguage;

    @Column(name = "course_average")
    private double courseAverage;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "lessons")
    private Set<Student> students;
}
