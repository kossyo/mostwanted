package mostwanted.domain.dtos.import_;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryImportRootDto implements Serializable {

    @XmlElement(name = "entry")
    private Set<EntryImportDto> entryImportDtos;

    public EntryImportRootDto() {
    }

    public Set<EntryImportDto> getEntryImportDtos() {
        return entryImportDtos;
    }

    public void setEntryImportDtos(Set<EntryImportDto> entryImportDtos) {
        this.entryImportDtos = entryImportDtos;
    }
}
