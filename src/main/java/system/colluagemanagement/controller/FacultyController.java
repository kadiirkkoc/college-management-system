package system.colluagemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.colluagemanagement.dtos.FacultyDto;
import system.colluagemanagement.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<List<FacultyDto>> getAllFaculties() {
        return new ResponseEntity<>(facultyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long id) {
        return new ResponseEntity<>(facultyService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addFaculty(@RequestBody FacultyDto facultyDto) {
        return new ResponseEntity<>(facultyService.addFaculty(facultyDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        return new ResponseEntity<>(facultyService.updateFaculty(id, facultyDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        return new ResponseEntity<>(facultyService.deleteFaculty(id), HttpStatus.OK);
    }

}
