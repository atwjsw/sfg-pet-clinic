package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService{

    @Override
    public Owner findByLastName(String lastName) {
        return super.map.values().stream().filter(o -> o.getLastName().equals(lastName)).findFirst().orElse(null);
    }
}
