package urlshortenerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import urlshortenerservice.domain.HitRateRequest;


@Repository
public interface HitRateRequestRepository extends CrudRepository<HitRateRequest, Long> {
    HitRateRequest findByShorterUrl(String shortUrl);
}