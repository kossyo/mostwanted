package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {

    Optional<Racer> findByName(String name);

    @Query(value = "select r from Racer r where size(r.cars) > 0 " +
            "order by size(r.cars) desc, r.name")
    Set<Racer> exportRacersWithCars();
}
