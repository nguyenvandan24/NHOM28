package conn;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.User;
import models.Category;
import models.Product;

public class Load {
	Connection conn;
	PreparedStatement ps;// nem cau lenh query sang sql server
	ResultSet rs;// nhan kq tra ve
	
	public List<Product> getAllProduct() {
		List<Product> list = new ArrayList<>();// danh sach product
		String query = "SELECT * FROM PRODUCT";
		try {
			conn = new Connect().getconnecttion();// mo ket noi
			ps = conn.prepareStatement(query);// nem lenh query
			rs = ps.executeQuery();
			while (rs.next())
				list.add(new Product(rs.getString(1), 
						rs.getString(2), 
						rs.getFloat(3), 
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)));

		} catch (Exception e) {

		}

		return list;
	}
	
	// lấy all các danh mục
		public List<Category> getAllCategory() {
			List<Category> list = new ArrayList<>();
			String query = "SELECT * FROM CATEGORIES";
			try {
				conn =new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next())
					list.add(new Category(rs.getString(1), rs.getString(2)));

			} catch (Exception e) {

			}

			return list;
		}
		
		// lấy sp theo Cate
		public List<Product> getProductByCateID(String cid) {
			List<Product> list = new ArrayList<>();// danh sach san pham
			String query = "SELECT * FROM PRODUCT WHERE cateId = ?";
			try {
				conn = new Connect().getconnecttion();// mo ket noi
				ps = conn.prepareStatement(query);// nem lenh query
				ps.setString(1, cid);
				rs = ps.executeQuery();
				while (rs.next())
					list.add(new Product(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5),
							rs.getString(6)));

			} catch (Exception e) {

			}

			return list;
		}
		
		// lay sp theo product id
		public Product getProductById(String pid) {
			String query = "SELECT * FROM PRODUCT WHERE pId = ?";
			try {
				conn = new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, pid);
				rs = ps.executeQuery();
				while (rs.next())
					return new Product(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5),
							rs.getString(6));

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public List<Product> searchByName(String txtSearch) {
			List<Product> list = new ArrayList<>();// danh sach san pham
			String query = "SELECT * FROM PRODUCT WHERE pName like ?";
			try {
				conn = new Connect().getconnecttion();// mo ket noi
				ps = conn.prepareStatement(query);// nem lenh query
				ps.setString(1, "%" + txtSearch + "%");
				rs = ps.executeQuery();
				while (rs.next())
					list.add(new Product(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5),
							rs.getString(6)));

			} catch (Exception e) {

			}

			return list;
		}
		
		public User login(String uname, String pass) {
			String query = "SELECT * FROM USERS\n" + "WHERE userName = ? and upassword = ?";
			try {
				conn = new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, uname);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				while (rs.next()) {
					return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				}
			} catch (Exception e) {

			}
			return null;

		}
		
		// kt xem user co ton tai chua
		public User checkUser(String uname) {
			String query = "SELECT * FROM USERS\n" + "WHERE userName = ?";
			try {
				conn =new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, uname);
				rs = ps.executeQuery();
				while (rs.next()) {
					return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				}
			} catch (Exception e) {

			}
			return null;

		}

		public void register(String ruser, String rpass, String remail) {
			String query = "INSERT INTO USERS VALUES(?, ?, ?, 1)";
			try {
				conn =new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, ruser);
				ps.setString(2, rpass);
				ps.setString(3, remail);
				ps.executeUpdate();
			} catch (Exception e) {

			}

		}
		// xóa sản phẩm
		public void deleteProduct(String pId) {
			String query = "DELETE FROM PRODUCT WHERE pId=?;";
			try {
				conn = new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, pId);
				ps.executeUpdate();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		
		// insert sản phẩm
		public void insertProduct(String id, String name, String price, String description, String cate, String img) {
			String query = "INSERT INTO PRODUCT VALUES(?,?,?,?,?,?)";
			try {
				conn = new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, id);
				ps.setString(2, name);
				ps.setString(3, price);
				ps.setString(4, description);
				ps.setString(5, cate);
				ps.setString(6, img);
				ps.executeUpdate();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		
		// chỉnh sửa thông tin sản phẩm
		public void editProduct(String id, String name, String price, String description, String cate, String img) {
			String query = "UPDATE PRODUCT SET pName=?, price=?, pDescription=?, cateId=?, img=? WHERE pId=?;";
			try {
				conn =new Connect().getconnecttion();
				ps = conn.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, price);
				ps.setString(3, description);
				ps.setString(4, cate);
				ps.setString(5, img);
				ps.setString(6, id);
				ps.executeUpdate();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	public static void main(String[] args) {
		Load load = new Load();
		List<Product> list = load.getAllProduct();
		
		for (Product p : list) {
			System.out.println(p);
		}
		List<Category> listCat = load.getAllCategory();
		
		for (Category c : listCat) {
			System.out.println(c);
		}
	}

	
}
