package com.ceraphi.entities;
import com.ceraphi.utils.Status;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "general_information")
@Data
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class GeneralInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user;
    private String projectName;
    private String clientId;
    private String address;
    private String country;
    private String projectCurrency;
    private String preferredUnits;
    private String clientName;
    private String city;
    @Column(nullable = false, columnDefinition = "bit default false")
    private Boolean is_deleted = false;
    private String postalCode;
    @Lob
    private String GeoPoliticalData;
    @Lob
    private String restrictionDetails;
    @OneToMany(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    List<WellInformation> wellInformation = new ArrayList<>();
    @OneToOne(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private SubSurface subSurface;
    @OneToOne(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private SurfaceEquipment SurfaceEquipment;
    @OneToOne(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private HeatConnectionCapex heatConnectionCapex;
    @OneToOne(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private OperationsAndMaintenance operationsAndMaintenance;
    @OneToOne(mappedBy = "generalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private CostCalculator costCalculator;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(mappedBy = "generalInformation",cascade = CascadeType.ALL,orphanRemoval = true)
    private OutputCalculator outputCalculator;
    private String ProjectType;
    @OneToOne(mappedBy = "generalInformation",cascade = CascadeType.ALL,orphanRemoval = true)
    private WellInstallationCAPEX wellInstallationCAPEX;


    public GeneralInformation(Long id, String projectName, String clientId, String address, String country, String projectCurrency, String preferredUnits, String clientName, String city, String postalCode, String geoPoliticalData, String restrictionDetails, List<WellInformation> wellInformation) {
        this.id = id;
        this.projectName = projectName;
        this.clientId = clientId;
        this.address = address;
        this.country = country;
        this.projectCurrency = projectCurrency;
        this.preferredUnits = preferredUnits;
        this.clientName = clientName;
        this.city = city;
        this.postalCode = postalCode;
        GeoPoliticalData = geoPoliticalData;
        this.restrictionDetails = restrictionDetails;
        this.wellInformation = wellInformation;
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getProjectCurrency() {
        return projectCurrency;
    }

    public String getPreferredUnits() {
        return preferredUnits;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getGeoPoliticalData() {
        return GeoPoliticalData;
    }

    public String getRestrictionDetails() {
        return restrictionDetails;
    }

    public List<WellInformation> getWellInformation() {
        return wellInformation;
    }
    public WellInformation getWellInfo() {
        return wellInformation != null && !wellInformation.isEmpty() ? wellInformation.get(0) : null;
    }
    public void updateDetails(GeneralInformation updatedInfo) {
        this.projectName = updatedInfo.getProjectName();
        this.clientId = updatedInfo.getClientId();
        this.address = updatedInfo.getAddress();
        this.country = updatedInfo.getCountry();
        this.projectCurrency = updatedInfo.getProjectCurrency();
        this.preferredUnits = updatedInfo.getPreferredUnits();
        this.clientName = updatedInfo.getClientName();
        this.city = updatedInfo.getCity();
        this.postalCode = updatedInfo.getPostalCode();
        this.GeoPoliticalData = updatedInfo.getGeoPoliticalData();
        this.restrictionDetails = updatedInfo.getRestrictionDetails();
    }
}





