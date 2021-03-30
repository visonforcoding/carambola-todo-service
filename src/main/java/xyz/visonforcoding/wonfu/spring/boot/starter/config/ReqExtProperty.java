/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.visonforcoding.wonfu.spring.boot.starter.config;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
public class ReqExtProperty {

    public ThreadLocal<String> _uniq_req_no = new ThreadLocal<String>();
    public ThreadLocal<Integer> sqlCount = ThreadLocal.withInitial(() -> 0);
    public ThreadLocal<Long> sqlDuration = ThreadLocal.withInitial(() -> 0L);

}
