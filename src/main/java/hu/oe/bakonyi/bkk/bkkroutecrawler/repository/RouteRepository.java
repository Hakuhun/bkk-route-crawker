package hu.oe.bakonyi.bkk.bkkroutecrawler.repository;

import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<Routes, Long> {
}
