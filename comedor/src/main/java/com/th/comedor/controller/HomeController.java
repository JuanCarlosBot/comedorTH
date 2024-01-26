package com.th.comedor.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.th.comedor.model.entity.Reserva;
import com.th.comedor.model.entity.TipoReserva;
import com.th.comedor.model.serviceI.IDiasService;
import com.th.comedor.model.serviceI.IEstadoService;
import com.th.comedor.model.serviceI.IPersonaService;
import com.th.comedor.model.serviceI.IReservaService;
import com.th.comedor.model.serviceI.ISubvensionService;
import com.th.comedor.model.serviceI.ITipoReservaService;

@Controller
public class HomeController {

    @Autowired
    private IPersonaService personaService;
    @Autowired
    private IDiasService diasService;
    @Autowired
    private ISubvensionService subvensionService;
    @Autowired
    private IReservaService reservaService;
    @Autowired
    private ITipoReservaService tipoReservaService;
    @Autowired
    private IEstadoService estadoService;

    @GetMapping("/tahuamanu")
    public String home(Model model) {

        TipoReserva tipoReserva1 = tipoReservaService.findOne(1l);
        TipoReserva tipoReserva2 = tipoReservaService.findOne(2l);
        TipoReserva tipoReserva3 = tipoReservaService.findOne(3l);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdf.format(new Date());
        System.out.println(tipoReserva2.getNombre_tipo_reserva());
        try {
            Date fechaActualFormateada = sdf.parse(fechaFormateada);
            // Ahora, fechaActualFormateada contiene la fecha actual en formato Date
            System.out.println("Fecha actual formateada: " + fechaActualFormateada);
            List<Reserva> listaReservasHoy2 = reservaService.listaReservaPorDia(fechaActualFormateada, tipoReserva2.getId_tipo_reserva());
            
            int cantPendiente = 0, cantAlmorzado = 0, total = 0;
            for (Reserva reserva : listaReservasHoy2) {
                if (reserva.getEstado_reserva().equals("A")) {
                    total++;
                    if (reserva.getEstados().getId_estado() == 1) {
                        cantPendiente++;
                    } else if (reserva.getEstados().getId_estado() == 2) {
                        cantAlmorzado++;
                    }
                }
            }
            int cantPendiente1 = 0, cantAlmorzado1 = 0, total1 = 0;
            List<Reserva> listaReservasHoy1 = reservaService.listaReservaPorDia(fechaActualFormateada, tipoReserva1.getId_tipo_reserva());
            for (Reserva reserva : listaReservasHoy1) {
                if (reserva.getEstado_reserva().equals("A")) {
                    total1++;
                    if (reserva.getEstados().getId_estado() == 1) {
                        cantPendiente1++;
                    } else if (reserva.getEstados().getId_estado() == 2) {
                        cantAlmorzado1++;
                    }
                }
            }
            int cantPendiente3 = 0, cantAlmorzado3 = 0, total3 = 0;
            List<Reserva> listaReservasHoy3 = reservaService.listaReservaPorDia(fechaActualFormateada, tipoReserva3.getId_tipo_reserva());
            for (Reserva reserva : listaReservasHoy3) {
                if (reserva.getEstado_reserva().equals("A")) {
                    total3++;
                    if (reserva.getEstados().getId_estado() == 1) {
                        cantPendiente3++;
                    } else if (reserva.getEstados().getId_estado() == 2) {
                        cantAlmorzado3++;
                    }
                }
            }

            model.addAttribute("cantPendiente1", cantPendiente1);
            model.addAttribute("cantAlmorzado1", cantAlmorzado1);
            model.addAttribute("total1", total1);
            
            model.addAttribute("cantPendiente", cantPendiente);
            model.addAttribute("cantAlmorzado", cantAlmorzado);
            model.addAttribute("total", total);

            model.addAttribute("cantPendiente3", cantPendiente3);
            model.addAttribute("cantAlmorzado3", cantAlmorzado3);
            model.addAttribute("total3", total3);

            model.addAttribute("tipoReserva", tipoReserva2);
            model.addAttribute("reservasHoyAlmuerzo", listaReservasHoy2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "home";
    }
}
