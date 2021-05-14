package br.com.manager.user.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.manager.user.model.User;

public class UserRepository {

    private final String PERSITENCE_UNIT = "database";

    public User save(User user) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSITENCE_UNIT);
        EntityManager manager = factory.createEntityManager();
        
        manager.getTransaction().begin();

        if(user.getId() == null) {
            manager.persist(user);
        } else {
        	User userOutdated = manager.find(User.class, user.getId());
        	userOutdated.setName(user.getName());
            manager.merge(userOutdated);
        }

        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
        
        return user;

    }

    public List<User> findAll() {
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSITENCE_UNIT);
        EntityManager manager = factory.createEntityManager();
        CriteriaBuilder criteria = manager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteria.createQuery(User.class);
        Root<User> rootEntry = query.from(User.class);
        CriteriaQuery<User> selectAll = query.select(rootEntry);
        TypedQuery<User> selectAllQuery = manager.createQuery(selectAll);
        
        return selectAllQuery.getResultList();
    }

    public void deleteById(Long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSITENCE_UNIT);
        EntityManager manager = factory.createEntityManager();
        CriteriaBuilder criteria = manager.getCriteriaBuilder();

        CriteriaDelete<User> criteriaDelete = criteria.createCriteriaDelete(User.class);
        Root<User> root = criteriaDelete.from(User.class);
        criteriaDelete.where(criteria.equal(root.get("id"), id));
        
        manager.getTransaction().begin();
        manager.createQuery(criteriaDelete).executeUpdate();
        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
    }

	public Optional<User> findByEmail(String email) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSITENCE_UNIT);
        EntityManager manager = factory.createEntityManager();
        CriteriaBuilder criteria = manager.getCriteriaBuilder();
        
        CriteriaQuery<User> query = criteria.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(criteria.equal(root.get("email"), email));
        
        manager.getTransaction().begin();
        List<User> result = manager.createQuery(query).getResultList();
        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
        
        if(result.isEmpty()) {
        	return Optional.empty();
        }
		return Optional.of(result.get(0));
	}
}
