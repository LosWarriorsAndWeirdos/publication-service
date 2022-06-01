package com.acme.publicacion.repository;

import com.acme.publicacion.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PublicationRepository extends JpaRepository<Publication, Long> {
}