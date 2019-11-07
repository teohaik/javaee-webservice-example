package bh.ws.example.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class PartyDTO {

    private String displayName;
    private BigDecimal version;
    private String primaryIdentificationType;

    public PartyDTO() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getPrimaryIdentificationType() {
        return primaryIdentificationType;
    }

    public void setPrimaryIdentificationType(String primaryIdentificationType) {
        this.primaryIdentificationType = primaryIdentificationType;
    }
}


