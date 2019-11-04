/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Duoc
 */
@Stateless
@Path("clases.boleta")
public class BoletaFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;
     @EJB
    private CorreoBean enviar;
    @POST
    @Path("{total}/{estado}/{ruta}/{cliente}/{direccion}/{total_trans}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create( @PathParam("total") Long total, @PathParam("estado") BigInteger estado, @PathParam("ruta") String ruta, @PathParam("cliente") Long cliente, @PathParam("direccion") Long direccion,@PathParam("total_trans") Long total_trans) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.INSERTAR")
                
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, int.class,
                        ParameterMode.OUT)
               
                .setParameter(1, total)
                .setParameter(2, estado)
                .setParameter(3, ruta)
                .setParameter(4, cliente)
                .setParameter(5, direccion)
                .setParameter(6, total_trans);
        query.execute();

        Object resp = query.getOutputParameterValue(7);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        String mensaje ="Se ha generado una compra con numero de boleta:"+resp.toString();
        String asunto ="Boleta de Compra";
       String destino = CorreoBoletaCliente(cliente,asunto,mensaje);
        if (destino !="") {
            enviar.enviarCorreo(destino, asunto, mensaje);
        }
        
        return Response.ok()
                .entity(su.toString()).build();

    }
    public String CorreoBoletaCliente(Long id,String asunto,String mensaje){
    StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String destino="";

        for (Object[] aux : SELECT_ALL) {
            destino = (aux[6]).toString();
         
        }
        //System.out.println(destino);
        //enviar.enviarCorreo(destino, asunto, mensaje);
        //enviar.enviarCorreo("teamalexduoc@gmail.com",asunto,mensaje);
        return destino;
    }

    @PUT
    @Path("{id}/{fecha}/{total}/{estado}/{ruta}/{cliente}/{direccion}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("fecha") String fecha, @PathParam("total") Long total, @PathParam("estado") BigInteger estado, @PathParam("ruta") String ruta, @PathParam("cliente") Long cliente, @PathParam("direccion") Long direccion) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, int.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, fecha)
                .setParameter(3, total)
                .setParameter(4, estado)
                .setParameter(5, ruta)
                .setParameter(6, cliente)
                .setParameter(7, direccion);

        query.execute();

        Object resp = query.getOutputParameterValue(8);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.ELIMINAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, int.class,
                        ParameterMode.OUT)
                .setParameter(1, id);

        query.execute();

        Object resp = query.getOutputParameterValue(2);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"fecha\":\"" + aux[1] + "\","
                    + "\"total\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"ruta\":\"" + aux[4] + "\","
                    + "\"cliente\":\"" + aux[5] + "\","
                    + "\"direccion\":\"" + aux[6] + "\","
                    + "\"total_trans\":\"" + aux[7] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();

        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"fecha\":\"" + aux[1] + "\","
                    + "\"total\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"ruta\":\"" + aux[4] + "\","
                    + "\"cliente\":\"" + aux[5] + "\","
                    + "\"direccion\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();

    }
    @GET
    @Path("Boleta/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response BoletaCliente(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.BOLETACLIENTE")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                    .setParameter(1, id);

        query.execute();

        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"fecha\":\"" + aux[1] + "\","
                    + "\"total\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"transporte\":\"" + aux[7] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();

    }
    @PUT
    @Path("{id}/{estado}/{ruta}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id,@PathParam("estado") BigInteger estado,@PathParam("ruta") BigInteger ruta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_BOLETA.COMPLETARENTREGA")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, BigInteger.class,
                        ParameterMode.IN)
                  .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, int.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, estado)
                .setParameter(3, ruta);
               
                

        query.execute();

        Object resp = query.getOutputParameterValue(3);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
