package br.com.sparkmongodb.main;


import javax.xml.bind.DatatypeConverter;

import org.mindrot.jbcrypt.BCrypt;

import br.com.sparkmongodb.domain.Post;
import br.com.sparkmongodb.model.Usuario;
import br.com.sparkmongodb.service.PostService;
import br.com.sparkmongodb.service.UsuarioService;
import br.com.sparkmongodb.util.JsonUtil;
import spark.Spark;

public class Api {
	
	public static UsuarioService usuarioService = new UsuarioService();
	public static PostService postService = new PostService();
	
	public static void main(String[] args) {
		
		Spark.before("/api/*", (req, res) -> {
			String authorization = req.headers("Authorization");
			if(null != authorization && authorization.startsWith("Basic")){
				String credentials = authorization.substring("Basic".length()).trim();
				byte[] decode = DatatypeConverter.parseBase64Binary(credentials);
				String decodedString = new String(decode);
				String[] actualCredentials = decodedString.split(":");
				String username = actualCredentials[0];
				String password = actualCredentials[1];
				if(!usuarioService.authenticate(username, password)){
					Spark.halt(401, "Não autorizado");
				}
				
			}else{
				Spark.halt(401, "Não autorizado");
			}
		});
		
		Spark.get("/api/greeting", (req,res) -> {
			String nome = req.queryParams("nome");
			return "hello " + nome;	
		});  
		
		Spark.get("/api/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			Usuario usuario = usuarioService.getUsuario(username);
			if(null != usuario)
				return JsonUtil.toJson(usuario);
			return "Usuario não encontrado"; 
		});
		
		Spark.post("/new/new-user", (req, res) -> {
			res.type("application/json");
			Usuario usuario = JsonUtil.fromJson(req.body(), Usuario.class);
			String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
			usuario.setPassword(hashedPassword);
			usuarioService.createUsuario(usuario);
			return usuario;
		}, JsonUtil.json());
		
		Spark.post("/api/:username/newpost", (req, res) -> {
			String username = req.params("username");
			Usuario usuario = usuarioService.getUsuario(username);
			Post post = JsonUtil.fromJson(req.body(), Post.class);
			
			res.type("application/json");
			postService.create(post, usuario);
			
			return post;
		}, JsonUtil.json()); 
		
		Spark.get("/api/:username/posts", (req, res) -> {
			res.type("application/json");
			
			String username = req.params("username");
			Usuario usuario = usuarioService.getUsuario(username);
			
			return postService.getUsuarioPosts(usuario);
		}, JsonUtil.json());
		
	}
}
