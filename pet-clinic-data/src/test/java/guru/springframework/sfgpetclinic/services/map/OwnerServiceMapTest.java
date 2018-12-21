package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OwnerServiceMapTest {

    OwnerService ownerServiceMap;

    @Before
    public void setUp() throws Exception {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setLastName("Smith");
        ownerServiceMap.save(owner);
    }

    @Test
    public void findAll() {
        Owner owner = new Owner();
        owner.setId(1L);
        assertThat(ownerServiceMap.findAll().size(), is(1));
    }

    @Test
    public void findById() {
        Owner owner = ownerServiceMap.findById(1L);
        assertThat(owner.getId(), is(1L));
    }

    @Test
    public void saveExistingId() {
        Owner owner2 = new Owner();
        owner2.setId(2L);
        Owner savedOwner = ownerServiceMap.save(owner2);
        assertNotNull(savedOwner);
        assertThat(savedOwner.getId(), is(2L));
    }

    @Test
    public void saveNoId() {
        Owner owner2 = new Owner();
        Owner savedOwner = ownerServiceMap.save(owner2);
        assertNotNull(savedOwner);
        assertThat(savedOwner.getId(), is(2L));
    }

    @Test
    public void deleteById() {
        ownerServiceMap.deleteById(1L);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    public void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(1L));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    public void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName("Smith");
        assertNotNull(owner);
        assertThat(owner.getId(), is(1L));
    }

    @Test
    public void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("Smith1");
        assertNull(owner);
    }
}