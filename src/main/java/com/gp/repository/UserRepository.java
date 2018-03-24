package com.gp.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.gp.db.bo.User;
import com.gp.db.bo.UserObj;

import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;


@Repository
@Transactional
public class UserRepository {

//	@Autowired
//	private CommonDao commonDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<UserObj> findAll(){
		List<User> tmpList = entityManager.createQuery("select e from User e ",
				User.class).getResultList();
		
		if(tmpList != null && tmpList.size() > 0){
			List<UserObj> finalList = new ArrayList<UserObj>();
			for(User lst:tmpList){
				UserObj tmpData = new UserObj();
				tmpData.setCreatedDate(lst.getCreatedDate());
				tmpData.setEmailAddress(lst.getEmailAddress());
				tmpData.setUpdatedDate(lst.getUpdatedDate());
				tmpData.setUserName(lst.getUserName());
				finalList.add(tmpData);
			}
			
			return finalList;
		} else {
			return null;
		}
		
	}
	
	public boolean isUserExist(User user) {	
		User rowData = null;
		try {
			rowData = findByEmail(user.getEmailAddress());
			if(rowData != null){
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			return false;
		}
		
    }
	
	public boolean loginSuccess(String emailAddress,String password) {	
		User rowData = null;
		try {
			rowData = findByEmail(emailAddress);
			if(rowData != null){
				if(!rowData.getPassword().equals(passwordEncoder(password))){
					return false;
				}
				
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
    }
	
	public String passwordEncoder(String password) throws NoSuchAlgorithmException{		
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(password.getBytes());
	    byte[] digest = md.digest();
	    String md5Hex = DigestUtils.md5DigestAsHex(digest).toUpperCase();
		return md5Hex;
	}
	
	public User findByEmail(String email){
		return entityManager.createQuery("select e from User e where e.emailAddress = :emailAddress",
				User.class).setParameter("emailAddress", email).getSingleResult();	
	}
	
	public boolean deleteByEmail(String email){
		User tmpUser = null;
		boolean success = false;
		try {
			tmpUser = findByEmail(email);
			if(tmpUser != null){
				entityManager.remove(tmpUser);	
				success = true;
			}
		} catch (Exception e){
			
		}
		
		return success;
		
	}
	
	public User createOrUpdateUsr(User user) {
		entityManager.persist(user);
		return user;
	}
	
}
