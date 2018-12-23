package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    OwnerController ownerController;
    Set<Owner> owners;
    Owner owner1;
    Owner owner2;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ownerController = new OwnerController(ownerService);
        owners = new HashSet<>();
        owner1 = new Owner();
        owner1.setId(1L);
        owner2 = new Owner();
        owner2.setId(2L);
        owners.add(owner1);
        owners.add(owner2);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

//    @Test
//    public void listOwnersMvc() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//        mockMvc.perform(get("/owners"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/ownersList"))
//                .andExpect(model().attribute("selections", hasSize(2)));
//    }

    @Test
    public void findOwners() throws Exception{
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(ownerService);
    }

    @Test
    public void processFindFormReturnMany() throws Exception{
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    public void processFindFormReturnOne() throws Exception{
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(owner1));
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void showOwner() throws Exception{

        when(ownerService.findById(1L)).thenReturn(owner1);
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

}