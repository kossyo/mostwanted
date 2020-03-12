package mostwanted.domain.dtos.import_;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TownImportDto implements Serializable {

    @Expose
    @NotNull
    private String name;

    public TownImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
