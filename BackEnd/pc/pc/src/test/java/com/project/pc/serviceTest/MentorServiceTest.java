package com.project.pc.serviceTest;

import com.project.pc.dto.MentorDTO;
import com.project.pc.exceptions.IncompleteMentorException;
import com.project.pc.exceptions.NotFoundException;
import com.project.pc.model.Mentor;
import com.project.pc.model.Status;
import com.project.pc.repository.MentorRepository;
import com.project.pc.repository.StatusRepository;
import com.project.pc.service.GradeService;
import com.project.pc.service.MappingService;
import com.project.pc.service.MentorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Rollback
class MentorServiceTest {
    @InjectMocks
    private MentorService mentorService;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private MappingService mappingService;

    @Mock
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMentorValidMentor() {
        Mentor mentor = new Mentor();
        mentor.setEmail("mentor@example.com");
        mentor.setName("Mentor name");
        Status status = new Status();
        MentorDTO mentorDTO = new MentorDTO();

        when(statusRepository.save(any(Status.class))).thenReturn(status);
        when(mentorRepository.save(any(Mentor.class))).thenAnswer(invocation -> {
            Mentor savedMentor = invocation.getArgument(0);
            savedMentor.setId(1L);
            return savedMentor;
        });
        when(mappingService.convertMentorIntoDTO(any(Mentor.class))).thenReturn(mentorDTO);

        MentorDTO result = mentorService.createMentor(mentor);

        assertEquals(mentorDTO, result);
    }

    @Test
    public void testCreateMentorNullMentor() {
        assertThrows(IllegalArgumentException.class, () -> mentorService.createMentor(null));
    }

    @Test
    public void testCreateMentorIncompleteMentor() {
        Mentor incompleteMentor = new Mentor();
        assertThrows(IncompleteMentorException.class, () -> mentorService.createMentor(incompleteMentor));
    }


    @Test
    public void testGetAllMentorsAndRepositoryReturnsListWithMentors() {
        List<Mentor> mentors = new ArrayList<>();
        mentors.add(new Mentor());
        mentors.add(new Mentor());

        when(mentorRepository.findAll()).thenReturn(mentors);

        List<MentorDTO> result = mentorService.getAllMentors();

        assertEquals(mentors.size(), result.size());
    }

    @Test
    public void testGetAllMentorsAndRepositoryReturnsEmptyList() {
        List<Mentor> mentors = new ArrayList<>();

        when(mentorRepository.findAll()).thenReturn(mentors);

        List<MentorDTO> result = mentorService.getAllMentors();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetMentorByIdValidId() throws NotFoundException {
        Long id = 1L;
        Mentor mentor = new Mentor();
        mentor.setId(id);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));

        MentorDTO expectedDto = new MentorDTO();
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(expectedDto);

        MentorDTO resultDto = mentorService.getMentorById(id);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetMentorByIdInvalidId() {
        Long id = 999L;
        when(mentorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> mentorService.getMentorById(id));
    }

    @Test
    public void testGetMentorByNameValidName() throws NotFoundException {
        String mentorName = "Ion Dan";
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setName(mentorName);
        MentorDTO mentorDTO = new MentorDTO();

        when(mentorRepository.findMentorByName(mentorName)).thenReturn(Optional.of(mentor));
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(mentorDTO);

        MentorDTO result = mentorService.getMentorByName(mentorName);

        assertEquals(mentorDTO, result);
    }

    @Test
    public void testGetMentorByNameInvalidName() {
        String name = "Ion Dan";
        when(mentorRepository.findMentorByName(name)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> mentorService.getMentorByName(name));
    }

    @Test
    public void testGetMentorByEmailValidEmail() throws NotFoundException {
        String mentorEmail = "ion.dan@example.com";
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setEmail(mentorEmail);
        MentorDTO mentorDTO = new MentorDTO();

        when(mentorRepository.findMentorByEmail(mentorEmail)).thenReturn(Optional.of(mentor));
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(mentorDTO);

        MentorDTO result = mentorService.getMentorByEmail(mentorEmail);

        assertEquals(mentorDTO, result);
    }

    @Test
    public void testGetMentorByEmailInvalidEmail() {
        String mentorEmail = "ion.dan@example.com";
        when(mentorRepository.findMentorByEmail(mentorEmail)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> mentorService.getMentorByEmail(mentorEmail));
    }

