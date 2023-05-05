package com.project.pc.service;

import com.project.pc.exception.CustomException;
import com.project.pc.model.Activity;
import com.project.pc.model.Mentor;
import com.project.pc.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorService {
    @Autowired
    MentorRepository mentorRepository;

    public Mentor createMentor(Mentor mentor){
        return mentorRepository.save(new Mentor(mentor.getName(), mentor.getEmail()));
    }

    public List<Mentor> getAllMentors() {
        List<Mentor> mentors = new ArrayList<>();
        mentorRepository.findAll().forEach(mentors::add);
        return mentors;
    }

    public Mentor getMentorById (Long id) {
        Optional<Mentor> optionalMentor = mentorRepository.findById(id);
        return optionalMentor.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"There is no mentor with id : " + id));
    }
    public Mentor updateMentor(Long id, Mentor newMentor){
        Mentor mentor = mentorRepository.findById(id).orElse(null);
        if (mentor== null){
            return null;
        }
        mentor.setName(newMentor.getName());
        mentor.setEmail(newMentor.getEmail());
        return mentor;
    }
    public HttpStatus deleteAllMentors(){
        mentorRepository.deleteAll();
        return HttpStatus.OK;
    }
    public HttpStatus deleteMentorById(long id){
        Optional<Mentor> mentor = mentorRepository.findById(id);
        if (mentor.isPresent()){
            mentorRepository.deleteById(id);
            return HttpStatus.OK;
        }else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
