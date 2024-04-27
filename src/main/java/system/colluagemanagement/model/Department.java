package system.colluagemanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @EqualsAndHashCode.Exclude
    private Faculty faculty;

    @OneToMany(mappedBy = "department")
    private Set<Student> students;

    @OneToMany(mappedBy = "department")
    private Set<Lesson> lessons;

    @OneToMany(mappedBy = "department")
    private Set<Instructor> instructors;
}
