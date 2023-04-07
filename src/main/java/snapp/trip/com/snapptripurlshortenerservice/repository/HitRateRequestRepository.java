package snapp.trip.com.snapptripurlshortenerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import snapp.trip.com.snapptripurlshortenerservice.domain.HitRateRequest;


@Repository
public interface HitRateRequestRepository extends CrudRepository<HitRateRequest, Long> {
    HitRateRequest findByShorterUrl(String shortUrl);
}