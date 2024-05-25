package org.osariusz.lorepaint.lore;

import jakarta.validation.*;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleService;
import org.osariusz.lorepaint.map.MapService;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.utils.RoleNames;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LoreService {
    @Autowired
    private LoreRepository loreRepository;

    @Autowired
    private LoreUserRoleService loreUserRoleService;

    @Autowired
    private MapService mapService;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    public Lore getLoreById(Long id) {
        Lore lore = loreRepository.findById(id).orElse(null);
        if(lore == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lore not found");
        }
        return lore;
    }

    public List<Lore> getAllLores() {
        return loreRepository.findAll();
    }

    public void validateLore(Lore lore) {
        Validation.validate(lore, validator);
    }

    public Lore createLore(LoreCreateDTO loreCreateDTO, User creator) {
        Lore lore = modelMapper.map(loreCreateDTO, Lore.class);
        lore.setPlaces(new ArrayList<>());
        lore.setMap(mapService.createMap());
        LoreUserRole loreUserRole = loreUserRoleService.assignLoreUserRole(creator, lore, RoleNames.LORE_GM_ROLE_NAME);
        saveLore(lore);
        loreUserRoleService.saveRole(loreUserRole);
        return lore;
    }

    public void saveLore(Lore lore) {
        validateLore(lore);
        loreRepository.save(lore);
    }

}
