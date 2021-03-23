package pe.seller.integration.domain.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import pe.seller.integration.domain.model.dto.SellerInfoDTO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_seller", schema = "private")
@TypeDef(typeClass = JsonBinaryType.class, defaultForType = SellerInfoDTO.class)
public class SellerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Integer id;

    @Column(name = "Sellername")
    private String sellerName;

    @Column(columnDefinition = "json", name = "json_seller")
    private SellerInfoDTO json;
}
