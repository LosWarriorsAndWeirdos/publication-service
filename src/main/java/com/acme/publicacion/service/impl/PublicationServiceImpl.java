package com.acme.publicacion.service.impl;


import com.acme.publicacion.entity.Publication;
import com.acme.publicacion.repository.PublicationRepository;
import com.acme.publicacion.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;

    @Override
    public List<Publication> ListAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication getPublication(long id) {
        return publicationRepository.findById(id).orElse(null);
    }

    @Override
    public Publication createPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication) {
        Publication publicationDB = getPublication(publication.getId());
        if(publicationDB==null)
        {
            return null;
        }

        publicationDB.setTitle(publication.getTitle());
        publicationDB.setBody(publication.getBody());
        publicationDB.setBudget(publication.getBudget());
        publicationDB.setCreatedAt(publication.getCreatedAt());

        return publicationRepository.save(publicationDB);
    }

    @Override
    public Publication deletePublication(long id) {
        Publication publicationDB = getPublication(id);
        if (publicationDB==null)
        {
            return null;
        }
        return publicationRepository.save(publicationDB);
    }
}
