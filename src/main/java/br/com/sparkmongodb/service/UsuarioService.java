package br.com.sparkmongodb.service;

import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import br.com.sparkmongodb.model.Usuario;

public class UsuarioService {
	
	 MongoClient client = new MongoClient("localhost", 27017);
	 Datastore datastore = new Morphia().createDatastore(client, "usuarios");
	 
	 public Usuario getUsuario(String username){
		 
		 //Versão do curso esta Deprecated
		 //Usuario usuarioDeprecated = (Usuario) datastore.find(Usuario.class, "username", username);
		 
		 Usuario usuario = datastore.find(Usuario.class).filter("username", username).get();
		 if(null != usuario){
			 return usuario;
		 }
		 //Caso o usuario seja nulo
		 return null;
	 }
	 
	 public String createUsuario(Usuario usuario){
		 datastore.save(usuario);
		 return "Usuario criado";
	 }
	 
	 public boolean authenticate(String username, String password){
		 Usuario usuario = getUsuario(username);
		 if(null != usuario && BCrypt.checkpw(password, usuario.getPassword()))
			 return true;
		 //Se não autentificou
		 return false;
	 }
	
}
