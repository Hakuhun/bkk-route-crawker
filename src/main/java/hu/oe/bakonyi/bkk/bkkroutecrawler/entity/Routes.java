package hu.oe.bakonyi.bkk.bkkroutecrawler.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "routes")
@Data
public class Routes implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "routeCode")
    private String routeCode;

    @Column(name = "routeType")
    private String routeType;
}
