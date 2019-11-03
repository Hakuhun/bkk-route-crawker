package hu.oe.bakonyi.bkk.bkkroutecrawler.repository;

import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepository extends CrudRepository<Routes, Long> {

    List<Routes> findRoutesByRouteCode(String routeCode);

}
