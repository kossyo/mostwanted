package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "town")
    private Set<District> districts;

    @OneToMany(mappedBy = "homeTown")
    private Set<Racer> racers;

    public Town() {
        this.districts = new LinkedHashSet<>();
        this.racers = new LinkedHashSet<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }

    public Set<Racer> getRacers() {
        return racers;
    }

    public void setRacers(Set<Racer> racers) {
        this.racers = racers;
    }
}
