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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.th.comedor.model.dao.DiasDao;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@CrossOrigin
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
    @Autowired
    private DiasDao diasDao;

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
            dia.setFecha_formateada(sdf.format(dia.getFecha()));
        }
        //model.addAttribute("fechasSemanaActual", fechasSemanaActual);
        model.addAttribute("tipoReserva", tipoReserva);
        return "reserva/buscador";
    }

    /*@GetMapping("/tu/controlador/obtenerFechas")
    @ResponseBody
    public List<Dias> obtenerFechasSemanaActualPersona(@RequestParam Long idPersona) {
        Persona persona = personaService.findOne(idPersona);
        System.out.println(persona.getNombre());
        // Lógica para obtener las fechas de la semana actual para la persona con el ID proporcionado
        List<Dias> fechasSemanaActual = obtenerFechasSemanaActual();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MMM");
        for (Dias dia : fechasSemanaActual) {
            dia.setFecha_formateada(sdf.format(dia.getFecha()));
        }
        // Devolver la lista de fechas en formato JSON
        return fechasSemanaActual;
    }*/
    @GetMapping("/tu/controlador/obtenerFechas")
    public String obtenerFechasSemanaActualPersona(@RequestParam Long idPersona, @RequestParam Long idTipoReserva, ModelMap model) {
        Persona persona = personaService.findOne(idPersona);
        System.out.println(persona.getNombre());
        TipoReserva tipoReserva = tipoReservaService.findOne(idTipoReserva);

        // Lógica para obtener las fechas de la semana actual para la persona con el ID proporcionado
        List<Dias> fechasSemanaActual = obtenerFechasSemanaActual();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MMM");
        for (Dias dia : fechasSemanaActual) {
            dia.setFecha_formateada(sdf.format(dia.getFecha()));
        }
        //fechas de semana actual sin restricciones
        List<Dias> contDias = new ArrayList<>();
        for (Dias dias : fechasSemanaActual) {
            if (!dias.getReserva().isEmpty()) {
                for (Reserva r : dias.getReserva()) {
                    if (r.getPersona().getId_persona()==persona.getId_persona() && r.getTipo_reserva().getId_tipo_reserva()==tipoReserva.getId_tipo_reserva()) {
                        dias.setFecha_formateada(sdf.format(dias.getFecha()));
                        contDias.add(dias);
                    }
                }    
            }
              
        }
        model.addAttribute("fechasSemanaActual", fechasSemanaActual);
        model.addAttribute("reservados", contDias);
        return "content :: fechas";
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
    @RequestParam(name = "id_dias")Long[] id_dias, Model model) {

        TipoReserva tipoReserva = tipoReservaService.findOne(id_tiporeserva);
        Persona persona = personaService.findOne(id_persona);
        Estados estados = estadoService.findOne(1l);


        System.out.println(tipoReserva.getNombre_tipo_reserva()+" "+persona.getNombre());
        Subvension subvension = subvensionService.findOne(1l);
        List<Dias> diasSemana=new ArrayList<>();
        if (id_dias.length!=0) {
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
        }else{
            redirectAttributes.addFlashAttribute("tipoReserva", tipoReserva);
            redirectAttributes.addFlashAttribute("nombre", persona.getNombre());
            redirectAttributes.addFlashAttribute("mensaje", "No selecionó fechas");
            return "redirect:/buscadorPersona?idTipoReserva=" + id_tiporeserva;
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
            int cantPendiente=0;
            int cantAlmorzado=0;
            int total=0;
            for (Reserva reserva : listaReservasHoy) {
                if (reserva.getEstado_reserva().equals("A")) {
                    total++;
                    if (reserva.getEstados().getId_estado()==1) {
                        cantPendiente++;
                    }else if (reserva.getEstados().getId_estado()==2) {
                        cantAlmorzado++;
                    }
                }
            }
            model.addAttribute("cantPendiente", cantPendiente);
            model.addAttribute("cantAlmorzado", cantAlmorzado);
            model.addAttribute("total", total);
            model.addAttribute("tipoReserva", tipoReserva);
            model.addAttribute("reservasHoy", listaReservasHoy);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return "reserva/reserva";
    }

    @PostMapping("/confirmarReserva")
    public String confirmarReserva(@RequestParam("id_reserva") Long id_reserva , HttpServletRequest request){
        String id_tiporeserva = request.getParameter("id_tipo_reserva");
        Long id_tipo_reserva = Long.parseLong(id_tiporeserva);
        Estados estados = estadoService.findOne(2l);
        Reserva reserva = reservaService.findOne(id_reserva);
        reserva.setEstados(estados);
        reservaService.save(reserva);
        return "redirect:/reservasHoy/"+id_tipo_reserva;
    }
    

    //nueva reserva modo admin
    @GetMapping("/NuevaReservaAdmin")
    public String nuevaReservaAdmin(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("tipoReservas", tipoReservaService.findAll());
        model.addAttribute("dias", diasService.findAll());
        model.addAttribute("subvensiones", subvensionService.findAll());
        model.addAttribute("estados", estadoService.findAll());

        model.addAttribute("personas", personaService.findAll());
        return "reserva/nueva_reserva";
    }
    @GetMapping("/obtenerDatosPersona")
    public Persona obtenerDatosPersona(@RequestParam("idPersona") Long idPersona) {
        // Aquí deberías realizar la lógica para obtener los datos de la persona con el ID proporcionado
        // Supongamos que tienes un servicio de Persona que te devuelve los datos de la persona según su ID
        Persona persona = personaService.findOne(idPersona);
        System.out.println(persona.getNombre()+" '''''''''''''''''''''''''''''''''''''''''''''''''''lllllllllllll");
        return persona;
    }









    //consultas
    @GetMapping("/consultas")
    public String consultasReserva(Model model){
        
        model.addAttribute("tipoReservas", tipoReservaService.findAll());
        return "reserva/consulta_reserva";
    }

    @GetMapping("/consultas/{fecha1}/{fecha2}/{tipo}")
    public String consultasReservaTipo(
        @PathVariable(value="fecha1", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1,
        @PathVariable(value="fecha2", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2,
        @PathVariable(value="tipo", required = false) Long tipo,
        Model model ) {
        TipoReserva tipoReserva = tipoReservaService.findOne(tipo);
        int cantPerPendientes = 0;
        int cantPerServidos = 0;
        int cantidadPersona = 0;
        List<Dias> dias = diasService.consultaRangoDias(fecha1, fecha2);
        List<Dias> diasConDatos = new ArrayList<>();


        for (Dias dia : dias) {
            for (Reserva reserva : dia.getReserva()) {
                
                if (reserva.getTipo_reserva().getId_tipo_reserva()==tipo && reserva.getEstado_reserva().equals("A")) {
                    cantidadPersona++;
                    if (reserva.getEstados().getNombre_estado().equals("PENDIENTE")) {
                        cantPerPendientes++;
                    }else if(reserva.getEstados().getNombre_estado().equals("SERVIDO")){
                        cantPerServidos++;
                    }
                }    
                
                
            }
            dia.setCantidad_reservas(cantidadPersona);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy - EEE");
            String fechaFormateada = sdf.format(dia.getFecha());
            //Date f = sdf.parse(fechaFormateada);
            dia.setFecha_formateada(fechaFormateada);
            dia.setCantidad_reservas_pendientes(cantPerPendientes);
            dia.setCantidad_reservas_servidos(cantPerServidos);
            dia.setTipo(tipoReserva.getNombre_tipo_reserva());
            diasConDatos.add(dia);
            cantidadPersona=0;
            cantPerPendientes=0;
            cantPerServidos=0;
            System.out.println(dia.getFecha()+" // "+dia.getCantidad_reservas());    
        }

        model.addAttribute("id_tipo_reserva", tipo);
        model.addAttribute("dias", diasConDatos);
        return "content :: dias";
    }



    //ver detalle de la consulta
    @GetMapping("/verDetalleReserva/{id_dia}/{id_tipo_reserva}")
    public String verDetalleReserva(
        @PathVariable(value="id_dia", required = false) Long id_dia,
        @PathVariable(value="id_tipo_reserva", required = false) Long id_tipo_reserva,  Model model) {
        Dias dia = diasService.findOne(id_dia);
        TipoReserva tipoReserva = tipoReservaService.findOne(id_tipo_reserva);
        int cantidadPersona=0, cantPerPendientes=0, cantPerServidos=0;
        List<Reserva> r = new ArrayList<>();
        for (Reserva reserva : dia.getReserva()) {
            if (reserva.getTipo_reserva().getId_tipo_reserva()==id_tipo_reserva && reserva.getEstado_reserva().equals("A")) {
                r.add(reserva);
                cantidadPersona++;
                if (reserva.getEstados().getNombre_estado().equals("PENDIENTE")) {
                    cantPerPendientes++;
                }else if(reserva.getEstados().getNombre_estado().equals("SERVIDO")){
                    cantPerServidos++;
                }
            }   
        }
        for (Reserva reserva : r) {
            System.out.println(reserva.getPersona().getNombre()+" == "+reserva.getEstados().getNombre_estado());
        }
        model.addAttribute("cantPerPendientes", cantPerPendientes);
        model.addAttribute("cantidadPersona", cantidadPersona);
        model.addAttribute("cantPerServidos", cantPerServidos);
        model.addAttribute("reservas", r);
        // Lógica de tu controlador aquí
        return "reserva/detalle_consulta";  // Cambia esto por la vista adecuada
    }

    @PostMapping("/generarDetalleConsulta")
    @ResponseBody
    public ResponseEntity<List<Reserva>> confirmarReserva(@RequestParam("id_dia") Long idDia,
                                                        @RequestParam("id_tipo_reserva") Long idTipoReserva) {
        try {
            // Lógica para obtener la lista de reservas por día y tipo
            System.out.println(idDia+"   f "+idTipoReserva);
            // Supongamos que tienes un servicio que devuelve la lista de reservas
            List<Reserva> reservas = reservaService.reservasPorTipoYDia(idDia, idTipoReserva);

            // Devolver la lista de reservas como parte de la respuesta
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // o loguea utilizando un logger
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
