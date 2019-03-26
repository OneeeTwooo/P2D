package com.htp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.Objects;

public class Factory {
    private Long factoryId;
    private String factoryName;
    private Date factoryOpenYear;

    public Factory() {
    }

    public Factory(Long factoryId, String factoryName, Date factoryOpenYear) {
        this.factoryId = factoryId;
        this.factoryName = factoryName;
        this.factoryOpenYear = factoryOpenYear;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Date getFactoryOpenYear() {
        return factoryOpenYear;
    }

    public void setFactoryOpenYear(Date factoryOpenYear) {
        this.factoryOpenYear = factoryOpenYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;
        return Objects.equals(factoryId, factory.factoryId) &&
                Objects.equals(factoryName, factory.factoryName) &&
                Objects.equals(factoryOpenYear, factory.factoryOpenYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factoryId, factoryName, factoryOpenYear);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
