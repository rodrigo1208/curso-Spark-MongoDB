package br.com.sparkmongodb.service;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;

import br.com.sparkmongodb.domain.Post;
import br.com.sparkmongodb.model.Usuario;

public class PostService {

	MongoClient client = new MongoClient("localhost", 27017);
	Datastore datastore = new Morphia().createDatastore(client, "usuarios");
	
	private UpdateOperations<Usuario> ops;
	
	public Usuario create(Post post, Usuario usuario){
		Query<Usuario> updateQuery = datastore.createQuery(Usuario.class).field("_id").equal(usuario.getId());
		ops = datastore.createUpdateOperations(Usuario.class).push("posts", post);
		datastore.update(updateQuery, ops);
		return usuario;
	}

	public List<Post> getUsuarioPosts(Usuario usuario){
		return usuario.getPosts();
	}
	
}