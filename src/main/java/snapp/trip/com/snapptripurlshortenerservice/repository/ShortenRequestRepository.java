package snapp.trip.com.snapptripurlshortenerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snapp.trip.com.snapptripurlshortenerservice.domain.ShortenRequest;

import java.util.Optional;

@Repository
public interface ShortenRequestRepository extends JpaRepository<ShortenRequest, Long> {
    Optional<ShortenRequest> findById(Long id);
}