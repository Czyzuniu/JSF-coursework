/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.diary.pers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import online.diary.ents.PostalAddress;

/**
 *
 * @author Konrad
 */
@Stateless
public class PostalAddressFacade extends AbstractFacade<PostalAddress> {
    @PersistenceContext(unitName = "OnlineDiaryProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostalAddressFacade() {
        super(PostalAddress.class);
    }
    
}
