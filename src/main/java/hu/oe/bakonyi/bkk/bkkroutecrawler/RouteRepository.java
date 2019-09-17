package hu.oe.bakonyi.bkk.bkkroutecrawler;

import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<Routes, Long> {
}