    @Test
    public void testUpdateMentorValidIdAndDTO() throws NotFoundException, IncompleteMentorException {
        Long id = 1L;

        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("John Doe");
        existingMentor.setEmail("john.doe@example.com");

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Jane Doe");
        mentorDTO.setEmail("jane.doe@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        Mentor updatedMentor = mentorService.updateMentor(id, mentorDTO);

        assertEquals(mentorDTO.getName(), updatedMentor.getName());
        assertEquals(mentorDTO.getEmail(), updatedMentor.getEmail());
        assertEquals(existingStatus, updatedMentor.getStatus());
    }

    @Test
    public void testUpdateMentorInvalidId() {
        Long id = 999L;

        when(mentorRepository.findById(id)).thenReturn(Optional.empty());

        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Ion Dan");
        mentorDTO.setEmail("ion.dan@example.com");

        assertThrows(NotFoundException.class, () -> mentorService.updateMentor(id, mentorDTO));
    }

    @Test
    public void testUpdateMentorIncompleteMentorDTO() {
        Long id = 1L;
        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("Ion Dan");
        existingMentor.setEmail("ion.dan@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        MentorDTO mentorDTO = new MentorDTO();
        assertThrows(IncompleteMentorException.class, () -> mentorService.updateMentor(id, mentorDTO));
    }



    @Test
    public void testPatchMentorExistingMentorPatchName() {
        Long id = 1L;
        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("Ion Dan");
        existingMentor.setEmail("ion.dan@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        MentorDTO mentorDTO = new MentorDTO();

        String updatedName = "Vasile Ioan";
        mentorDTO.setName(updatedName);
        mentorDTO.setEmail(null);
        Mentor patchedMentor = mentorService.patchMentor(id, mentorDTO);
        assertEquals(updatedName, patchedMentor.getName());
    }

    @Test
    public void testPatchMentorExistingMentorPatchEmail() {
        Long id = 1L;
        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("Ion Dan");
        existingMentor.setEmail("ion.dan@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        MentorDTO mentorDTO = new MentorDTO();

        String updatedEmail = "vasi.le@example.com";
        mentorDTO.setName(null);
        mentorDTO.setEmail(updatedEmail);
        Mentor patchedMentor = mentorService.patchMentor(id, mentorDTO);
        assertEquals(updatedEmail, patchedMentor.getEmail());
    }

    @Test
    public void testPatchMentorExistingMentorPatchNameAndEmail() {
        Long id = 1L;
        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("Ion Dan");
        existingMentor.setEmail("ion.dan@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findById(id)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));

        MentorDTO mentorDTO = new MentorDTO();

        String updatedName = "Jake Johnson";
        String updatedEmail = "vasi.le@example.com";
        mentorDTO.setName(updatedName);
        mentorDTO.setEmail(updatedEmail);
        Mentor patchedMentor = mentorService.patchMentor(id, mentorDTO);
        assertEquals(updatedName, patchedMentor.getName());
        assertEquals(updatedEmail, patchedMentor.getEmail());
    }

    @Test
    public void testPatchMentorNotFoundException() {
        Long id = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        when(mentorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> mentorService.patchMentor(id, mentorDTO));
    }

    @Test
    void testDeleteMentorByEmailMentorExists() {
        String email = "ana.maria@example.com";

        Mentor existingMentor = new Mentor();
        existingMentor.setId(1L);
        existingMentor.setName("Ana Maria");
        existingMentor.setEmail(email);

        when(mentorRepository.findMentorByEmail(email)).thenReturn(Optional.of(existingMentor));

        boolean result = mentorService.deleteMentorByEmail(email);

        assertTrue(result);
    }

    @Test
    void testDeleteMentorByEmailMentorNotExists() {
        String email = "ana.maria@example.com";
        when(mentorRepository.findMentorByEmail(email)).thenReturn(Optional.empty());
        boolean result = mentorService.deleteMentorByEmail(email);
        assertFalse(result);
    }

    @Test
    void testDeleteMentorByIdMentorExists() {
        Long id = 1L;

        Mentor existingMentor = new Mentor();
        existingMentor.setId(id);
        existingMentor.setName("Ana Maria");
        existingMentor.setEmail("ana.maria@example.com");

        when(mentorRepository.findMentorById(id)).thenReturn(Optional.of(existingMentor));
        boolean result = mentorService.deleteMentorById(id);
        assertTrue(result);
    }

    @Test
    void testDeleteMentorByIdMentorNotExists() {
        Long id = 1L;
        when(mentorRepository.findMentorById(id)).thenReturn(Optional.empty());
        boolean result = mentorService.deleteMentorById(id);
        assertFalse(result);
    }
}
