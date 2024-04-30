package system.colluagemanagement.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.colluagemanagement.model.Department;
import system.colluagemanagement.model.Instructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class LessonDto {

    private String courseCode;
    private int courseHours;
    private String courseSubject;
    private String courseDescription;
    private String courseType;
    private String courseLevel;
    private int courseCredits;
    private List<String> courseBooks;
    private String courseLanguage;
    private double courseAverage;
    private String instructorId;
    private String departmentId;
}
