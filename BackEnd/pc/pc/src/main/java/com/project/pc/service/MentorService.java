package com.project.pc.service;

import com.project.pc.dto.MentorDTO;
import com.project.pc.model.Mentor;
import com.project.pc.model.Status;
import com.project.pc.repository.MentorRepository;
import com.project.pc.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorService {
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private MappingService mappingService;
    public MentorDTO createMentor(Mentor mentor){
        if (mentor == null)
            return null;
        Status status = new Status();
        statusRepository.save(status);
        mentor.setStatus(status);
        mentorRepository.save(mentor);
        return mappingService.convertMentorIntoDTO(mentor);
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
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        update.setName(mentorDTO.getName());
        update.setEmail(mentorDTO.getEmail());
        update.setStatus(status);
        mentorRepository.save(update);
        return update;
    }
    public Mentor patchMentor(Long id, MentorDTO mentorDTO){
        Mentor update = mentorRepository.findMentorById(id).orElse(null);
        if (update == null){
            return null;
        }
        Status status = statusRepository.findById(update.getStatus().getId()).orElse(null);
        if (status == null) {
            return null;
        }
        status.setModifiedBy();
        status.setModificationDate();
        statusRepository.save(status);
        if (mentorDTO.getEmail() != null){
            update.setEmail(mentorDTO.getEmail());
        }
        if (mentorDTO.getName() != null){
            update.setName(mentorDTO.getName());
        }
        update.setStatus(status);
        mentorRepository.save(update);
        return update;
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