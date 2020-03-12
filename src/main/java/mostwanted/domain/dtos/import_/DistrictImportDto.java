package mostwanted.domain.dtos.import_;

import com.google.gson.annotations.Expose;
import mostwanted.domain.entities.Town;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DistrictImportDto implements Serializable {

    @Expose
    @NotNull
    private String name;

    @Expose
    private String townName;

    public DistrictImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownName() {
        return townName;
    }

    public void getTownName(String town) {
        this.townName = town;
    }
}
