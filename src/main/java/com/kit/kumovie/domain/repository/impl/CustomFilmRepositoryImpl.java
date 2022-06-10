package com.kit.kumovie.domain.repository.impl;

import com.kit.kumovie.domain.Film;
import com.kit.kumovie.domain.Screening;
import com.kit.kumovie.domain.Ticket;
import com.kit.kumovie.domain.repository.CustomFilmRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Subgraph;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomFilmRepositoryImpl implements CustomFilmRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Film> findByIdWithEntityGraph(Long id) {
        EntityGraph<Film> entityGraph = em.createEntityGraph(Film.class);
        Subgraph<Screening> screeningSubgraph = entityGraph.addSubgraph("screenings");
        Subgraph<Ticket> ticketSubgraph = screeningSubgraph.addSubgraph("tickets");
        ticketSubgraph.addAttributeNodes("member");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(em.find(Film.class, id, hints));
    }
}
