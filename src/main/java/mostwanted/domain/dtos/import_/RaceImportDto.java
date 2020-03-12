package mostwanted.domain.dtos.import_;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportDto implements Serializable {

//        <laps>4</laps>
//        <district-name>Jackson</district-name>
//        <entries>
//            <entry id="10"/>
//            <entry id="9"/>
//        </entries>

    @XmlElement(name = "laps")
    @NotNull
    private Integer laps;

    @XmlElement(name = "district-name")
    @NotNull
    private String districtName;

    @XmlElement(name = "entries")
    private EntryImportRootDto entryImportRootDto;

    public RaceImportDto() {
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public EntryImportRootDto getEntryImportRootDto() {
        return entryImportRootDto;
    }

    public void setEntryImportRootDto(EntryImportRootDto entryImportRootDto) {
        this.entryImportRootDto = entryImportRootDto;
    }
}
