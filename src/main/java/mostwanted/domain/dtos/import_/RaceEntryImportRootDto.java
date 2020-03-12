package mostwanted.domain.dtos.import_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto {

    @XmlElement(name = "race-entry")
    private Set<RaceEntryImportDto> raceEntryImportDtos;

    public RaceEntryImportRootDto() {
    }

    public Set<RaceEntryImportDto> getRaceEntryImportDtos() {
        return raceEntryImportDtos;
    }

    public void setRaceEntryImportDtos(Set<RaceEntryImportDto> raceEntryImportDtos) {
        this.raceEntryImportDtos = raceEntryImportDtos;
    }
}
