package com.th.comedor.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.th.comedor.model.entity.Dias;
import com.th.comedor.model.entity.Estados;
import com.th.comedor.model.entity.Persona;
import com.th.comedor.model.entity.Reserva;
import com.th.comedor.model.entity.Subvension;
import com.th.comedor.model.entity.TipoReserva;
import com.th.comedor.model.serviceI.IDiasService;
import com.th.comedor.model.serviceI.IEstadoService;
import com.th.comedor.model.serviceI.IPersonaService;
import com.th.comedor.model.serviceI.IReservaService;
import com.th.comedor.model.serviceI.ISubvensionService;
import com.th.comedor.model.serviceI.ITipoReservaService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class ReservaController {
    
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

    @GetMapping("/seleccionBuscador")
    public String seleccionBuscador(Model model){
        List<TipoReserva> tipoReservas = tipoReservaService.findAll();
        Collections.sort(tipoReservas, Comparator.comparingLong(TipoReserva::getId_tipo_reserva));
        model.addAttribute("tipoReservas", tipoReservas);
        return "reserva/seleccion_buscador";
    }

    @GetMapping("/buscadorPersona")
    public String buscadorPersona(Model model, HttpServletRequest request) {
        String idTipoReserva = request.getParameter("idTipoReserva");
        Long id_tipo = Long.parseLong(idTipoReserva);
        
        TipoReserva tipoReserva = tipoReservaService.findOne(id_tipo);
        System.out.println(idTipoReserva+" "+tipoReserva.getNombre_tipo_reserva());
        List<Dias> fechasSemanaActual = obtenerFechasSemanaActual();
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MMM");
        for (Dias dia : fechasSemanaActual) {
            dia.setFechaFormateada(sdf.format(dia.getFecha()));
        }
        model.addAttribute("fechasSemanaActual", fechasSemanaActual);
        model.addAttribute("tipoReserva", tipoReserva);
        return "reserva/buscador";
    }

    private List<Dias> obtenerFechasSemanaActual() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date lunesSemanaActual = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date domingoSemanaActual = calendar.getTime();
        return diasService.buscarFechaActual(lunesSemanaActual, domingoSemanaActual);
    }


    @GetMapping("buscador/personaCi/{texto}")
    public String buscarPersonaCi(@PathVariable(value="texto", required = false) String ci, ModelMap model){
    	System.out.println("des="+ci);
    	model.addAttribute("persona", personaService.listaPersonasPorCi(ci.toUpperCase()));
		return "content :: content1";
    }
    @GetMapping("buscador/personaCodigo/{texto}")
    public String buscarPersonaCodigo(@PathVariable(value="texto", required = false) String codigo, ModelMap model){
    	System.out.println("des="+codigo);
    	model.addAttribute("persona", personaService.listaPersonasPorCodigo(codigo.toUpperCase()));
    	
		return "content :: content2";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(RedirectAttributes redirectAttributes, 
    @RequestParam(value = "id_persona")Long id_persona, 
    @RequestParam(value = "id_tiporeserva")Long id_tiporeserva,
    @RequestParam(value = "id_dias")Long[] id_dias, Model model) {

        TipoReserva tipoReserva = tipoReservaService.findOne(id_tiporeserva);
        Persona persona = personaService.findOne(id_persona);
        Estados estados = estadoService.findOne(1l);


        System.out.println(tipoReserva.getNombre_tipo_reserva()+" "+persona.getNombre());
        Subvension subvension = subvensionService.findOne(1l);
        List<Dias> diasSemana=new ArrayList<>();
        for (Long long1 : id_dias) {
            Dias dias = diasService.findOne(long1);
            diasSemana.add(dias);
        }
        for (Dias dias : diasSemana) {
            Reserva reserva = new Reserva();
            reserva.setFecha_reserva(new Date());
            reserva.setEstado_reserva("A");
            reserva.setPersona(persona);
            reserva.setSubvension(subvension);
            reserva.setDias(dias);
            reserva.setEstados(estados);
            reserva.setTipo_reserva(tipoReserva);
            reservaService.save(reserva);

            System.out.println("fechas seleccionadas  "+dias.getFecha());
        }
        redirectAttributes.addFlashAttribute("tipoReserva", tipoReserva);
        redirectAttributes.addFlashAttribute("nombre", persona.getNombre());
        redirectAttributes.addFlashAttribute("mensaje", "Tu reserva fue completada con éxito");
        return "redirect:/buscadorPersona?idTipoReserva=" + id_tiporeserva;
    }

    @GetMapping("/seleccionReserva")
    public String seleccionReserva(Model model){
        List<TipoReserva> tipoReservas = tipoReservaService.findAll();
        Collections.sort(tipoReservas, Comparator.comparingLong(TipoReserva::getId_tipo_reserva));
        model.addAttribute("tipoReservas", tipoReservas);
        return "reserva/seleccion_reserva";
    }

    @GetMapping("/reservasHoy/{idTipoReserva}")
    public String reservasHoy(@PathVariable(value = "idTipoReserva") Long idTipoReserva, Model model){
        TipoReserva tipoReserva = tipoReservaService.findOne(idTipoReserva);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdf.format(new Date());
        System.out.println(tipoReserva.getNombre_tipo_reserva());
        try {
            Date fechaActualFormateada = sdf.parse(fechaFormateada);
            // Ahora, fechaActualFormateada contiene la fecha actual en formato Date
            System.out.println("Fecha actual formateada: " + fechaActualFormateada);
            List<Reserva> listaReservasHoy = reservaService.listaReservaPorDia(fechaActualFormateada, tipoReserva.getId_tipo_reserva());
            model.addAttribute("tipoReserva", tipoReserva);
            model.addAttribute("reservasHoy", listaReservasHoy);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return "reserva/reserva";
    }
    
}
