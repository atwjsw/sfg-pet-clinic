package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PetControllerTest {

    PetController petController;
    @Mock OwnerService ownerService;
    @Mock PetService petService;
    @Mock PetTypeService petTypeService;
    MockMvc mockMvc;
    Owner owner;
    Set<PetType> petTypes;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        petController = new PetController(ownerService, petService, petTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        owner = new Owner();
        owner.setId(1L);
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().name("dog").build());
        petTypes.add(PetType.builder().name("cat").build());
    }

    @Test
    public void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verifyZeroInteractions(petService);
    }

    @Test
    public void processCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:owners/1"));

        verify(petService).save(any());
    }

    @Test
    public void populatePetTypes() {

    }

    @Test
    public void findOwner() {
    }

    @Test
    public void initOwnerBinder() {
    }

}