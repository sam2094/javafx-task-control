package repo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import java.util.Map;

    public interface Repository<O> {
     void add(O o);
     
     void update(O o);
     
     boolean remove(O o);
     
     List<O> find(Map<String,String> map);
     
     List<O> findAll();

}