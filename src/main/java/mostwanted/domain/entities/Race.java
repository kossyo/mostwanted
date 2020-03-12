package mostwanted.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "races")
public class Race extends BaseEntity {

    @Column(name = "laps", nullable = false, columnDefinition = "int default 0")
//    @Size(min = 0)
    private Integer laps;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @OneToMany(mappedBy = "race")
    private Set<RaceEntry> raceEntries;



    public Race() {
        this.raceEntries = new HashSet<>();
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Set<RaceEntry> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(Set<RaceEntry> raceEntries) {
        this.raceEntries = raceEntries;
    }


}
