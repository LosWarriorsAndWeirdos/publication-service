package com.acme.publicacion.service;
import com.acme.publicacion.entity.Publication;
import java.util.List;

public interface PublicationService {
    public List<Publication> ListAllPublications();
    public Publication getPublication(long id);
    public Publication createPublication(Publication budget);
    public Publication updatePublication(Publication budget);
    public Publication deletePublication(long id);
}
