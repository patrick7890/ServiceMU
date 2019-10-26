/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.math.BigInteger;
import java.sql.Date;
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
@Path("clases.subasta")
public class SubastaFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{valor_ini}/{valor_fin}/{fecha_sub}/{estado}/{transporte}/{boleta}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("valor_ini") Long valor_ini, @PathParam("valor_fin") Long valor_fin, @PathParam("fecha_sub") String fecha_sub, @PathParam("estado") BigInteger estado, @PathParam("transporte") Long transporte, @PathParam("boleta") Long boleta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, Long.class,
                        ParameterMode.OUT)
                .setParameter(1, valor_ini)
                .setParameter(2, valor_fin)
                .setParameter(3, fecha_sub)
                .setParameter(4, estado)
                .setParameter(5, transporte)
                .setParameter(6, boleta);
        query.execute();

        Object resp = query.getOutputParameterValue(7);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    
    
    

    @PUT
    @Path("{id}/{valor_ini}/{valor_fin}/{fecha_sub}/{estado}/{transporte}/{boleta}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("valor_ini") Long valor_ini, @PathParam("valor_fin") Long valor_fin, @PathParam("fecha_sub") String fecha_sub, @PathParam("estado") BigInteger estado, @PathParam("transporte") Long transporte, @PathParam("boleta") Long boleta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, BigInteger.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, Long.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, valor_ini)
                .setParameter(3, valor_fin)
                .setParameter(4, fecha_sub)
                .setParameter(5, estado)
                .setParameter(6, transporte)
                .setParameter(7, boleta);

        query.execute();

        Object resp = query.getOutputParameterValue(8);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.ELIMINAR")
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
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.FIND")
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
                    + "\"valor_ini\":\"" + aux[1] + "\","
                    + "\"valor_fin\":\"" + aux[2] + "\","
                    + "\"fecha_sub\":\"" + aux[3] + "\","
                    + "\"estado\":\"" + aux[4] + "\","
                    + "\"transporte\":\"" + aux[5] + "\","
                    + "\"boleta\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        return Response.ok().entity(su)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();

        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"valor_ini\":\"" + aux[1] + "\","
                    + "\"valor_fin\":\"" + aux[2] + "\","
                    + "\"fecha_sub\":\"" + aux[3] + "\","
                    + "\"estado\":\"" + aux[4] + "\","
                    + "\"transporte\":\"" + aux[5] + "\","
                    + "\"boleta\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        return Response.ok().entity(su)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    @PUT
    @Path("Modificar/{id}/{valor_fin}/{transporte}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response Modificar(@PathParam("id") Long id,  @PathParam("valor_fin") Long valor_fin, @PathParam("transporte") Long transporte) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_SUBASTA.MODIFICARSUBASTA")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, valor_fin)
                .setParameter(3, transporte);


        query.execute();

        Object resp = query.getOutputParameterValue(4);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .allow("OPTIONS").build();

    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
