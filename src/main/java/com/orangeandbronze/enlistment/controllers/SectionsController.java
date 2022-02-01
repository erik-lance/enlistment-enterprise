package com.orangeandbronze.enlistment.controllers;

import com.orangeandbronze.enlistment.domain.*;
import com.orangeandbronze.enlistment.domain.Period;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import java.time.*;
import java.util.*;

@Controller
@RequestMapping("sections")
@SessionAttributes("admin")
class SectionsController {

    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private SectionRepository sectionRepo;
    @Autowired
    private FacultyRepository facultyRepository;

    @ModelAttribute("admin")
    public Admin admin(Integer id) {
        return adminRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no admin found for adminId " + id));
    }

    @GetMapping
    public String showPage(Model model, Integer id) {
        Admin admin = id == null ? (Admin) model.getAttribute("admin") :
                adminRepo.findById(id).orElseThrow(() -> new NoSuchElementException("no admin found for adminId " + id));
        model.addAttribute("admin", admin);
        model.addAttribute("subjects", subjectRepo.findAll());
        model.addAttribute("rooms", roomRepo.findAll());
        model.addAttribute("sections", sectionRepo.findAll());
        model.addAttribute("instructors", facultyRepository.findAll());
        return "sections";
    }

    @PostMapping
    public String createSection(@RequestParam String sectionId, @RequestParam String subjectId, @RequestParam Days days,
                                @RequestParam String start, @RequestParam String end, @RequestParam String roomName,
                                @RequestParam int facultyNumber, RedirectAttributes redirectAttrs) {
        var subject = subjectRepo.findById(subjectId).orElseThrow(() -> new NoSuchElementException("No subject found for subjectId " + subjectId));
        var room = roomRepo.findById(roomName).orElseThrow(() -> new NoSuchElementException("No room found for roomName " + roomName));
        var period = new Period(LocalTime.parse(start), LocalTime.parse(end));
        var instructor = facultyRepository.findById(facultyNumber).orElseThrow(() -> new NoSuchElementException("No faculty found for facultyNumber " + facultyNumber));
        sectionRepo.save(new Section(sectionId, subject, new Schedule(days, period), room, instructor));
        redirectAttrs.addFlashAttribute("sectionSuccessMessage", "Successfully created new section " + sectionId);
        return "redirect:sections";
    }

    @ExceptionHandler(EnlistmentException.class)
    public String handleException(RedirectAttributes redirectAttrs, EnlistmentException e) {
        redirectAttrs.addFlashAttribute("sectionExceptionMessage", e.getMessage());
        return "redirect:sections";
    }

    void setSubjectRepo(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    void setSectionRepo(SectionRepository sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    void setRoomRepo(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    void setAdminRepo(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    void setFacultyRepository(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

}
