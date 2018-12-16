package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;

public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    @Override
    public Vet findByLastName(String lastName) {
        return super.map.values().stream().filter(v -> v.getLastName().equals(lastName)).findFirst().orElse(null);
    }
}