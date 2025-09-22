package com.scm.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    @PersistenceContext
    private EntityManager entityManager;

    public Long getNextVal(String seqName) {
        return ((Number) entityManager
                .createNativeQuery("SELECT get_next_seq_value(:seq)")
                .setParameter("seq", seqName)
                .getSingleResult())
                .longValue();
    }
}
