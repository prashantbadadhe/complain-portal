package com.radixile.panvel.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Complain.
 */
@Entity
@Table(name = "complain")
public class Complain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "details")
    private String details;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @Column(name = "expection")
    private String expection;

    @Column(name = "show_anonymous")
    private Boolean showAnonymous;

    @Column(name = "escalate")
    private String escalate;

    @Column(name = "resolution")
    private String resolution;

    @OneToOne    @JoinColumn(unique = true)
    private Department department;

    @OneToOne    @JoinColumn(unique = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public Complain details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public byte[] getImg() {
        return img;
    }

    public Complain img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Complain imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getExpection() {
        return expection;
    }

    public Complain expection(String expection) {
        this.expection = expection;
        return this;
    }

    public void setExpection(String expection) {
        this.expection = expection;
    }

    public Boolean isShowAnonymous() {
        return showAnonymous;
    }

    public Complain showAnonymous(Boolean showAnonymous) {
        this.showAnonymous = showAnonymous;
        return this;
    }

    public void setShowAnonymous(Boolean showAnonymous) {
        this.showAnonymous = showAnonymous;
    }

    public String getEscalate() {
        return escalate;
    }

    public Complain escalate(String escalate) {
        this.escalate = escalate;
        return this;
    }

    public void setEscalate(String escalate) {
        this.escalate = escalate;
    }

    public String getResolution() {
        return resolution;
    }

    public Complain resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Department getDepartment() {
        return department;
    }

    public Complain department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Status getStatus() {
        return status;
    }

    public Complain status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Complain complain = (Complain) o;
        if (complain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), complain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Complain{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", expection='" + getExpection() + "'" +
            ", showAnonymous='" + isShowAnonymous() + "'" +
            ", escalate='" + getEscalate() + "'" +
            ", resolution='" + getResolution() + "'" +
            "}";
    }
}
