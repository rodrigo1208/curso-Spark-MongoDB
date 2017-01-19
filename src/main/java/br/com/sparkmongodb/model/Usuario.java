package br.com.sparkmongodb.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import br.com.sparkmongodb.domain.Post;

@Entity
public class Usuario {
	
	@Id
	private ObjectId id;
	//Atributos
	private String firstName;
	private String lastName; 
	private String username;
	private String password;
	private int age;
	private int likes;
	
	
	@Embedded
	private List<Post> posts = new ArrayList<>();
	
	public Usuario() {
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public void addPosts(Post post){
		posts.add(post);
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Usuario [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", age=" + age
				+ ", likes=" + likes + "]";
	}
	
	
	
}
