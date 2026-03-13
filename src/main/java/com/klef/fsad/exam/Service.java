package com.klef.fsad.exam;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Service Entity Class
 * Maps to the 'services' table in the 'fsadexam' database.
 *
 * Package : com.klef.fsad.exam
 * Database: fsadexam
 */
@Entity
@Table(name = "services")
public class Service {

    // ─────────────────────────────────────────────
    //  Primary Key
    // ─────────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int id;

    // ─────────────────────────────────────────────
    //  Core Attributes
    // ─────────────────────────────────────────────
    @Column(name = "service_name", nullable = false, length = 100)
    private String name;

    @Column(name = "service_date", nullable = false)
    private LocalDate date;

    @Column(name = "service_status", nullable = false, length = 50)
    private String status;                    // e.g. "Active", "Inactive", "Pending"

    @Column(name = "service_type", length = 50)
    private String serviceType;               // e.g. "Maintenance", "Consultation", "Repair"

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "cost")
    private double cost;

    @Column(name = "provider_name", length = 100)
    private String providerName;

    // ─────────────────────────────────────────────
    //  Constructors
    // ─────────────────────────────────────────────

    /** Default no-arg constructor required by Hibernate */
    public Service() {}

    /** Convenience constructor for inserting a full record */
    public Service(String name, LocalDate date, String status,
                   String serviceType, String description,
                   double cost, String providerName) {
        this.name         = name;
        this.date         = date;
        this.status       = status;
        this.serviceType  = serviceType;
        this.description  = description;
        this.cost         = cost;
        this.providerName = providerName;
    }

    // ─────────────────────────────────────────────
    //  Getters & Setters
    // ─────────────────────────────────────────────

    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public LocalDate getDate()              { return date; }
    public void setDate(LocalDate date)     { this.date = date; }

    public String getStatus()               { return status; }
    public void setStatus(String status)    { this.status = status; }

    public String getServiceType()                  { return serviceType; }
    public void setServiceType(String serviceType)  { this.serviceType = serviceType; }

    public String getDescription()                  { return description; }
    public void setDescription(String description)  { this.description = description; }

    public double getCost()             { return cost; }
    public void setCost(double cost)    { this.cost = cost; }

    public String getProviderName()                     { return providerName; }
    public void setProviderName(String providerName)    { this.providerName = providerName; }

    // ─────────────────────────────────────────────
    //  toString
    // ─────────────────────────────────────────────
    @Override
    public String toString() {
        return "Service{" +
               "id="           + id           +
               ", name='"      + name         + '\'' +
               ", date="       + date         +
               ", status='"    + status       + '\'' +
               ", type='"      + serviceType  + '\'' +
               ", cost="       + cost         +
               ", provider='"  + providerName + '\'' +
               ", desc='"      + description  + '\'' +
               '}';
    }
}
