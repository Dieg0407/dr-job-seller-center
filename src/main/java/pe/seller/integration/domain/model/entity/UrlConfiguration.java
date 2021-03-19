package pe.seller.integration.domain.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tb_seller_webhook", schema = "private")
public class UrlConfiguration {
    @Id
    @Column(name = "seller_hook_id")
    public int id;

    @Column
    public String url;

    @Column(name = "Sellername")
    public String sellerName;

    @Column
    public String status;
}
