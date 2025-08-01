package com.descodeuses.planit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.UtilisateurDTO;
import com.descodeuses.planit.entity.UtilisateurEntity;
import com.descodeuses.planit.repository.UtilisateurRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<UtilisateurEntity> findByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }

    //Conversion vers DTO
    public UtilisateurDTO getUserDTOByUsername(String username) {
        UtilisateurEntity user = utilisateurRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + username));

        return new UtilisateurDTO(user.getId(), user.getUsername(), user.getRole());
    }

    public void registerNewUser(String username, String password) {
        UtilisateurEntity newUser = new UtilisateurEntity();
        newUser.setUsername(username);
        newUser.setPassword(password); // Pensez à encoder le mot de passe avec BCryptPasswordEncoder
        utilisateurRepository.save(newUser);
    }
}
