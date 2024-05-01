package system.collegemanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "campus")
    private String campus;

    @OneToMany(mappedBy = "faculty" , cascade = CascadeType.ALL )
    @EqualsAndHashCode.Exclude
    private Set<Department> departments;
}
