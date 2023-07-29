package com.project.pc.service;

import com.project.pc.dto.MentorDTO;
import com.project.pc.exceptions.IncompleteActivityException;
import com.project.pc.exceptions.IncompleteMentorException;
import com.project.pc.exceptions.NotFoundException;
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
    public MentorDTO createMentor(Mentor mentor) throws IncompleteMentorException, IllegalArgumentException {
        if (mentor == null){
            throw new IllegalArgumentException("Mentor cannot be null");
        }
        if (mentor.getEmail() == null || mentor.getName() == null){
            throw new IncompleteMentorException("Mentor is missing some required fields");
        }
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
    public MentorDTO getMentorById(Long id) throws NotFoundException{
        return mappingService.convertMentorIntoDTO(mentorRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentor not found with ID: " + id)));
    }
    public MentorDTO getMentorByName(String name) throws NotFoundException{
        return mappingService.convertMentorIntoDTO(mentorRepository.findMentorByName(name).orElseThrow(() -> new NotFoundException("Mentor not found with name: " + name)));
    }
    public MentorDTO getMentorByEmail(String email) throws NotFoundException{
        return mappingService.convertMentorIntoDTO(mentorRepository.findMentorByEmail(email).orElseThrow(() -> new NotFoundException("Mentor not found with email: " + email)));
    }
    public Mentor updateMentor(Long id, MentorDTO mentorDTO) throws NotFoundException, IncompleteMentorException {
        Mentor update = mentorRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentor not found with ID: " + id));
        Status status = statusRepository.findById(update.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + update.getStatus().getId()));
        if (mentorDTO.getName() == null || mentorDTO.getEmail() == null) {
            throw new IncompleteMentorException("Mentor is missing some required fields");
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
    public Mentor patchMentor(Long id, MentorDTO mentorDTO) throws NotFoundException{
        Mentor update = mentorRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentor not found with ID: " + id));
        Status status = statusRepository.findById(update.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found with ID: " + update.getStatus().getId()));
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