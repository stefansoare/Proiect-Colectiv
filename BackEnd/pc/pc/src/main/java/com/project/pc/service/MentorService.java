package com.project.pc.service;

import com.project.pc.dto.MentorDTO;
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
    private MentorRepository mentorRepository;
    @Autowired
    private MappingService mappingService;
    public Mentor createMentor(MentorDTO mentorDTO){
        if (mentorDTO == null)
            return null;
        return mentorRepository.save(mappingService.convertDTOIntoMentor(mentorDTO));
    }
    public List<MentorDTO> getAllMentors(){
        List<Mentor> mentors = mentorRepository.findAll();
        List<MentorDTO> mentorDTOS = new ArrayList<>();
        for(Mentor mentor : mentors){
            mentorDTOS.add(mappingService.convertMentorIntoDTO(mentor));
        }
        return mentorDTOS;
    }
    public MentorDTO getMentorById(Long id){
        return mappingService.convertMentorIntoDTO(mentorRepository.findById(id).orElse(null));
    }
    public MentorDTO getMentorByName(String name){
        return mappingService.convertMentorIntoDTO(mentorRepository.findMentorByName(name).orElse(null));
    }
    public MentorDTO getMentorByEmail(String email){
        return mappingService.convertMentorIntoDTO(mentorRepository.findMentorByEmail(email).orElse(null));
    }
    public Mentor updateMentor(Long id, MentorDTO mentorDTO){
        Mentor update = mentorRepository.findMentorById(id).orElse(null);
        if (update == null){
            return null;
        }
        update.setName(mentorDTO.getName());
        update.setEmail(mentorDTO.getEmail());
        mentorRepository.save(update);
        return update;
    }
    public Mentor patchMentor(Long id, MentorDTO mentorDTO){
        Mentor update = mentorRepository.findMentorById(id).orElse(null);
        if (update == null){
            return null;
        }
        if (mentorDTO.getEmail() != null){
            update.setEmail(mentorDTO.getEmail());
        }
        if (mentorDTO.getName() != null){
            update.setName(mentorDTO.getName());
        }
        mentorRepository.save(update);
        return update;
    }
    public boolean deleteAllMentors(){
        mentorRepository.deleteAll();
        return true;
    }
    public boolean deleteMentorByEmail(String email){
        Optional<Mentor> mentor = mentorRepository.findMentorByEmail(email);
        if (mentor.isPresent()){
            mentorRepository.deleteById(mentor.get().getId());
            return true;
        }
        return false;
    }
    public boolean deleteMentorById(Long id){
        Optional<Mentor> mentor = mentorRepository.findMentorById(id);
        if (mentor.isPresent()){
            mentorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}