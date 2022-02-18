package org.rabbitmq.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.rabbitmq.model.orderlist;
import org.rabbitmq.redis.RedisUtils;
import org.rabbitmq.repository.orderlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class orderlistController {
	@Autowired
	private orderlistRepository ordRes;
	 @Resource
	  private RedisUtils redisUtil;
	 @RequestMapping("/add")
	 public String orderadd(HttpServletRequest request)
	 {
		 String id=request.getParameter("id");
		 String result="下单成功！";
		 while(true)
		 {
			if(redisUtil.hasKey("return.errorid:"+id) || redisUtil.hasKey("confirm.errorid:"+id))
			 {
				 result="下单失败！";
				 break;
			 }
			 else  if(redisUtil.hasKey("confirm.id:"+id))
			 {
				 orderlist model=new orderlist();
				 model.setOrderID(id);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS");
			      String now=sdf.format(new Date());
			      model.setCreateTime(new Date());
			      model.setStatus(1);;
			      model.setTotalNum(10);
			      model.setTotalPrice(215);
			      model.setUserName(now);
			      ordRes.save(model);
				 break;
			 }
		 }
		 request.setAttribute("result", result);
		  return "/orderadd";
	}
	 @RequestMapping("/orderfail")
	 public String orderfail() {
		  return "/orderfail";
		 }
}
