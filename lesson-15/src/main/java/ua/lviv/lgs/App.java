package ua.lviv.lgs;

import java.util.Arrays;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class App {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.configure("/META-INF/hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();

		Session session = config.buildSessionFactory(serviceRegistry).openSession();

		Transaction transaction = session.beginTransaction();

		// create post and comments
		Post post = new Post("randomPost");
		Comment coment1 = new Comment("Petrovych");
		Comment coment2 = new Comment("Sydorovich");

		// add post and comments to database
		coment1.setPost(post);
		coment2.setPost(post);
		post.setPostComents(new HashSet<Comment>(Arrays.asList(coment1, coment2)));

		session.save(post);
		transaction.commit();

		// get post an it`s comment from database
		Post postFromDB = (Post) session.get(Post.class, 1);
		System.out.println("\nPost from database --->>>" + postFromDB + "\nComments --->>>" + postFromDB.getPostComents());

		session.close();

	}

}
