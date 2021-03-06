package com.spring.javagreenS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.dao.DbShopDAO;
import com.spring.javagreenS.vo.DbBaesongVO;
import com.spring.javagreenS.vo.DbCartListVO;
import com.spring.javagreenS.vo.DbOptionVO;
import com.spring.javagreenS.vo.DbOrderVO;
import com.spring.javagreenS.vo.DbProductVO;

@Service
public class DbShopServiceImpl implements DbShopService {

	@Autowired
	DbShopDAO dbShopDAO;

	@Override
	public List<DbProductVO> getCategoryMain() {
		return dbShopDAO.getCategoryMain();
	}

	@Override
	public List<DbProductVO> getCategoryMiddle() {
		return dbShopDAO.getCategoryMiddle();
	}

	@Override
	public List<DbProductVO> getCategorySub() {
		return dbShopDAO.getCategorySub();
	}
	
	@Override
	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode) {
		return dbShopDAO.getCategoryMiddleName(categoryMainCode);
	}

	@Override
	public List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode) {
		return dbShopDAO.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
	@Override
	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName) {
		return dbShopDAO.getCategoryMainOne(categoryMainCode, categoryMainName);
	}
	
	@Override
	public void categoryMainInput(DbProductVO vo) {
		dbShopDAO.setCategoryMainInput(vo);
	}

	@Override
	public List<DbProductVO> getCategoryMiddleOne(DbProductVO vo) {
		return dbShopDAO.getCategoryMiddleOne(vo);
	}

	@Override
	public void setCategoryMiddleInput(DbProductVO vo) {
		dbShopDAO.setCategoryMiddleInput(vo);
	}

	@Override
	public List<DbProductVO> getCategorySubOne(DbProductVO vo) {
		return dbShopDAO.getCategorySubOne(vo);
	}

	@Override
	public void setCategorySubInput(DbProductVO vo) {
		dbShopDAO.setCategorySubInput(vo);
	}

	@Override
	public void delCategoryMain(String categoryMainCode) {
		dbShopDAO.delCategoryMain(categoryMainCode);
	}

	@Override
	public void delCategoryMiddle(String categoryMiddleCode) {
		dbShopDAO.delCategoryMiddle(categoryMiddleCode);
	}

	@Override
	public List<DbProductVO> getDbProductOne(String categorySubCode) {
		return dbShopDAO.getDbProductOne(categorySubCode);
	}

	@Override
	public void delCategorySub(String categorySubCode) {
		dbShopDAO.delCategorySub(categorySubCode);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void imgCheckProductInput(MultipartFile file, DbProductVO vo) {
		// ?????? ??????(??????)??????????????? 'dbShop/product'????????? ????????? ????????????.
		try {
			String originalFilename = file.getOriginalFilename();
			if(originalFilename != null && originalFilename != "") {
				// ?????? ??????????????? ??????????????????????????? ???????????????????????? ???????????????
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			  String saveFileName = sdf.format(date) + "_" + originalFilename;
				writeFile(file, saveFileName);	  // ?????? ???????????? ????????? ????????? ???????????? ????????? ??????
				vo.setFName(originalFilename);		// ???????????? ???????????? fName??? ??????
				vo.setFSName(saveFileName);				// ????????? ????????? ???????????? vo??? set????????????.
			}
			else {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/javagreenS/data/dbShop/211229124318_4.jpg"
		// <img alt="" src="/javagreenS/data/dbShop/product/211229124318_4.jpg"
		
		// ckeditor??? ???????????? ?????? ????????? ????????????????????? ????????? ???????????? ????????? ????????? dbShop/product????????? ?????????????????? ????????????.
		String content = vo.getContent();
		if(content.indexOf("src=\"/") == -1) return;		// content????????? ????????? ????????? ????????? ????????????.
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getRealPath("/resources/data/dbShop/");
		
		int position = 29;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			String copyFilePath = "";
			String oriFilePath = uploadPath + imgFile;	// ?????? ????????? ???????????? '?????????+?????????'
			
			copyFilePath = uploadPath + "product/" + imgFile;	// ????????? ??? '?????????+?????????'
			
			fileCopyCheck(oriFilePath, copyFilePath);	// ??????????????? ????????? ????????? ???????????????????????? ?????????
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
		// ????????? ??????????????? ???????????? ????????? ????????? 'dbShop/product'???????????? vo??? set???????????? ??????.
		vo.setContent(vo.getContent().replace("/data/dbShop/", "/data/dbShop/product/"));

		// ?????? ??????????????? ?????? ????????? vo??? ??????????????? ????????? ????????? DB??? ????????????.
		// ?????? productCode??? ??????????????? ?????? ???????????? ????????? dbProduct???????????? idx????????? ???????????? ????????????. ????????? 0?????? ????????????.
		int maxIdx = 0;
		DbProductVO maxVo = dbShopDAO.getProductMaxIdx();
		if(maxVo != null) {
			maxIdx = maxVo.getIdx() + 1;
			vo.setIdx(maxIdx);
		}
		vo.setProductCode(vo.getCategoryMainCode()+vo.getCategoryMiddleCode()+vo.getCategorySubCode()+maxIdx);
		dbShopDAO.setDbProductInput(vo);
	}
	
  // ?????? ??????(dbShop??????)??? 'dbShop/product'????????? ?????????????????????
	private void fileCopyCheck(String oriFilePath, String copyFilePath) {
		File oriFile = new File(oriFilePath);
		File copyFile = new File(copyFilePath);
		
		try {
			FileInputStream  fis = new FileInputStream(oriFile);
			FileOutputStream fos = new FileOutputStream(copyFile);
			
			byte[] buffer = new byte[2048];
			int count = 0;
			while((count = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, count);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ?????? ?????? ????????? ????????? ????????????
	private void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/product/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		fos.close();
	}

	@Override
	public String[] getProductName() {
		return dbShopDAO.getProductName();
	}

	@Override
	public void setDbOptionInput(DbOptionVO vo) {
		dbShopDAO.setDbOptionInput(vo);
	}

	@Override
	public List<DbProductVO> getProductInfor(String productName) {
		return dbShopDAO.getProductInfor(productName);
	}

	@Override
	public List<DbProductVO> getDbShopList(String part) {
		return dbShopDAO.getDbShopList(part);
	}

	@Override
	public List<DbProductVO> getSubTitle() {
		return dbShopDAO.getSubTitle();
	}

	@Override
	public int getOptionSame(int productIdx, String optionName) {
		return dbShopDAO.getOptionSame(productIdx, optionName);
	}

	@Override
	public List<DbOptionVO> getOptionList(int productIdx) {
		return dbShopDAO.getOptionList(productIdx);
	}

	@Override
	public void setOptionDelete(int idx) {
		dbShopDAO.setOptionDelete(idx);
	}

	@Override
	public DbProductVO getDbShopProduct(int idx) {
		return dbShopDAO.getDbShopProduct(idx);
	}

	@Override
	public List<DbOptionVO> getDbShopOption(int productIdx) {
		return dbShopDAO.getDbShopOption(productIdx);
	}

	@Override
	public DbCartListVO getDbCartListProductOptionSearch(String productName, String optionName, String mid) {
		return dbShopDAO.getDbCartListProductOptionSearch(productName, optionName, mid);
	}

	@Override
	public void dbShopCartUpdate(DbCartListVO vo) {
		dbShopDAO.dbShopCartUpdate(vo);
	}

	@Override
	public void dbShopCartInput(DbCartListVO vo) {
		dbShopDAO.dbShopCartInput(vo);
	}

	@Override
	public List<DbCartListVO> getDbCartList(String mid) {
		return dbShopDAO.getDbCartList(mid);
	}

	@Override
	public void dbCartDelete(int idx) {
		dbShopDAO.dbCartDelete(idx);
	}

	@Override
	public void dbCartDeleteAll(int cartIdx) {
		dbShopDAO.dbCartDeleteAll(cartIdx);
	}

	@Override
	public DbCartListVO getCartIdx(int idx) {
		return dbShopDAO.getCartIdx(idx);
	}

	@Override
	public DbOrderVO getOrderMaxIdx() {
		return dbShopDAO.getOrderMaxIdx();
	}

	@Override
	public void setDbOrder(DbOrderVO vo) {
		dbShopDAO.setDbOrder(vo);
	}

	@Override
	public int getOrderOIdx(int orderIdx) {
		return dbShopDAO.getOrderOIdx(orderIdx);
	}

	@Override
	public void setDbBaesong(DbBaesongVO baesongVo) {
		dbShopDAO.setDbBaesong(baesongVo);
	}

	@Override
	public void setMemberPointPlus(int point, String mid) {
		dbShopDAO.setMemberPointPlus(point, mid);
	}

	@Override
	public List<DbBaesongVO> getBaesong(String mid) {
		return dbShopDAO.getBaesong(mid);
	}

	@Override
	public List<DbBaesongVO> getOrderBaesong(String orderIdx) {
		return dbShopDAO.getOrderBaesong(orderIdx);
	}

	@Override
	public List<DbProductVO> getMyOrderList(int startIndexNo, int pageSize, String mid) {
		return dbShopDAO.getMyOrderList(startIndexNo, pageSize, mid);
	}

	@Override
	public List<DbBaesongVO> getMyOrderStatus(int startIndexNo, int pageSize, String mid, String startJumun, String endJumun,	String conditionOrderStatus) {
		return dbShopDAO.getMyOrderStatus(startIndexNo, pageSize, mid, startJumun, endJumun,	conditionOrderStatus);
	}

	@Override
	public List<DbBaesongVO> getOrderStatus(String mid, String orderStatus, int startIndexNo, int pageSize) {
		return dbShopDAO.getOrderStatus(mid, orderStatus, startIndexNo, pageSize);
	}

	@Override
	public List<DbBaesongVO> getOrderCondition(String mid, int conditionDate, int startIndexNo, int pageSize) {
		return dbShopDAO.getOrderCondition(mid, conditionDate, startIndexNo, pageSize);
	}

	@Override
	public List<DbBaesongVO> getAdminOrderStatus(String startJumun, String endJumun, String orderStatus) {
		//System.out.println("startJumun: " + startJumun + ", endJumun: " + endJumun + ", orderStatus : " + orderStatus);
		return dbShopDAO.getAdminOrderStatus(startJumun, endJumun, orderStatus);
	}

	@Override
	public void setOrderStatusUpdate(String orderIdx, String orderStatus) {
		dbShopDAO.setOrderStatusUpdate(orderIdx, orderStatus);
	}
}
