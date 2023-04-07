package snapp.trip.com.snapptripurlshortenerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snapp.trip.com.snapptripurlshortenerservice.domain.ShortenRequest;

@Repository
public interface URLRepository extends JpaRepository<ShortenRequest, Long> {
}