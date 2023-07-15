package com.project.pc.serviceTest;

import com.project.pc.dto.MentorDTO;
import com.project.pc.model.Mentor;
import com.project.pc.model.Status;
import com.project.pc.repository.MentorRepository;
import com.project.pc.repository.StatusRepository;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMentor_ValidMentor_CreatesMentor() {
        Mentor mentor = new Mentor();
        Status status = new Status();

        when(statusRepository.save(any(Status.class))).thenReturn(status);
        when(mentorRepository.save(any(Mentor.class))).thenReturn(mentor);
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(new MentorDTO());

        MentorDTO result = mentorService.createMentor(mentor);

        assertEquals(new MentorDTO(), result);

        verify(mentorRepository).save(mentor);
        verify(statusRepository).save(any(Status.class));

        verify(mappingService).convertMentorIntoDTO(mentor);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testCreateMentor_NullMentor_ReturnsNull() {
        Mentor mentor = null;

        MentorDTO result = mentorService.createMentor(mentor);

        assertNull(result);

        verifyNoInteractions(mentorRepository, mappingService);
    }

    @Test
    void testGetAllMentors_RepositoryReturnsMentors_ReturnsMentorDTOList() {
        List<Mentor> mentors = new ArrayList<>();
        mentors.add(new Mentor());
        mentors.add(new Mentor());

        when(mentorRepository.findAll()).thenReturn(mentors);

        List<MentorDTO> result = mentorService.getAllMentors();

        assertEquals(mentors.size(), result.size());

        verify(mentorRepository).findAll();
        verify(mappingService, times(mentors.size())).convertMentorIntoDTO(any(Mentor.class));
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetAllMentors_RepositoryReturnsEmptyList_ReturnsEmptyList() {
        List<Mentor> mentors = new ArrayList<>();

        when(mentorRepository.findAll()).thenReturn(mentors);

        List<MentorDTO> result = mentorService.getAllMentors();

        assertTrue(result.isEmpty());

        verify(mentorRepository).findAll();
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testGetMentorById_ValidId_ReturnsMentorDTO() {
        Mentor mentor = new Mentor(1L);
        Long mentorId = mentor.getId();

        when(mentorRepository.findById(mentorId)).thenReturn(Optional.of(mentor));
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(new MentorDTO());

        MentorDTO result = mentorService.getMentorById(mentorId);

        assertNotNull(result);

        verify(mentorRepository).findById(mentorId);
        verify(mappingService).convertMentorIntoDTO(mentor);
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetMentorById_InvalidId_ReturnsNull() {
        Long mentorId = 1L;

        when(mentorRepository.findById(mentorId)).thenReturn(Optional.empty());

        MentorDTO result = mentorService.getMentorById(mentorId);

        assertNull(result);

        verify(mentorRepository).findById(mentorId);
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testGetMentorByName_ValidName_ReturnsMentorDTO() {
        String mentorName = "Ion Dan";
        Mentor mentor = new Mentor();
        mentor.setName(mentorName);

        when(mentorRepository.findMentorByName(mentorName)).thenReturn(Optional.of(mentor));
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(new MentorDTO());

        MentorDTO result = mentorService.getMentorByName(mentorName);

        assertNotNull(result);

        verify(mentorRepository).findMentorByName(mentorName);
        verify(mappingService).convertMentorIntoDTO(mentor);
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetMentorByName_InvalidName_ReturnsNull() {
        String mentorName = "Ion Dan";

        when(mentorRepository.findMentorByName(mentorName)).thenReturn(Optional.empty());

        MentorDTO result = mentorService.getMentorByName(mentorName);

        assertNull(result);

        verify(mentorRepository).findMentorByName(mentorName);
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testGetMentorByEmail_ValidEmail_ReturnsMentorDTO() {
        String mentorEmail = "ion.dan@example.com";
        Mentor mentor = new Mentor();
        mentor.setEmail(mentorEmail);

        when(mentorRepository.findMentorByEmail(mentorEmail)).thenReturn(Optional.of(mentor));
        when(mappingService.convertMentorIntoDTO(mentor)).thenReturn(new MentorDTO());

        MentorDTO result = mentorService.getMentorByEmail(mentorEmail);

        assertNotNull(result);

        verify(mentorRepository).findMentorByEmail(mentorEmail);
        verify(mappingService).convertMentorIntoDTO(mentor);
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(mappingService);
    }

    @Test
    void testGetMentorByEmail_InvalidEmail_ReturnsNull() {
        String mentorEmail = "ion.dan@example.com";

        when(mentorRepository.findMentorByEmail(mentorEmail)).thenReturn(Optional.empty());

        MentorDTO result = mentorService.getMentorByEmail(mentorEmail);

        assertNull(result);

        verify(mentorRepository).findMentorByEmail(mentorEmail);
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testUpdateMentor_ExistingMentor_ReturnsUpdatedMentor() {
        Long mentorId = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Ion Dan");
        mentorDTO.setEmail("ion.dan@example.com");

        Mentor existingMentor = new Mentor();
        existingMentor.setId(mentorId);
        existingMentor.setName("Ana Maria");
        existingMentor.setEmail("ana.maria@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));
        when(mentorRepository.save(existingMentor)).thenReturn(existingMentor);


        Mentor result = mentorService.updateMentor(mentorId, mentorDTO);


        assertNotNull(result);
        assertEquals(mentorDTO.getName(), result.getName());
        assertEquals(mentorDTO.getEmail(), result.getEmail());
        assertEquals(existingStatus, result.getStatus());

        verify(mentorRepository).findMentorById(mentorId);
        verify(statusRepository).findById(existingStatus.getId());
        verify(statusRepository).save(existingStatus);
        verify(mentorRepository).save(existingMentor);
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testUpdateMentor_NonExistingMentor_ReturnsNull() {
        Long mentorId = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Ion Dan");
        mentorDTO.setEmail("ion.dan@example.com");

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.empty());

        Mentor result = mentorService.updateMentor(mentorId, mentorDTO);

        assertNull(result);

        verify(mentorRepository).findMentorById(mentorId);
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testPatchMentor_ExistingMentor_PatchesMentor() {
        Long mentorId = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Ion Dan");

        Mentor existingMentor = new Mentor();
        existingMentor.setId(mentorId);
        existingMentor.setName("Ana Maria");
        existingMentor.setEmail("ana.maria@example.com");

        Status existingStatus = new Status();
        existingStatus.setId(1L);
        existingMentor.setStatus(existingStatus);

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.of(existingMentor));
        when(statusRepository.findById(existingStatus.getId())).thenReturn(Optional.of(existingStatus));
        when(mentorRepository.save(existingMentor)).thenReturn(existingMentor);


        Mentor result = mentorService.patchMentor(mentorId, mentorDTO);


        assertNotNull(result);
        assertEquals(mentorDTO.getName(), result.getName());
        assertEquals(existingMentor.getEmail(), result.getEmail());
        assertEquals(existingStatus, result.getStatus());

        verify(mentorRepository).findMentorById(mentorId);
        verify(statusRepository).findById(existingStatus.getId());
        verify(statusRepository).save(existingStatus);
        verify(mentorRepository).save(existingMentor);
        verifyNoMoreInteractions(mentorRepository);
        verifyNoMoreInteractions(statusRepository);
    }

    @Test
    void testPatchMentor_NonExistingMentor_ReturnsNull() {
        Long mentorId = 1L;
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setName("Ion Dan");

        when(mentorRepository.findMentorById(mentorId)).thenReturn(Optional.empty());

        Mentor result = mentorService.patchMentor(mentorId, mentorDTO);

        assertNull(result);

        verify(mentorRepository).findMentorById(mentorId);
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testDeleteMentorByEmail_ExistingMentor_DeletesMentorAndReturnsTrue() {
        String email = "ana.maria@example.com";

        Mentor existingMentor = new Mentor();
        existingMentor.setId(1L);
        existingMentor.setName("Ana Maria");
        existingMentor.setEmail(email);

        when(mentorRepository.findMentorByEmail(email)).thenReturn(Optional.of(existingMentor));

        boolean result = mentorService.deleteMentorByEmail(email);

        assertTrue(result);

        verify(mentorRepository).findMentorByEmail(email);
        verify(mentorRepository).deleteById(existingMentor.getId());
        verifyNoMoreInteractions(mentorRepository);
    }

    @Test
    void testDeleteMentorByEmail_NonExistingMentor_ReturnsFalse() {
        String email = "ana.maria@example.com";

        when(mentorRepository.findMentorByEmail(email)).thenReturn(Optional.empty());

        boolean result = mentorService.deleteMentorByEmail(email);

        assertFalse(result);

        verify(mentorRepository).findMentorByEmail(email);
        verifyNoMoreInteractions(mentorRepository);
    }

}
