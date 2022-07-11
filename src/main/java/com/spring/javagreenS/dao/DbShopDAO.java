package com.spring.javagreenS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.DbBaesongVO;
import com.spring.javagreenS.vo.DbCartListVO;
import com.spring.javagreenS.vo.DbOptionVO;
import com.spring.javagreenS.vo.DbOrderVO;
import com.spring.javagreenS.vo.DbProductVO;

public interface DbShopDAO {
	public List<DbProductVO> getCategoryMain();

	public List<DbProductVO> getCategoryMiddle();

	public List<DbProductVO> getCategorySub();
	
	public List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	public List<DbProductVO> getCategorySubName(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode);

	public DbProductVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName") String categoryMainName);

	public void setCategoryMainInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategoryMiddleOne(@Param("vo") DbProductVO vo);

	public void setCategoryMiddleInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategorySubOne(@Param("vo") DbProductVO vo);

	public void setCategorySubInput(@Param("vo") DbProductVO vo);

	public void delCategoryMain(@Param("categoryMainCode") String categoryMainCode);

	public void delCategoryMiddle(@Param("categoryMiddleCode") String categoryMiddleCode);

	public List<DbProductVO> getDbProductOne(@Param("categorySubCode") String categorySubCode);

	public void delCategorySub(@Param("categorySubCode") String categorySubCode);

	public DbProductVO getProductMaxIdx();

	public void setDbProductInput(@Param("vo") DbProductVO vo);

	public String[] getProductName();

	public void setDbOptionInput(@Param("vo") DbOptionVO vo);

	public List<DbProductVO> getProductInfor(@Param("productName") String productName);

	public List<DbProductVO> getDbShopList(@Param("part") String part);

	public List<DbProductVO> getSubTitle();

	public int getOptionSame(@Param("productIdx") int productIdx, @Param("optionName") String optionName);

	public List<DbOptionVO> getOptionList(@Param("productIdx") int productIdx);

	public void setOptionDelete(@Param("idx") int idx);

	public DbProductVO getDbShopProduct(@Param("idx") int idx);

	public List<DbOptionVO> getDbShopOption(@Param("productIdx") int productIdx);

	public DbCartListVO getDbCartListProductOptionSearch(@Param("productName") String productName, @Param("optionName") String optionName, @Param("mid") String mid);

	public void dbShopCartUpdate(@Param("vo") DbCartListVO vo);

	public void dbShopCartInput(@Param("vo") DbCartListVO vo);

	public List<DbCartListVO> getDbCartList(@Param("mid") String mid);

	public void dbCartDelete(@Param("idx") int idx);

	public void dbCartDeleteAll(@Param("cartIdx") int cartIdx);

	public DbCartListVO getCartIdx(@Param("idx") int idx);

	public DbOrderVO getOrderMaxIdx();

	public void setDbOrder(@Param("vo") DbOrderVO vo);

	public int getOrderOIdx(@Param("orderIdx") int orderIdx);

	public void setDbBaesong(@Param("baesongVo") DbBaesongVO baesongVo);

	public void setMemberPointPlus(@Param("point") int point, @Param("mid") String mid);

	public List<DbBaesongVO> getBaesong(@Param("mid") String mid);

	public List<DbBaesongVO> getOrderBaesong(@Param("orderIdx") String orderIdx);
}
