package system.collegemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
