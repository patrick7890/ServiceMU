/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.math.BigInteger;
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
@Path("clases.producto")
public class ProductoFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{producto}/{desc}/{estado}/{tipo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("producto") String producto, @PathParam("desc") String desc, @PathParam("estado") BigInteger estado, @PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.INSERTAR")
                .registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, producto)
                .setParameter(2, desc)
                .setParameter(3, estado)
                .setParameter(4, tipo);

        query.execute();

        Object resp = query.getOutputParameterValue(5);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("{id}/{producto}/{desc}/{estado}/{tipo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("producto") String producto, @PathParam("desc") String desc, @PathParam("estado") BigInteger estado, @PathParam("tipo") Long tipo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, producto)
                .setParameter(3, desc)
                .setParameter(4, estado)
                .setParameter(5, tipo);

        query.execute();

        Object resp = query.getOutputParameterValue(6);
       String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.ELIMINAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id);

        query.execute();

        Object resp = query.getOutputParameterValue(2);
        String su = "[{\"resp\":\"" + resp + "\""
                ;
        return Response.ok()
                .entity(su.toString()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.FIND")
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
                    + "\"nombre\":\"" + aux[1] + "\","
                    + "\"desc\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"tipo\":\"" + aux[4] + "\""
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
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();

        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"nombre\":\"" + aux[1] + "\","
                    + "\"desc\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"tipo\":\"" + aux[4] + "\","
                    + "\"tipo_desc\":\"" + aux[5] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }
    
    @GET
    @Path("buscartipo/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findPorTipo(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PRODUCTO.BUSCARTIPO")
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
                    + "\"nombre\":\"" + aux[1] + "\","
                     + "\"desc\":\"" + aux[2] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({ MediaType.APPLICATION_JSON})
//    public List<Producto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
    protected EntityManager getEntityManager() {
        return em;
    }

}
