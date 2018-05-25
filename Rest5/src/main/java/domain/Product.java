package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement
@Entity
@NamedQueries({
@NamedQuery(name="product.all", query="SELECT p FROM Product p"),
@NamedQuery(name="product.id", query="FROM Product p Where p.id=:productId"),
@NamedQuery(name="product.category", query="FROM Product p Where p.category=:productCategory"),
@NamedQuery(name="product.price", query="FROM Product p Where p.price<=:lowPrice and p.price>=highPrice")
})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private String name;
	private String category;
	private float price;
	private List<Comment> comments = new ArrayList<Comment>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	@OneToMany(mappedBy="product")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
