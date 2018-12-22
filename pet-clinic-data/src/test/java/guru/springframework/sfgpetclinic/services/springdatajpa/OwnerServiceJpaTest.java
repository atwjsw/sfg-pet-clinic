package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OwnerServiceJpaTest {

    OwnerService ownerServiceJpa;

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetTypeRepository petTypeRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        ownerServiceJpa = new OwnerServiceJpa(ownerRepository, petRepository, petTypeRepository);
        Owner o1 = new Owner();
        o1.setId(1L);
        o1.setLastName("Smith");
        Set<Owner> owners = new HashSet<>();
        owners.add(o1);
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(o1));
        when(ownerRepository.findAll()).thenReturn(owners);
        when(ownerRepository.findByLastName("Smith")).thenReturn(o1);
    }

    @Test
    public void findByLastName() {
        Owner owner = ownerServiceJpa.findByLastName("Smith");
        assertNotNull(owner);
        assertThat(owner.getId(), is(1L));
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    public void findByLastNameNotFound() {
        Owner owner = ownerServiceJpa.findByLastName("Smith1");
        assertNull(owner);
    }

    @Test
    public void findAll() {
        Set<Owner> owners = ownerServiceJpa.findAll();
        assertNotNull(owners);
        assertThat(owners.size(), is(1));
    }

    @Test
    public void findById() {
        Owner owner = ownerServiceJpa.findById(1L);
        assertNotNull(owner);
        assertThat(owner.getId(), is(1L));
    }

    @Test
    public void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerServiceJpa.findById(2L);
        assertNull(owner);
    }

    @Test
    public void save() {
        Owner o1 = new Owner();
        o1.setId(1L);
        when(ownerRepository.save(o1)).thenReturn(o1);
        Owner savedOwner = ownerServiceJpa.save(o1);
        assertNotNull(savedOwner);
        assertThat(o1.getId(), is(1L));
    }

    @Test
    public void delete() {
        Owner o1 = new Owner();
        o1.setId(1L);
        ownerServiceJpa.delete(o1);
        verify(ownerRepository).delete(any());

    }

    @Test
    public void deleteById() {
        ownerServiceJpa.deleteById(anyLong());
        verify(ownerRepository).deleteById(anyLong());
    }
}