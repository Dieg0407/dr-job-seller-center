package pe.seller.integration.domain.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "url_configuration", schema = "private")
public class UrlConfiguration {
    @Id
    public int id;

    @Column
    public String url;
}
