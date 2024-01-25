package com.th.comedor.model.entity;

import java.io.Serializable;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_reserva")
@Getter
@Setter
public class TipoReserva implements Serializable {
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_reserva;

    @Column
    private String nombre_tipo_reserva;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo_reserva", fetch = FetchType.LAZY)
	private List<Reserva> reserva;
}
