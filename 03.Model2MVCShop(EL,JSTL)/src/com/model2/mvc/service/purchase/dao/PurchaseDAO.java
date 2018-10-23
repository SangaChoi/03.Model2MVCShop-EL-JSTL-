package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	
	public PurchaseDAO() {
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
				
		Connection con = DBUtil.getConnection();
		
		String sql="INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql="SELECT * FROM transaction WHERE buyer_id='"+buyerId+"'";
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()) {
			
			User user=new User();
			UserService userUservice=new UserServiceImpl();
			user=userUservice.getUser(buyerId);
			
			Product product=new Product();
			ProductService productService=new ProductServiceImpl();
			product.setProdNo(rs.getInt("PROD_NO"));
			product=productService.getProduct(product.getProdNo());
			
			Purchase purchase=new Purchase();
			purchase.setBuyer(user);
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			list.add(purchase);
		}
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}
	
	private int getTotalCount(String sql) throws Exception {
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}

}
