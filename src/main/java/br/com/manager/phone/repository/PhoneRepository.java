package br.com.manager.phone.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.manager.phone.model.Phone;
import br.com.manager.user.model.User;

public class PhoneRepository {

	public Phone addPhone(Long userId, Phone phone) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("database");
        EntityManager manager = factory.createEntityManager();
        
        manager.getTransaction().begin();

        manager.persist(phone);
        
        User user = manager.find(User.class, userId);
        user.add(phone);
        manager.merge(user);

        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
        
        return phone;
	}

	public void deleteById(Long phoneId, Long userId) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("database");
        EntityManager manager = factory.createEntityManager();
        
        manager.getTransaction().begin();
        
        Phone phone = manager.find(Phone.class, phoneId);
        User user = manager.find(User.class, userId);        
        user.remove(phone);
        
        manager.merge(user);
        manager.remove(phone);
        
        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
	}

}
