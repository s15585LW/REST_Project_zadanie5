package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import domain.Comment;
import domain.Product;
import domain.services.ProductService;

@Path("/product")
@Stateless
public class ProductResources {

	private ProductService db = new ProductService();
	
	@PersistenceContext
	EntityManager em;
	
	
	//Wyswietl produkty
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAll() {
		return em.createNamedQuery("product.All", Product.class).getResultList();
	}
	
	
	//Dodaj produkt
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Product product) {
		em.persist(product);
		return Response.ok(product.getId()).build();
	}
	
	//Wyswietl po id
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	//Wyswietl po category
	@GET
	@Path("/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getC(@PathParam("category") int category){
		Product result = em.createNamedQuery("product.category", Product.class)
				.setParameter("productCategory", category)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	//Wyswietl po cenie od - do
	@GET
	@Path("/{lower}/{higher}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getP(@PathParam("lower") int lower,@PathParam("higher") int higher){
		Product result = em.createNamedQuery("product.price", Product.class)
				.setParameter("lowPrice", lower)
				.setParameter("highPrice", higher)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	//Aktualizacja danych
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Product p){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		p.setId(id);
		em.persist(p);
		return Response.ok().build();
	}
	
	
	//Usuwanie komentarza
	@DELETE
	@Path("/{productid}/{commentId}")
	public Response delete(@PathParam("productid") int id,@PathParam("commentId") int commentid){
		Product result = em.createNamedQuery("product.id.comment", Product.class)
				.setParameter("productId", id)
				.setParameter("commentId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		em.remove(result);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{productId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("productId") int productId){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", productId)
				.getSingleResult();
		if(result==null)
			return null;
		return result.getComments();
	}
	
	@POST
	@Path("/{productid}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("productid") int productId, Comment comment){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", productId)
				.getSingleResult();
		if(result==null)
			return Response.status(404).build();
		result.getComments().add(comment);
		comment.setProduct(result);
		em.persist(comment);
		return Response.ok().build();
	}
	
}
