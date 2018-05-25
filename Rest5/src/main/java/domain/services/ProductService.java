package domain.services;

import java.util.List;
import java.util.ArrayList;

import domain.Product;

public class ProductService {

	private static List<Product> db = new ArrayList<Product>();
	private static int currentId = 1;
	public List<Product> getAll(){
		return db;
	}
	
	public Product get(int id){
		for(Product p : db){
			if(p.getId() == id)
				return p;
		}
		return null;
	}
	public Product getC(String category){
		for(Product p : db){
			if(p.getCategory() == category)
				return p;
		}
		return null;
	}	

	public Product getP(float lower, float higher){
		for(Product p : db){
			if(p.getPrice() <= higher && p.getPrice() >= lower)
				return p;
		}
		return null;
	}	
	
	public void add(Product p){
		p.setId(++currentId);
		db.add(p);
	}
	public void update(Product product){
		for(Product p : db){
			if(p.getId()==product.getId()){
				p.setName(product.getName());
				p.setCategory(product.getCategory());
				p.setPrice(product.getPrice());
			}
		}
	}
}
