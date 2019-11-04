/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.util.List;
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
@Path("clases.clientetipo")
public class ClienteTipoFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{cliente}/{tipo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("cliente") Long cliente,@PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE_TIPO.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, cliente)
                .setParameter(2, tipo)
                .setParameter(3, 1);

        query.execute();

        Object resp = query.getOutputParameterValue(4);
        String su = "{"
                + "\"resp\": "+resp
                
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("{cliente}/{tipo}/{estado}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("cliente") Long cliente, @PathParam("tipo") Long tipo,@PathParam("estado")Long estado) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE_TIPO.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, cliente)
                .setParameter(2, tipo)
                .setParameter(3, estado);

        query.execute();

        Object resp = query.getOutputParameterValue(4);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @DELETE
    @Path("{cliente}/{tipo}")
    public Response remove(@PathParam("cliente") Long cliente, @PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE_TIPO.ELIMINAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, cliente)
                .setParameter(2, tipo);

        query.execute();

        Object resp = query.getOutputParameterValue(3);
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
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE_TIPO.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"cliente\":\"" + aux[0] + "\","
                    + "\"tipo\":\"" + aux[1] + "\","
                    + "\"estado\":\"" + aux[2] + "\""
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
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE_TIPO.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"cliente\":\"" + aux[0] + "\","
                    + "\"tipo\":\"" + aux[1] + "\","
                    + "\"estado\":\"" + aux[2] + "\""
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
