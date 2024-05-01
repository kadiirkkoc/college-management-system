package system.collegemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.collegemanagement.dtos.InstructorDto;
import system.collegemanagement.service.InstructorService;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAllInstructors() {
        return new ResponseEntity<>(instructorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDto> getInstructorById(@PathVariable Long id) {
        return new ResponseEntity<>(instructorService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addInstructor(@RequestBody InstructorDto instructorDto) {
        return new ResponseEntity<>(instructorService.addInstructor(instructorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id, @RequestBody InstructorDto instructorDto) {
        return new ResponseEntity<>(instructorService.updateInstructor(id, instructorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        return new ResponseEntity<>(instructorService.deleteInstructor(id), HttpStatus.OK);
    }
}
