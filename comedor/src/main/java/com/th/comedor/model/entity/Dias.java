package com.th.comedor.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dias")
@Getter
@Setter
public class Dias implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dia;
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column
    private String estado;
    @Column
    private String fecha_formateada;
    @Column
    private int cantidad_reservas;
    @Column
    private String tipo;
    @Column
    private int cantidad_reservas_pendientes;
    @Column
    private int cantidad_reservas_servidos;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dias", fetch = FetchType.LAZY)
	private List<Reserva> reserva;
}
