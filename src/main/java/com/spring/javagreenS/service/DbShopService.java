package com.spring.javagreenS.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.vo.DbBaesongVO;
import com.spring.javagreenS.vo.DbCartListVO;
import com.spring.javagreenS.vo.DbOptionVO;
import com.spring.javagreenS.vo.DbOrderVO;
import com.spring.javagreenS.vo.DbProductVO;

public interface DbShopService {

	public List<DbProductVO> getCategoryMain();

	public List<DbProductVO> getCategoryMiddle();

	public List<DbProductVO> getCategorySub();

	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode);

	public List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode);

	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName);

	public void categoryMainInput(DbProductVO vo);

	public List<DbProductVO> getCategoryMiddleOne(DbProductVO vo);

	public void setCategoryMiddleInput(DbProductVO vo);

	public List<DbProductVO> getCategorySubOne(DbProductVO vo);

	public void setCategorySubInput(DbProductVO vo);

	public void delCategoryMain(String categoryMainCode);

	public void delCategoryMiddle(String categoryMiddleCode);

	public List<DbProductVO> getDbProductOne(String categorySubCode);

	public void delCategorySub(String categorySubCode);

	public void imgCheckProductInput(MultipartFile file, DbProductVO vo);

	public String[] getProductName();

	public void setDbOptionInput(DbOptionVO vo);

	public List<DbProductVO> getProductInfor(String productName);

	public List<DbProductVO> getDbShopList(String part);

	public List<DbProductVO> getSubTitle();

	public int getOptionSame(int productIdx, String optionName);

	public List<DbOptionVO> getOptionList(int productIdx);

	public void setOptionDelete(int idx);

	public DbProductVO getDbShopProduct(int idx);

	public List<DbOptionVO> getDbShopOption(int productIdx);

	public DbCartListVO getDbCartListProductOptionSearch(String productName, String optionName, String mid);

	public void dbShopCartUpdate(DbCartListVO vo);

	public void dbShopCartInput(DbCartListVO vo);

	public List<DbCartListVO> getDbCartList(String mid);

	public void dbCartDelete(int idx);

	public void dbCartDeleteAll(int cartIdx);

	public DbCartListVO getCartIdx(int idx);

	public DbOrderVO getOrderMaxIdx();

	public void setDbOrder(DbOrderVO vo);

	public int getOrderOIdx(int orderIdx);

	public void setDbBaesong(DbBaesongVO baesongVo);

	public void setMemberPointPlus(int point, String mid);

	public List<DbBaesongVO> getBaesong(String mid);

	public List<DbBaesongVO> getOrderBaesong(String orderIdx);

}
