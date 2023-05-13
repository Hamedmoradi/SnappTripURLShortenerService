package urlshortenerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urlshortenerservice.domain.ShortenRequest;

import java.util.Optional;

@Repository
public interface ShortenRequestRepository extends JpaRepository<ShortenRequest, Long> {
    Optional<ShortenRequest> findById(Long id);
}