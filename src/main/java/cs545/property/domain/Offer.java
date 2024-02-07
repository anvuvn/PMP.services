package cs545.property.domain;

import cs545.property.constant.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.NonNull;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "offer")
@SQLDelete(sql = "UPDATE Offer SET deleted = true WHERE id=?")
@Filter(name = "deletedOfferFilter", condition = "deleted = :isDeleted")
public class Offer {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private Users customer;

    @Column(name = "date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private Property property;

    @Override
    public String toString() {
        return "Offer{" +
                "status=" + status +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}


