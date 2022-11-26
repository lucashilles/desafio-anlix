package br.com.lucashilles.domains.parameter;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "sys_parameter")
public class ApplicationParameter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String profile;

    public String name;

    public String value;
}