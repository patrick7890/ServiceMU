/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.math.BigInteger;
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
@Path("clases.solicitud")
public class SolicitudFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @EJB
    private CorreoBean enviar;

    @POST
    @Path("{estado}/{cliente}/{tipo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("estado") BigInteger estado, @PathParam("cliente") Long cliente, @PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.INSERTAR")
                .registerStoredProcedureParameter(1, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, estado)
                .setParameter(2, cliente)
                .setParameter(3, tipo);

        query.execute();

        Object resp = query.getOutputParameterValue(4);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @PUT
    @Path("{id}/{estado}/{client}/{tipo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("estado") BigInteger estado, @PathParam("cliente") Long cliente, @PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, estado)
                .setParameter(3, cliente)
                .setParameter(4, tipo);

        query.execute();

        Object resp = query.getOutputParameterValue(5);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("Estado/{id}/{estado}/{cli}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response estado(@PathParam("id") Long id, @PathParam("estado") BigInteger estado, @PathParam("cli") Long cli) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.ESTADO")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, estado);
        query.execute();

        Object resp = query.getOutputParameterValue(3);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        sendmail(id, estado, cli);
        return Response.ok()
                .entity(su.toString()).build();

    }

    void sendmail(Long sol_id, BigInteger estado, Long cli) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.FINDID")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, cli);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String destino = "";

        for (Object[] aux : SELECT_ALL) {
            destino = (aux[6]).toString();

        }
        //System.out.println(destino);
        String asunto = "Solicitud n°" + sol_id;
        String mensaje = "I really just wanna die... TURUTURUTUTUTURUTURUTUTUTURUTURUTUTU";
        switch (Integer.parseInt(estado.toString())) {
            case 0:
                mensaje = "Su solicitud se Encunetra pendiente";
                break;
            case 1:
                mensaje = "Su solicitud Fue Aceptada";
                break;
            case 2:
                mensaje = "Su solicitud Fue Rechazada";
                break;
            default:
                mensaje = "I really just wanna die... TURUTURUTUTUTURUTURUTUTUTURUTURUTUTU";
                break;
        }
        enviar.enviarCorreo(destino, asunto, mensaje);
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.ELIMINAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id);

        query.execute();

        Object resp = query.getOutputParameterValue(2);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.FIND")
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
                    + "\"estado\":\"" + aux[1] + "\","
                    + "\"cliente\":\"" + aux[2] + "\","
                    + "\"rol\":\"" + aux[3] + "\""
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
                .createStoredProcedureQuery("PKG_MAIPOU_SOLICITUD.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"estado\":\"" + aux[1] + "\","
                    + "\"rol\":\"" + aux[2] + "\","
                    + "\"id_cli\":\"" + aux[3] + "\","
                    + "\"rut\":\"" + aux[4] + "\","
                    + "\"nombre\":\"" + aux[5] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
