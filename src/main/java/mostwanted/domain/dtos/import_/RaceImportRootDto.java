package mostwanted.domain.dtos.import_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportRootDto implements Serializable {

    @XmlElement(name = "race")
    private Set<RaceImportDto> raceImportDtos;

    public RaceImportRootDto() {
    }

    public Set<RaceImportDto> getRaceImportDtos() {
        return raceImportDtos;
    }

    public void setRaceImportDtos(Set<RaceImportDto> raceImportDtos) {
        this.raceImportDtos = raceImportDtos;
    }
}
